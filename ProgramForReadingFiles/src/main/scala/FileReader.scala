import scala.collection.immutable.List
import scala.io.BufferedSource
import scala.util.Using

object FileReader {


  def parseCoordinates(sequenceOfLines: Iterator[String]): Seq[(Double, Double)] =
    try {
      val coordinates = sequenceOfLines.map(line => line.split({
          "\\s+"
        })).filter(_.nonEmpty)
        .map(array => (array(0).toDouble, array(1).toDouble)).toList.sortBy(_._1)
      if (coordinates.isEmpty)
        throw CoordinatesError("brak danych do wyświetlenia")
      else
        coordinates
    }
    catch {
      //unification of the errors
      case e: IndexOutOfBoundsException => throw CoordinatesError("brak koordynaty")
      case e: NumberFormatException => throw CoordinatesError("Niepoprawny format liczby")
      case e: CoordinatesError => throw e
      case e: Exception => throw CoordinatesError("Wystąpił nieznany błąd")
    }


  def getCordinates(fileName: String): Seq[(Double, Double)] = {

    Using(scala.io.Source.fromFile(fileName)) {
      f => parseCoordinates(f.getLines())
    }.get
  }

  def fileOperation[A](fileName: String)(f: scala.io.Source => A): A = {
    val source = scala.io.Source.fromFile(fileName)
    try {
      f(source)
    } finally {
      source.close()
    }
  }

  def getCordinates2(fileName: String): List[(Double, Double)] = {
    fileOperation(fileName) { (source: scala.io.Source) =>
      source.getLines().map(line => line.split('\t'))
        .map(array => (array(0).toDouble, array(1).toDouble)).toList.sortBy(_._1)
    }
  }









}
