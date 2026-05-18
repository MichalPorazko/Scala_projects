class TestPart2 extends munit.FunSuite{

  val almanac = ReadingFile.parseAlmanac("exampleFile")
  val seedsRange = Almanac.seedsRange(almanac.seeds)
  println(almanac.resources)

  test("testing the seedsRange method"){
    assertEquals(seedsRange, List(SeedsRange(79, 14), SeedsRange(55, 13)))
  }

  test("testing finding the lowest Location in a Range by using the findLocationForSeedRange method"){

    val location = Almanac.findLocationSD(SeedsRange(79,14), almanac.resources)
    assertEquals(location, 46L)

  }

  test("testing finding the lowest Location in a Range by using the findLocationForSeedRange method") {

    val location = Almanac.findTheLowestLocationSD(seedsRange, almanac.resources)
    assertEquals(location, 46L)

  }


}
