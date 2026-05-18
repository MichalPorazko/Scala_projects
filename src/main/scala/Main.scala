object Main {

   @main def fun = {
    println("asda")
     val almanac = ReadingFile.parseAlmanac("mainFile")
     val seedRanges = Almanac.seedsRange(almanac.seeds)
     val location = Almanac.findTheLowestLocationSD(seedRanges, almanac.resources)
     println(location)

   }

}
