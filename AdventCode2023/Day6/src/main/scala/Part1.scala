
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*
import scala.concurrent.{Await, Future}
import scala.io.Source
import scala.util.matching.Regex
import scala.util.{Failure, Success}

object Part1 {

  def readRacesFromFileAsync(filename: String): Future[List[Race]] = Future {
    println(s"Attempting to read file: $filename")
    val source = Source.fromFile(filename)
    val lines = try source.getLines().toList finally source.close()

    val numberPattern: Regex = "\\d+".r

    println(s"First line: ${lines.head}")
    println(s"Second line: ${lines(1)}")

    val times: List[Int] = numberPattern.findAllIn(lines.head).toList.map(_.toInt)
    val distances: List[Int] = numberPattern.findAllIn(lines(1)).toList.map(_.toInt)

    if (times.length != distances.length) {
      throw new IllegalArgumentException("Mismatch between number of times and distances")
    }

    val zipped: List[(Int, Int)] = times.zip(distances)
    zipped.map { case (time, distance) => Race(time, distance) }
  }

  // Get the number of ways to beat the record
  def waysToBeatRecord(race: Race): Int = {
    var ways = 0
    var time = race.time
    var speed = 0
    while (time > 0) {
      if ((time * speed) > race.distance) {
        ways += 1
      }
      time -= 1
      speed += 1
    }
    ways
  }

  def main(args: Array[String]): Unit = {
    val filename = if (args.nonEmpty) args(0) else "file"
    println(s"Filename: $filename")

    val racesFuture: Future[List[Race]] = readRacesFromFileAsync(filename)

    val resultFuture: Future[Int] = racesFuture.map(races => {
      println(s"Parsed races: $races")
      races.map(waysToBeatRecord).product
    })

    resultFuture.onComplete {
      case Success(result) => println(s"Product of ways to beat the record: $result")
      case Failure(ex) => println(s"Failed to calculate the product: ${ex.getMessage}")
    }

    Await.result(resultFuture, 10.seconds)
  }
}
