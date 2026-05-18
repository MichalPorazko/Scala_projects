
class TestPart1 extends munit.FunSuite{


  test("testing the mapTheNumber method"){

    val rangeFunction1 = RangeFunction(52, 50, 48)
    val rangeFunction2 = RangeFunction(50, 98, 2)
    val rangeFunction3 = RangeFunction(0, 11, 42)
    
    val value1 = 78
    val value2 = 49
    val value3 = 99
    val value4 = 100
    
    val result1 = Resource.mapTheNumber(value1, rangeFunction1)
    val result2 = Resource.mapTheNumber(value2, rangeFunction2)
    val result3 = Resource.mapTheNumber(value3, rangeFunction2)
    val result4 = Resource.mapTheNumber(value4, rangeFunction2)

    assertEquals(result1, Some(80L))
    assertEquals(result2, None)
    assertEquals(result3, Some(51L))
    assertEquals(result4, None)
  }

  test("testing the first transformTheNumber method"){
    val rangeFunction1 = RangeFunction(0,0,0)
    val rangeFunction2 = RangeFunction(50, 98, 2)
    val rangeFunction3 = RangeFunction(52, 50, 48)
    val rangesFunctions = Seq(rangeFunction1, rangeFunction2, rangeFunction3)
    val resource = Resource(rangesFunctions, Section.Soil)

    val result1 = Resource.transformTheNumber(0, rangesFunctions)

    assertEquals(result1, 0L)

    val result2 = Resource.transformTheNumber(48, rangesFunctions)

    assertEquals(result2, 48L)


    val result3 = Resource.transformTheNumber(100, rangesFunctions)
    assertEquals(result3, 100L)

    val result4 = Resource.transformTheNumber(99, rangesFunctions)

    assertEquals(result4, 51L)

  }

  test("testing the water light transformation for seed 14"){

    val rangeFunction1 = RangeFunction(88L,  18L,  7L)
    val rangeFunction2 = RangeFunction(18L, 25L, 70L)
    val rangesFunctions = Seq(rangeFunction1, rangeFunction2)
    val resource = Resource(rangesFunctions, Section.Light)

    val result1 = Resource.transformTheNumber(49L, rangesFunctions)

    assertEquals(result1, 42L)



  }

  test("testing the fertilizer water  transformation for seed 14") {

    val rangeFunction1 = RangeFunction(49L, 53L, 8L)
    val rangeFunction2 = RangeFunction(0L, 11L, 42L)
    val rangeFunction3 = RangeFunction(42L, 0L, 7L)
    val rangeFunction4 = RangeFunction(57L, 7L, 4L)
    val rangesFunctions = Seq(rangeFunction1, rangeFunction2, rangeFunction3, rangeFunction4)
    val resource = Resource(rangesFunctions, Section.Water)

    val result1 = Resource.transformTheNumber(53L, rangesFunctions)

    assertEquals(result1, 49L)


  }

  test("testing the findTheLocation method"){

    val almanac = ReadingFile.parseAlmanac("exampleFile")

    val location1 = Almanac.findTheLocation(79L, almanac.resources)
    assertEquals(location1, 82L)

    val location2 = Almanac.findTheLocation(55L, almanac.resources)
    assertEquals(location2, 86L)

    val location3 = Almanac.findTheLocation(14L, almanac.resources)
    assertEquals(location3, 43L)

    val location4 = Almanac.findTheLocation(82L, almanac.resources)
    assertEquals(location4, 46L)



  }


}
