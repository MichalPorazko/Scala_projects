import scala.concurrent.{Await, Future}
import scala.io.Source
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*

object Part2 {

  def parse(filePath: String): Race = {
    // Read the file contents
    val source = Source.fromFile(filePath)
    val lines = try source.getLines().toList finally source.close()

    // Extract the time and distance strings
    val timeLine = lines.find(_.startsWith("Time:")).getOrElse("")
    val distanceLine = lines.find(_.startsWith("Distance:")).getOrElse("")

    // Extract the numbers from the lines
    val timeNumbers = timeLine.stripPrefix("Time:").trim.split("\\s+").mkString
    val distanceNumbers = distanceLine.stripPrefix("Distance:").trim.split("\\s+").mkString

    // Convert the concatenated strings to Long
    val time = timeNumbers.toLong
    val distance = distanceNumbers.toLong

    // Create a Race object
    Race(time, distance)
  }

  def computeDistance(time: Long, race: Race): Long =
    time*(race.time - time)

  def records(race: Race): Long =

      // we can use a quadratic equation in this method to find the records
      // time * (wholeTime - time) > distance
      // math.ceil to round up the lower bound to ensure we exceed the record distance:
      val minHoldTime = math.ceil((race.time - math.sqrt(race.time * race.time - 4 * race.distance)) / 2).toLong
      val maxHoldTime = math.floor((race.time + math.sqrt(race.time * race.time - 4 * race.distance)) / 2).toLong

      (minHoldTime to maxHoldTime).length




  def main(args: Array[String]): Unit = {
    val filename = if (args.nonEmpty) args(0) else "file"
    println(s"Filename: $filename")

    try {
      val race = parse(filename)
      println(s"Successfully parsed race: $race")
      val numWaysToWin = records(race)
      println(s"Number of ways to win: $numWaysToWin")
    } catch {
      case e: Exception =>
        println(s"Exception: ${e.getMessage}")
    }
  }

}
