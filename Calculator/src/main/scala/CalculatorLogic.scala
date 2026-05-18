
object CalculatorLogic
{
    private var state: CalculatorState = CalculatorState("0", Option.empty, Option.empty)

    private def stringToDouble(s: String): Double =
        s.toDouble

    private def doubleToString(v: Double): String =
        v.toString

    def compute(): String =
        state.currentOperator match {
            case None => state.firstOperand
            case Some(operator) => doubleToString(operator.function.apply(
                stringToDouble(state.firstOperand),
                stringToDouble(state.secondOperand.getOrElse(state.firstOperand)))
            )
        }

    def resetSecondOperand(): Unit =
    {
        state = state.copy(secondOperand = None)
    }

    def setCurrentOperator(operator: BinaryOperator): Unit = {
        state = state.copy(currentOperator = Some(operator))
    }

    def getState():CalculatorState =
    {
        state
    }

    def setInitialState(): CalculatorState =
        getState().copy("0", Option.empty, Option.empty)




    def setFirstOperand(newVal:String) = state = state.copy(firstOperand = newVal)
    def setSecondOperand(newVal:String) = state = state.copy(secondOperand = Some(newVal))


}
