import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class LazyListTransformationTest extends AnyFlatSpec with Matchers {
  "A string 'LRRL'" should "be transformed into a LazyList of Strings correctly" in {
    val instructionString = "LRRL"
    val expectedLazyList = LazyList("L", "R", "R", "L")

    val transformedLazyList = instructionString.map(_.toString).to(LazyList)

}
}
