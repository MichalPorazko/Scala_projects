import scala.annotation.tailrec
import scala.collection.mutable
import scala.io.Source

object Part1 extends App{

  type Card = String
  lazy val bingo = "ZZZ"
  type Pair = (Card, Card)
  type Nodes = mutable.LinkedHashMap[Card,  Pair]
  extension (nodes: Nodes)
    def nodesToString: String =
      nodes.map((k, v) => s"$k = (${v._1}, ${v._2})").mkString("\n")

  type Instruction = String
  extension (pair: Pair)
    def left: Card = pair._1
    def right: Card = pair._2
    def returnSide(letter: Char): Card =
      letter match
        case 'L' => left
        case 'R' => right


  @tailrec
  def stepsNumber(instruction: Instruction, nodes: Nodes, card: Card, index: Int, traces: Map[Card, mutable.Buffer[Int]]): Int =
    println(card)
    val position = index % instruction.length
    val pair = nodes(card)
    val nextCard = pair.returnSide(instruction(position))
    if traces(card).contains(position) then
      throw Exception(s"Found cycle at ${card} selecting next card ${nextCard} from pair ${pair} with ${index} index with position ${position} ")
//    println(s"The index is ${index} and the current count is ${index + 1} with the current card ${card}")
    if nextCard.equals("ZZZ") then
      index + 1
    else
      println(s"the current traces ${traces.get(card)}")
      traces(card).append(position)
      stepsNumber(instruction, nodes, nextCard, index + 1, traces)

  def readDataFromInput(input: String): (String, Nodes) =
      val lines = input.linesIterator.filter(_.nonEmpty).toList
      val instruction = lines.head.trim
      val nodes = lines.tail.map(_.trim).flatMap { line =>
        val splitLine = line.split("=")
        if (splitLine.length == 2) {
          val card = splitLine(0).trim
          val pair = splitLine(1).trim.stripPrefix("(").stripSuffix(")").split(", ").map(_.trim)
          pair match {
            case Array(left, right) => Some(card -> (left, right))
            case _ => throw new IllegalArgumentException(s"Malformed pair on line: $line")
          }
        } else {
          throw new IllegalArgumentException(s"Line does not contain '=' or is malformed: $line")
        }
      }.to(mutable.LinkedHashMap)

      (instruction, nodes)


  val source = Source.fromFile("file").getLines().filter(_.nonEmpty).mkString("\n")
  println(source)
  val (inst, nodes) = readDataFromInput(source)
  val string = inst + "\n" + nodes.nodesToString
  println(string)
  println()
  println(source)

  assert(string == source)

  val traces = nodes.keys.map(k => (k, mutable.Buffer.empty[Int])).toMap

  val result =stepsNumber(inst, nodes, nodes.head._1, 0, traces)
  println(result)



}
