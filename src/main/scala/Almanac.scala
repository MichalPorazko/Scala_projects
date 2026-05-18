

case class Almanac(seeds: List[Long], resources: List[Resource])

object Almanac{

  def findTheLocation(seed: Long, resources: List[Resource]): Long =
    /*println(s"Resources:")
    resources.foreach { resource =>
     println(s"  Section: ${resource.section}")
      resource.ranges.foreach { range =>
        println(s"    RangeFunction(destination: ${range._1}, source: ${range._2}, size: ${range._3})")
      }
    }*/

    resources.foldLeft(seed) { (number, resource) =>
      println(s"current number is: $number")
      println(s"section type: ${resource.section}")
      //resource.ranges.foreach(range => println(s"RangeFunction: $range"))
      val transformation = Resource.transformTheNumber(number, resource.ranges)
      //println(s"its transformation is $transformation")
      transformation
    }

  def lowestLocation(seeds: List[Long], resources: List[Resource]): Long =
    seeds.map( seed => findTheLocation(seed, resources)).min

  def seedsRange(seeds: List[Long]): List[SeedsRange] =
    //for example List((79,14), (55,13))
    
    val iteratorOfPairs: Iterator[List[Long]] = seeds.sliding(2, 2)
    val seedsRanges = iteratorOfPairs.collect{
      //The case statement within the collect method is a partial function
      case List(start, length) =>SeedsRange(start, length)
      }.toList
    seedsRanges

  def findLocationSD(seedRange: SeedsRange, resource: List[Resource]): Long =
    val seeds = (seedRange._1 until seedRange._1+seedRange._2).toList
    println(s"seeds: $seeds")
    lowestLocation(seeds, resource)

  def findTheLowestLocationSD(seedsRanges: List[SeedsRange], resource: List[Resource]): Long =
    val locations =
      seedsRanges.map{ seedRange =>
      findLocationSD(seedRange, resource)
    }
    println(s"The locations are $locations")
    locations.min







}


