import scala.io.Source


object ReadingFile {


  def parseAlmanac(filePath: String): Almanac =

    val lines = Source.fromFile(filePath).getLines().toList
    
    

    val seedLine = lines.head
    val seeds = seedLine.split(" ").drop(1).map(_.toLong).toList

    val sections = lines.tail.mkString("\n").split("\n\n").map(_.trim).toList

    val resources = sections.map { section =>
      val lines = section.split("\n")
      val sectionType = lines.head match {
        case "seed-to-soil map:" => Section.Soil
        case "soil-to-fertilizer map:" => Section.Fertilizer
        case "fertilizer-to-water map:" => Section.Water
        case "water-to-light map:" => Section.Light
        case "light-to-temperature map:" => Section.Temperature
        case "temperature-to-humidity map:" => Section.Humidity
        case "humidity-to-location map:" => Section.Location
      }

      splittingToResources(lines.tail, sectionType)
    }

    Almanac(seeds, resources)


  def splittingToResources(s: Array[String], section: Section): Resource =
    val rangeFunctions =
       s.map { entry =>
      val Array(destination, source, length) = entry.split(" ").map(_.toLong)
      RangeFunction(destination, source, length)}.toSeq
    Resource(rangeFunctions, section)


}
