import Section.Soil

import scala.io.Source
import munit.FunSuite

class ReadingFileTest extends FunSuite {

  val lines = Source.fromFile("exampleFile").getLines().toList
  //println(lines)

 /* test("testing reading from the file, specifically extracting the seeds") {
    val seedLine = lines.head
    val seeds = seedLine.split(" ").drop(1).map(_.toLong).toList
    assertEquals(seeds, List(79L, 14L, 55L, 13L)) // Expected list as List[Long]
  }

  test("testing the zipping with index"){
    //val sections = lines.tail.mkString("\n").split("\n\n").toList

    val tail = lines.tail
   // println(tail)

    val splitLines = tail.flatMap( line =>
      val split = line.split("\n")
      //println(split.toList)
      //println(split.head)
      split
    )
   // println(splitLines)

    val mkStringAdd = tail.mkString("\n")
    //println(mkStringAdd)

    val split = mkStringAdd.split("\n\n").toList
    //println(split)

    val trim = split.map(_.trim).toList
    //println(trim)

    //assertEquals(split, trim)

    val sections = lines.tail.mkString("\n").split("\n\n").map(_.trim).toList
    println(sections)

    val splitSection = sections.flatMap( section =>
      val lala = section.split("\n")
      println(lala.toList)
      lala
    )
  }*/

  test("testing the splittingToResources method"){

    val fileContent = Source.fromFile("fileForTestingReading").getLines().toList
    println(s"File content: $fileContent")

    val sections = fileContent.tail.mkString("\n").split("\n\n").map(_.trim)
    println(s"Section: ${sections.toList}")

    // the result is Array("seed-to-soil map:", "50 98 2", "52 50 48")
    val lines = sections.flatMap( section => section.split("\n"))
    println(s"Lines: ${lines.toList}")

    //this gives as an Array that we want to work on
    val arrayString = lines.tail
    println(s"Array String: ${arrayString.toList}")

//    this gives us two arrays: List(50, 98, 2) and List(52, 50, 48)
    val arrays = arrayString.map(_.split(" "))
    println(s"Arrays: ${arrays.toList}")



    // Split arrays into individual arrays
    val array1 = arrays(0)
    val array2 = arrays(1)

    //now for each of those two array we do this:
    val listOfResources1 = arrays.map( array =>
      val Array(destination, source, length) = array.map(_.toLong)
      Resource(Seq(RangeFunction(destination, source, length)), Section.Soil)
    )

    //different way
    val listOfResources2 = arrays.map { entry =>
      val Array(destination, source, length) = entry.map(_.toLong)
      RangeFunction(destination, source, length)
    }.map { rangeFunction =>
      Resource(Seq(rangeFunction), Section.Soil)
    }

    listOfResources2.foreach(println)

    val expectedType = Resource(Seq(RangeFunction(50, 98, 2)), Section.Soil)

    // Assertions for testing purposes
    assert(listOfResources1.head == expectedType)
    assert(listOfResources2.head.ranges.head == RangeFunction(50, 98, 2))

  }

  /*test("testing the whole method with the file fileForTestingReading "){

    val result = ReadingFile.parseAlmanac("fileForTestingReading")

    val almanac = Almanac(
      seeds = List(79, 14, 55, 13),
      resources = List(
        Resource(List(RangeFunction(50, 98, 2)), Section.Soil),
        Resource(List(RangeFunction(52, 50, 48)), Section.Soil)
      )
    )

    assertEquals(result, almanac)
  }

  test("testing the whole method with the example file from the website") {

    val result = ReadingFile.parseAlmanac("exampleFile")

    val almanac = Almanac(
      seeds = List(79, 14, 55, 13),
      resources = List(
        Resource(List(RangeFunction(50, 98, 2)), Section.Soil),
        Resource(List(RangeFunction(52, 50, 48)), Section.Soil),
        Resource(List(RangeFunction(0, 15, 37)), Section.Fertilizer),
        Resource(List(RangeFunction(37, 52, 2)), Section.Fertilizer),
        Resource(List(RangeFunction(39, 0, 15)), Section.Fertilizer),
        Resource(List(RangeFunction(49, 53, 8)), Section.Water),
        Resource(List(RangeFunction(0, 11, 42)), Section.Water),
        Resource(List(RangeFunction(42, 0, 7)), Section.Water),
        Resource(List(RangeFunction(57, 7, 4)), Section.Water),
        Resource(List(RangeFunction(88, 18, 7)), Section.Light),
        Resource(List(RangeFunction(18, 25, 70)), Section.Light),
        Resource(List(RangeFunction(45, 77, 23)), Section.Temperature),
        Resource(List(RangeFunction(81, 45, 19)), Section.Temperature),
        Resource(List(RangeFunction(68, 64, 13)), Section.Temperature),
        Resource(List(RangeFunction(0, 69, 1)), Section.Humidity),
        Resource(List(RangeFunction(1, 0, 69)), Section.Humidity),
        Resource(List(RangeFunction(60, 56, 37)), Section.Location),
        Resource(List(RangeFunction(56, 93, 4)), Section.Location)
      )
    )


    assertEquals(result, almanac)
  }*/
}
