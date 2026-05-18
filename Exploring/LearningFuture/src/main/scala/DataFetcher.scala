import play.api.libs.json.Json.using
import play.api.libs.json.{JsObject, Json}
import sttp.*
import sttp.client3.{HttpClientSyncBackend, HttpURLConnectionBackend, Identity, Request, Response, SttpBackend, UriContext, asString, asStringAlways, basicRequest}
import sttp.model.StatusCode

import scala.io.Source
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Either, Using}
import scala.util.control.NonFatal

object DataFetcher {

  private val backend = HttpClientSyncBackend()

  // Define how to release the HttpClientSyncBackend
  implicit val releasableBackend: Using.Releasable[SttpBackend[Identity, Any]] =
    new Using.Releasable[SttpBackend[Identity, Any]] {
      def release(resource: SttpBackend[Identity, Any]): Unit = resource.close()
    }

  def uploadDefinition(word: String, definition: String): String = {
    val definitionEndpoint = uri"https://api.example.com/definitions"
    val jsonPayload = s"""{"word": "$word", "definition": "$definition"}"""

    // Using block to manage the backend resource
    Using(HttpClientSyncBackend()) { backend =>
      val response = basicRequest
        .body(jsonPayload)
        .post(definitionEndpoint)
        .send(backend)

      // Handle the response
      response.body match {
        case Right(content) => content
        case Left(error) => s"Error during upload: $error"
      }
    }.getOrElse {
      // Handling the case where resource management fails
      throw new RuntimeException("Failed to manage HTTP client backend resource")
    }
  }

  def readFileAndPostData(filePath: String): Future[Response[Either[String, String]]] = Future {
    // Use Using to ensure the Source is closed properly
    Using.resource(Source.fromFile(filePath)) { source =>
      // Read data from the file
      val data = source.getLines().map { line =>
        val Array(id, word) = line.split(",\\s*")
        (id.trim, word.trim)
      }.toList

      // Convert list of tuples into a JSON array of objects
      val jsonPayload = data.map { case (id, word) =>
        s"""{"id": "$id", "word": "$word"}"""
      }.mkString("[", ",", "]")

      // Create a POST request with the JSON payload
      val request = basicRequest
        .body(jsonPayload)
        .post(uri"https://api.example.com/posts")
        .contentType("application/json")

      // Send the request using an HTTP client backend
      sendRequest(request)
    }
  }

  def sendRequest(request: Request[Either[String, String], Any]): Response[Either[String, String]] =
    request.send(backend)

  // Method to fetch data from a remote server
  def fetchData(url: String): Future[Either[String, List[(String, String)]]] = Future {
    // Create a GET request
    val request = basicRequest
      .get(uri"$url")
      .response(asStringAlways)

    // Send the request using an HTTP client backend
    val response = request.send(backend)

    // Process the response
    response.body match {
      case json if response.code == StatusCode.Ok =>
        // Attempt to parse the JSON response
        try {
          val jsonVal = Json.parse(json)
          val items = (jsonVal \ "data").as[List[JsObject]]
          val results = items.map { item =>
            val id = (item \ "id").as[String]
            val word = (item \ "word").as[String]
            (id, word)
          }
          Right(results)
        } catch {
          case e: Exception => Left(s"Error parsing JSON: ${e.getMessage}")
        }
      case _ =>
        Left("Failed to fetch data or server error")
    }
  }

  def fetchDefinition(word: String): Future[Either[String, String]] = Future {
    // Assume the API endpoint for definitions is constructed by appending the word to a base URL
    val definitionUrl = s"https://api.example.com/definitions/$word"

    // Create a GET request
    val request = basicRequest
      .get(uri"$definitionUrl")
      .response(asStringAlways)

    // Send the request using an HTTP client backend
    val response = request.send(backend)

    // Process the response
    response.body match {
      case json if response.code == StatusCode.Ok =>
        // Attempt to parse the JSON response
        try {
          val jsonVal = Json.parse(json)
          val definition = (jsonVal \ "definition").as[String]
          Right(definition)
        } catch {
          case e: Exception => Left(s"Error parsing JSON: ${e.getMessage}")
        }
      case _ =>
        Left("Failed to fetch data or server error")
    }
  }

  def getAllDefinitions(url: String): Future[List[Either[String, String]]] =
    fetchData(url).flatMap{
      case Right(idsAndWords) =>
        val words = idsAndWords.map(_._2)
        Future.traverse(words)(fetchDefinition)
      case Left(error) => Future.failed(new Exception(error))
    }

  //using foldLeft
  def getAllDefinitionsFLD(url: String): Future[List[Either[String, String]]] =
    fetchData(url).flatMap {
      case Right(idsAndWords) =>
        val listOfWords = idsAndWords.map(_._2)
        listOfWords.foldLeft[Future[List[Either[String, String]]]](Future(List.empty)){
          (words, word) =>
            for
              definitionsOfWords <- words
              definition <- fetchDefinition(word)
            yield definitionsOfWords :+ definition
        }
      case Left(error) => Future.failed(new Exception(error))
    }

  def retry(word: String): Future[Either[String, String]] =
    val maxAttempts = 4
    def getData(attempt: Int): Future[Either[String, String]] =
      if attempt == 0 then
        Future.failed(Exception("no damn data"))
      else
        println(s"doing me best to get the data, attempt $attempt")
        /*
                * recoverWith includes a case _ => statement, catches any Throwable and apply the function defined.
                without a case, expression not recognised as a PartialFunction, which is required by recoverWith.
                recoverWith, the function expects a PartialFunction[Throwable, Future[U]].
                * This means the function you provide should be able to handle some (or all)
                *  Throwable instances and return a Future[U] where U is a type compatible with the original Future.
                The case syntax is syntactic sugar for creating an instance of a PartialFunction
                * */
        val lala: Future[Either[String, String]] =
          fetchDefinition(word).recoverWith{ case NonFatal(_) =>
          System.err.println(s"Fetching data failed")
          getData(attempt - 1)
        }
        lala
    getData(maxAttempts)

  //the method like getAllDefinitionsFLD and  getAllDefinitions but using zip and retry method
  def getAllData(url: String): Future[List[Either[String, String]]] =
    fetchData(url).flatMap {
      case Right(idsAndWords) =>
        val listOfWords = idsAndWords.map(_._2)
        listOfWords.foldLeft[Future[List[Either[String, String]]]](Future(List.empty)) {
          (words, word) =>
            for
              definitionsOfWords <- words
              definition <- retry(word)
            yield definitionsOfWords :+ definition
        }
      case Left(error) => Future.failed(new Exception(error))
    }


}
