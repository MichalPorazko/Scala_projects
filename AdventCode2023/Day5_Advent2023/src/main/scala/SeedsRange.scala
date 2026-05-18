type SeedsRange = (Long, Long)

object SeedsRange{

  def apply(start: Long , length: Long): SeedsRange =
    new SeedsRange(start, length)
}