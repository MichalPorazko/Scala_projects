import javax.swing._
import java.awt._
import javax.swing.WindowConstants.{EXIT_ON_CLOSE}

class CalculatorGUI extends JFrame with Observable {
  setTitle("Kalkulator")
  setDefaultCloseOperation(EXIT_ON_CLOSE)
  setSize(300, 400)

  this.dispose()



  val displayLabel = new JLabel("0")
  displayLabel.setHorizontalAlignment(SwingConstants.RIGHT)
  displayLabel.setFont(new Font("Arial", Font.PLAIN, 24))
  add(displayLabel, BorderLayout.NORTH)

  val buttonPanel = new JPanel(new GridBagLayout())
  add(buttonPanel, BorderLayout.CENTER)

  setVisible(true)

  val buttons = Array(
    NumberButtons.number1, NumberButtons.number2, NumberButtons.number3, OperationButtons.Adding,
    NumberButtons.number4, NumberButtons.number5, NumberButtons.number6, OperationButtons.Subtracting,
    NumberButtons.number7, NumberButtons.number8, NumberButtons.number9, OperationButtons.Multiplying,
    NumberButtons.number0, OperationButtons.Comma, OperationButtons.Equals, OperationButtons.Dividing, OperationButtons.BackSpace
  )

  for (button <- buttons) {
    val buttonLabel = new JButton(button.sign)
    buttonLabel.addActionListener(_ => handleButtonPress(button))
    buttonPanel.add(buttonLabel)
  }

  def handleButtonPress(button: CalculatorButton): Unit = {
    button match {
      case nb: CalculatorButton with NumberButton => CalculatorController.onDigit(nb.sign.head)
      case OperationButtons.Adding => CalculatorController.onOperator(BinaryOperator.Plus)
      case OperationButtons.Dividing => CalculatorController.onOperator(BinaryOperator.By)
      case OperationButtons.Subtracting => CalculatorController.onOperator(BinaryOperator.Minus)
      case OperationButtons.Multiplying => CalculatorController.onOperator(BinaryOperator.Times)
      case OperationButtons.Equals => CalculatorController.onEqualsSign()
      case OperationButtons.Comma => CalculatorController.onDecimalSeparator()
      case OperationButtons.Delete => CalculatorController.onDelete()
      case OperationButtons.BackSpace => CalculatorController.backSpace()
    }

    displayLabel.setText(CalculatorController.getCurrentOperandValue().get)


  }

  override def notifyObservers(): Unit = super.notifyObservers()



}