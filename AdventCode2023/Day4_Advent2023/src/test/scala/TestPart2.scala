class TestPart2 extends munit.FunSuite{

  val cards = Seq(
    Card(Set(30, 67, 98), Set(30, 45, 68, 10)),
    Card(Set(12, 45, 25), Set(12, 45, 65, 14)),
    Card(Set(100, 24), Set(37, 23, 43, 14)),
    Card(Set(87, 37, 23), Set(37, 65, 12, 23))


  )

  /*test("testing the matching method"){
    val expectedCard = Card(Set(41, 48, 83, 86, 17), Set(83, 86, 6, 31, 17, 9, 48, 53))
    assertEquals(expectedCard.matching, Set(17, 86, 48, 83))
  }

  test("benefits what the zipWithIndex method brings me"){
    val haha = cards.zipWithIndex
    println(haha.map((_, n) => n +1))
    println(haha.filter(_._1.hasMatchingNumbers).map((_, n) => n +1))
  }

  test("testing the collect method which is used in the unfold method") {

    def collectingMethod(index: Int, winningNumbers: Int): Unit =
      val copiesGenerated = (1 to winningNumbers).collect {
        case i if (i + index < cards.length) => (cards(i + index))
      }
      println(copiesGenerated)

    //Card(Set(12, 45, 25), Set(12, 45, 65, 14)), 2 winning number
    val card2 = cards(1)
    val winningNumbers = card2.matching.size
    collectingMethod(1, 2)

    //Card(Set(87, 37, 23), Set(37, 65, 12, 23)) 0 matching numbers
    val card4 = cards(3)
    val n = card4.matching.size
    collectingMethod(3, 2)

  }


  test("the unfold method"){

    val lala = Part2.Scratchcards(cards).unfold(cards.zipWithIndex)
    println(lala.map((_, index) => index+1))
  }

  test("building the result function and testing it"){

    val chars = Seq('a', 'b', 'a', 'c', 'b', 'b', 'd', 'c', 'c', 'a', 'd')
    val identityCheck = chars.map(identity)
    println(identityCheck)

    val usingGroupByMethod = chars.groupBy(identity)
    println(usingGroupByMethod)

    println(usingGroupByMethod.map(_._2.size))

    //the key word case has to be used hee
    /*By using case (char, seq), we explicitly declare that each element in the map (from usingGroupByMethod)
     is a tuple composed of char and seq,
     where char is the key and seq is the sequence (or list) associated with that key.*/
    val addCount = usingGroupByMethod.map{case (char, seq) => (char, seq.size)}

    Part2.printCards(addCount)

    val scratchCards = Part2.Scratchcards(cards).unfold(cards.zipWithIndex)
    val result = Part2.result(scratchCards)
    Part2.printCards(result)

  }*/

  test("testing the example from the wbesite"){

    val exampleCards = Seq(
      Card(Set(41, 48, 83, 86, 17), Set(83, 86,  6, 31, 17,  9, 48, 53)),
      Card(Set(13, 32, 20, 16, 61), Set(61, 30, 68, 82, 17, 32, 24, 19)),
      Card(Set(1 ,21, 53, 59, 44), Set(69, 82, 63, 72, 16, 21, 14,  1)),
      Card(Set(41, 92, 73, 84, 69), Set(59, 84, 76, 51, 58,  5, 54, 83)),
      Card(Set(87, 83, 26, 28, 32), Set(88, 30, 70, 12, 93, 22, 82, 36)),
      Card(Set(31, 18, 13, 56, 72), Set(74, 77, 10, 23, 35, 67, 36, 11)))

    val scratchCards = Part2.Scratchcards(exampleCards).unfold(exampleCards.zipWithIndex)
    val result = Part2.countCards(scratchCards)
    Part2.printCards(result)
    println(Part2.result(result))

  }



}
