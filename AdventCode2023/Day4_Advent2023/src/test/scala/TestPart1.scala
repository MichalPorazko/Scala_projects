class TestPart1 extends munit.FunSuite{

  test("splitCard separates winning and actual numbers correctly") {
    val line = "Card 1: 41 48 83 86 17 | 83 86 6 31 17 9 48 53"
    assertEquals((ReadingFile.splitCard(line)), Seq("41 48 83 86 17", "83 86 6 31 17 9 48 53"))
  }

  test("separateNumbers converts a string of numbers into a sequence of Ints") {
    val line = "41 48 83 86 17"
    assertEquals(ReadingFile.seperateNumbers(line), Set(41, 48, 83, 86, 17))
  }

  test("card creates a Card instance with correct winning and actual numbers") {
    val line = "41 48 83 86 17 | 83 86 6 31 17 9 48 53"
    val expectedCard = Card(Set(41, 48, 83, 86, 17), Set(83, 86, 6, 31, 17, 9, 48, 53))
    assertEquals(ReadingFile.card(line), expectedCard)
  }
  
  
  
}
