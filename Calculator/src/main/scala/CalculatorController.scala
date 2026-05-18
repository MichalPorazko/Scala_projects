import scala.language.postfixOps



object CalculatorController
{
    var isCurrentOperandFirst = true
    var shouldClearView = false

    def getCurrentOperandValue() :Option[String]=
        if (isCurrentOperandFirst)
            Some(CalculatorLogic.getState().firstOperand)
        else CalculatorLogic.getState().secondOperand

    def modifyCurrentOperand(mfun: Option[String] => String) =
    {
        if (isCurrentOperandFirst)
            CalculatorLogic.setFirstOperand(mfun(Some(CalculatorLogic.getState().firstOperand)))
        else CalculatorLogic.setSecondOperand(mfun.apply(CalculatorLogic.getState().secondOperand))
    }
    def setCurrentOperandValue(newValue:String):Unit =
    {
        modifyCurrentOperand(_ => newValue);
    }


    def moveToFirstOperand() =
    {
        shouldClearView = true
        isCurrentOperandFirst = true
        CalculatorLogic.resetSecondOperand()
    }

    def moveToSecondOperand():Unit = {
        shouldClearView = true
        isCurrentOperandFirst = false
        CalculatorLogic.setSecondOperand("0")
    }

    def onOperator(operator: BinaryOperator):Unit = {

        if(CalculatorLogic.getState().secondOperand.isEmpty){
            moveToSecondOperand()
        }else{
            computeIntoFirstOperand()
            shouldClearView = true

        }

        CalculatorLogic.setCurrentOperator(operator)
        //TODO: update view
    }

    def onDigit(digit: Char): Unit = {
        // clear
        if(shouldClearView)
        {
            setCurrentOperandValue("")
            shouldClearView = false

        }
        modifyCurrentOperand(old => old.get + digit)
        //TODO: update view
    }

    def onDecimalSeparator(): Unit = {

        if (getCurrentOperandValue().get.contains("."))
            return
        modifyCurrentOperand(old => old.get + ".")


        //TODO: update view
    }

    def onEqualsSign(): Unit = {
        computeIntoFirstOperand()
        moveToFirstOperand()


        //TODO: update view
    }

    def onDelete(): Unit = {
        CalculatorLogic.setInitialState()

    }

    def backSpace(): Unit = {
        if(getCurrentOperandValue().get.length == 1)
           if (isCurrentOperandFirst)
               setCurrentOperandValue("0")
           else
               CalculatorLogic.resetSecondOperand()
        else
            modifyCurrentOperand(old => old.get.dropRight(1))

    }

    def quadraticFuntion

    private def computeIntoFirstOperand(): Unit = {
        CalculatorLogic.setFirstOperand(CalculatorLogic.compute())
    }
}
