import org.scalatest.funsuite.AnyFunSuite
import sttp.client3.testing.SttpBackendStub
import sttp.client3.{Response, UriContext, basicRequest, StringBody}
import sttp.model.StatusCode
import sttp.client3._

class Test extends AnyFunSuite {

  test("uploadDefinition should return content on successful upload") {
    val backendStub = SttpBackendStub.synchronous
      .whenRequestMatches(_.uri == uri"https://api.example.com/definitions")
      .thenRespond(Response.ok("Upload successful"))

    val response = DataFetcher.uploadDefinition("Apple", "A fruit that is red, green, or yellow")
    assert(response == "Upload successful")
  }

  test("uploadDefinition should return error message on failed upload") {
    val backendStub = SttpBackendStub.synchronous
      .whenRequestMatches(_.uri == uri"https://api.example.com/definitions")
      .thenRespondWithCode(StatusCode.BadRequest, "Upload failed")

    val response = DataFetcher.uploadDefinition("Apple", "A fruit that is red, green, or yellow")
    assert(response == "Error during upload: Upload failed")
  }
}
