import scala.io.Source
import scala.util.Using

object Part1 {

  def hits(card: Card): Int =
    card.matching.size
    /*card.actualNumbers.foldLeft(0)( op = (a, b) =>
      if (card.winningNumbers.contains(b))  a + 1
      else a)*/

  def points(hits: Int): Int =
    Math.pow(2, hits - 1).toInt

  def global(list: Iterator[Card]): Int =
      list.foldLeft(0)((a, b) => a + points(hits(b)))


  def main(args: Array[String]): Unit = {


    Using(Source.fromFile("input.txt")) { source =>
      val cards = source.getLines().map(ReadingFile.card)
      println(global(cards))
    }.getOrElse(sys.error("Cannot open input file."))
  }

}

