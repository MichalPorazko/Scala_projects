case class Card(winningNumbers: Set[Int], actualNumbers: Set[Int]){

  def matching: Set[Int] = actualNumbers.intersect(winningNumbers)
  def hasMatchingNumbers: Boolean = matching.nonEmpty
}
