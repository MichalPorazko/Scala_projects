

case class Resource(ranges: Seq[RangeFunction], section: Section)


enum Section:
  case Seed, Soil, Fertilizer, Water,
    Light, Temperature, Humidity, Location

object Resource{

  def mapTheNumber(value: Long, rangeFunction: RangeFunction): Option[Long] =
    val RangeFunction(destination, source, size) = rangeFunction
    //println(s"Mapping value $value with rangeFunction (destination: $destination, source: $source, size: $size)")
    //println(s"source + size - 1 is ${source + size - 1}")
    //println(s"source: $source")
    //println(s"value <= (source + size - 1) is ${value <= (source + size - 1)}")
    //println(s"value >= source is ${value >= source}")
    if (value >= source && value < (source + size)) {
      //println(s"value - source is ${value - source}")
      //println(s"destination + (value - source) is ${destination + (value - source)}")
      Some(destination + (value - source))
    } else {
      None
    }

  def transformTheNumber(value: Long, ranges: Seq[RangeFunction]): Long =
    println(s"Starting transformation of value $value")
    val findRange: Option[Long] =
      ranges.foldLeft[Option[Long]](None) { (acc, rangeFunction) =>
        println(s"Current accumulator: $acc")
        // if acc is still None (which it is by default), then
        // run the mapTheNumber operation, which may change the result
        val result: Option[Long] = acc.orElse(mapTheNumber(value, rangeFunction))
        // if the result is still None, return result
        //println(s"Result after applying rangeFunction: $result")
        result
      }
    // finally, use the getOrElse operation on it so that it is no longer None
    val finalResult: Long = findRange.getOrElse(value)
    //println(s"Final result of transformation: $finalResult")
    finalResult

  def transformTheNumberDifferentVersion(value: Long, ranges: Seq[RangeFunction]): Long =
    //we start the iteration from the value itself
    //which seems quite intuitive
    val findRange: Long =
      ranges.foldLeft(value) { (acc, rangeFunction) =>
        val mapResult: Long =
          //if the mapping results in None it will simply mean
          //that we stay with the value itself
          mapTheNumber(acc, rangeFunction).getOrElse(acc)
        //we do this every time there is an iteration
        mapResult
      }
    findRange




}