import scala.swing.TextField

abstract class QuadraticField (val sign: String) extends TextField{

  def getFieldText:Option[String] = {
    this.text match {
      case "" => None
      case text => Some(text)
    }
  }
}

object QuadraticFields {
  case object QuadraticCoefficient extends QuadraticField("a")
  case object LinearCoefficient extends QuadraticField("b")
  case object ConstantTerm extends QuadraticField("c")


}
