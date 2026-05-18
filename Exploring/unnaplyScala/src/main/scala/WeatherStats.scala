// Simulate a function to retrieve weather data
import scala.util.Random

// Define a case class to hold weather statistics
case class WeatherStats(
                         averageTempToday: Double,
                         averageTempWeek: Double,
                         averageTempMonth: Double,
                         totalRainfallMonth: Double,
                         highestWindSpeedMonth: Double
                       )

case class Stats(today: Double, week: Double)

def getWeatherData(): WeatherStats = {
  // Generate random weather data for demonstration
  val averageTempToday = Random.nextDouble() * 40 // Random temperature between 0 and 40 degrees
  val averageTempWeek = Random.nextDouble() * 40
  val averageTempMonth = Random.nextDouble() * 40
  val totalRainfallMonth = Random.nextDouble() * 100 // Random rainfall in mm
  val highestWindSpeedMonth = Random.nextDouble() * 100 // Random wind speed in km/h

  // Return a WeatherStats object with the generated values
  WeatherStats(
    averageTempToday,
    averageTempWeek,
    averageTempMonth,
    totalRainfallMonth,
    highestWindSpeedMonth
  )
}


def todayAndWeekStats(): Stats =
  val allStats = getWeatherData()
  Stats(allStats.averageTempToday, allStats.averageTempWeek)

def todayAndWeekStatsTuple(): (Double, Double) =
  val allStats = getWeatherData()
  (allStats.averageTempToday, allStats.averageTempWeek)

val todayWeekStats = todayAndWeekStatsTuple()

//referring to the elements of the tuple
val (today, week) = todayAndWeekStatsTuple()

val t: (Int, String, Int) = (1,"Michał", 2)
val something: Any = t match
  case (_,b,c) => b*c
  case _ =>


object Main {

  def main(args: Array[String]): Unit = {
    println(something)
  }

}







