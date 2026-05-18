type RangeFunction = (Long, Long, Long)

object RangeFunction{

  def apply(destination: Long, source: Long , length: Long): RangeFunction =
    new RangeFunction(destination, source, length)
}