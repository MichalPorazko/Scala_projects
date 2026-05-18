import java.text.NumberFormat
import java.util.Locale
import scala.swing._
import scala.swing.Button

abstract class CalculatorButton(val sign:String) extends Button

trait NumberButton
trait OperationButton
trait EnterButton

object OperationButtons {

  case object Adding extends CalculatorButton("+") with OperationButton
  case object Dividing extends CalculatorButton("/") with OperationButton
  case object Subtracting extends CalculatorButton("-") with OperationButton
  case object Multiplying extends CalculatorButton("x") with OperationButton
  case object Equals extends CalculatorButton("=") with OperationButton
  case object Comma extends CalculatorButton(".") with OperationButton
  case object Delete extends CalculatorButton("C") with OperationButton
  case object BackSpace extends CalculatorButton("⌫ ") with OperationButton

}

object NumberButtons {

  case object number0 extends CalculatorButton("0") with NumberButton
  case object number1 extends CalculatorButton("1") with NumberButton
  case object number2 extends CalculatorButton("2") with NumberButton
  case object number3 extends CalculatorButton("3") with NumberButton
  case object number4 extends CalculatorButton("4") with NumberButton
  case object number5 extends CalculatorButton("5") with NumberButton
  case object number6 extends CalculatorButton("6") with NumberButton
  case object number7 extends CalculatorButton("7") with NumberButton
  case object number8 extends CalculatorButton("8") with NumberButton
  case object number9 extends CalculatorButton("9") with NumberButton

}

object EnterButton{

  case object enterButton extends CalculatorButton("Enter") with EnterButton
}





