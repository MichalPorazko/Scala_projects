abstract class BinaryOperator(val name:String,val function:(Double,Double) => Double)


object BinaryOperator
{
    case object Plus extends BinaryOperator("+",(a,b) => a + b)
    case object Minus extends BinaryOperator("-",(a,b) => a - b)
    case object Times extends BinaryOperator("x",(a,b) => a * b)
    case object By extends BinaryOperator("/",(a,b) => a / b)
}
