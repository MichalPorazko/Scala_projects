import scala.io.Source
import scala.util.Using

object Part2 {


  def countCards(unfoldedCards: Seq[(Card, Int)]): Map[Int, Int] =
    //get just the indexes of the cards
    val haha = unfoldedCards.map(_._2)
    //group them by themselves, so their amount
    val map = haha.groupBy(identity).map{
      case (cardIndex, count) => (cardIndex + 1, count.size)
    }
    map

  def result(countCards: Map[Int, Int]): Int =
    countCards.foldLeft(0)( (a,b) => a + b._2 )

  def printCards[A](cardCount: Map[A, Int]): Unit =
    cardCount.foreach { (card, count) =>
      println(s"$card -> $count")
    }



  class Scratchcards(cards: Seq[Card]) {

    def unfold(winners: Seq[(Card, Int)]): Seq[(Card, Int)] =
      winners.flatMap { case (card, index) =>
        println(s"card ${index+1}")

        val n = card.matching.size

        println(s"matches for card ${index+1}: $n")

        val copies: Seq[(Card, Int)] = (1 to n).collect {
          case i if i + index < cards.length =>
            println(s"copy of card ${i + index +1} from card ${index+1}")
            (cards(i + index), i+ index)
        }
        copies.length match
          case 0 =>
            Seq((card, index))
          case _ =>
            val result = (card, index) +: unfold(copies)
            println(s"added the copies from card ${index + 1}")
            result



      }
      

    def computeResult3: Seq[(Card, Int)] = {
      val originalWinners = cards.zipWithIndex
      val result = collection.mutable.Buffer[(Card, Int)](originalWinners *)
      val queue = collection.mutable.ArrayDeque(cards.zipWithIndex *)
      while (queue.nonEmpty) {
        val item = queue.removeHead()
        val copies = unfoldWinner(item)
        result.appendAll(copies)
        queue.appendAll(copies)
      }
      result.toSeq
    }

    def unfoldWinner(winner: (Card, Int)): Seq[(Card, Int)] =
      val (card, index) = winner
      val winners = winner._1.winningNumbers.size
      (1 to winners).collect{
        case i if i + index < cards.length =>
          (cards(index + i), index +i)
      }

  }




  @main def runEmpty = {

    var cards: Seq[Card] = Seq()
    Using(Source.fromFile("input.txt")) { source =>
      cards = source.getLines().map(ReadingFile.card).toSeq
    }.getOrElse(sys.error("Cannot open input file."))


    val lala = Scratchcards(cards).unfold(cards.zipWithIndex)

  }
}
