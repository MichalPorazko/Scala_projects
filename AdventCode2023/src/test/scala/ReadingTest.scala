

import Part1.{Nodes, readDataFromInput}

import scala.collection.mutable
import scala.io.Source

class ReadingTest extends munit.FunSuite {

  test("testing creating the node"){

    val input = "AAA = (BBB, CCC)\nBBB = (DDD, EEE)\nCCC = (ZZZ, GGG)\nDDD = (DDD, DDD)\nEEE = (EEE, EEE)\nGGG = (GGG, GGG)\nZZZ = (ZZZ, ZZZ)"
    val lines = input.split("\n").toList

    val line = "AAA = (BBB, CCC)"
    val splitLine = line.split("=").map(_.trim)
    println(splitLine.toList)
    val card = splitLine(0)
    println(card)
    val pair = splitLine(1).stripPrefix("(").stripSuffix(")").split(", ").map(_.trim)
    println(pair.toList)
    val pairRepaired = pair match
      case Array(left, right) => (left, right)
    println(pairRepaired)
    println(card -> pairRepaired)

    val nodes = lines.map { line =>
      val splitLine = line.split("=").map(_.trim)
      val card = splitLine(0).trim
      val pair = splitLine(1).stripPrefix("(").stripSuffix(")").split(", ").map(_.trim) match {
        case Array(left, right) => (left, right)
      }
      println(s"The pair is ${pair}")
      card -> pair
    }.toMap
    println(nodes)


  }
  test("testing the stepsNumber method"){

    val instruction: Part1.Instruction = "LLR"

    /**
     * Since Card is just an alias for String, and Pair is an alias for (String, String),
     * Scala will correctly recognize "AAA", "BBB", etc., as Card, and ("BBB", "BBB"), etc., as Pair.
     *
     * So whenever you're using a String,
     * Scala treats it as a Card when it's used in the context where Card is expected.
     *
     * type Pair = (Card, Card) means that Pair is
     * just another name for a tuple of two Card values,
     * which is effectively a tuple of two String values.
     * */
    val nodes: Nodes = mutable.LinkedHashMap(
      "AAA" -> ("BBB", "BBB"),
      "BBB" -> ("AAA", "ZZZ"),
      "ZZZ" -> ("ZZZ", "ZZZ")
    )

    val pair = ("BBB", "BBB")
    val traces = nodes.keys.map(k => (k, mutable.Buffer.empty[Int])).toMap
    val result = Part1.stepsNumber(instruction, nodes, "AAA", 0, traces)
    println(s"The number of steos are ${result}")



  }

  test("testing the stepsNumber method based on the data read from the file") {

    val source = Source.fromFile("testFile").getLines().filter(_.nonEmpty).mkString("\n")
    val (inst, nodes) = readDataFromInput(source)
    val traces = nodes.keys.map(k => (k, mutable.Buffer.empty[Int])).toMap
    val result = Part1.stepsNumber(inst, nodes, "AAA", 0, traces)
    println(s"The number of steos are ${result}")

  }


}
