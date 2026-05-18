object ReadingFile {

  def splitCard(line: String): Seq[String] =
    line.substring(line.indexOf(":") + 1).trim.split("\\|").map(_.trim)

  def seperateNumbers(line: String): Set[Int] =
    line.split("\\s+").map(_.toInt).toSet

  def card(line: String): Card =
    val numbers = splitCard(line).map(seperateNumbers)
    Card(numbers(0), numbers(1))
}
