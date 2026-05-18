import java.awt.Color
import javax.imageio.ImageIO
import javax.swing.{JRadioButton, JToggleButton}
import scala.swing.*
import scala.swing.Color
import scala.swing.event.ButtonClicked

object Paint extends SimpleSwingApplication {
  

  def top: Frame = new MainFrame {

    val drawingPanel = new DrawingPanel() {
      preferredSize = new Dimension(600, 400)
      background = Color.WHITE
    }

    case object CircleButton extends Button("Koło")
    case object RectangleButton extends Button("Kwadrat")
    case object StraightLineButton extends Button("Prosta linia")
    case object CurveLineButton extends Button("krzywa linia")
    case object TextButton extends Button("tekst")
    case object RubberButton extends Button("guma")


    val toolButtons = List(CircleButton, RectangleButton, StraightLineButton, CurveLineButton, TextButton, RubberButton)

    val straightLineTool = new StraightLineTool(this.drawingPanel)
    val curveLineTool = new CurveLineTool(this.drawingPanel)
    val rectangleTool = new RectangleTool(this.drawingPanel)
    val circleTool = new CircleTool(this.drawingPanel)
    val rubberTool = new RubberTool(this.drawingPanel)
    val textTool = new TextTool(this.drawingPanel, this.drawingPanel)

    drawingPanel.setDrawingTool(straightLineTool)

    toolButtons.foreach(listenTo(_))
    reactions += {
      case ButtonClicked(source) => drawingPanel.setDrawingTool(
        source match {
        case RectangleButton => rectangleTool
        case StraightLineButton => straightLineTool
        case CurveLineButton => curveLineTool
        case CircleButton => circleTool
        case RubberButton => rubberTool
        case TextButton => textTool

        })
    }



    val colorChooserButton = new Button("wybierz kolor"){
      reactions += {
        case ButtonClicked(_) =>
          val chosenColor = ColorChooser.showDialog(drawingPanel, "Choose a Color", Color.BLACK)
          chosenColor.foreach(drawingPanel.setCurrColor)
      }}

    val saveButton = new Button("zapisz"){
      reactions += {
        case ButtonClicked(_) =>
          val chooser = new FileChooser()
          if (chooser.showSaveDialog(null) == FileChooser.Result.Approve) {
            ImageIO.write(drawingPanel.getBitMap(), "png", chooser.selectedFile)
          }
      }
    }

    val loadButton = new Button("załaduj"){
      reactions += {
        case ButtonClicked(_) =>
          val chooser = new FileChooser()
          if (chooser.showOpenDialog(null) == FileChooser.Result.Approve) {
            val image = ImageIO.read(chooser.selectedFile)
            drawingPanel.setBitMapAndGraphics(image)
          }
      }}

    val lineThicknessButton = new Button("Grubość lini") {
        reactions += {
          case ButtonClicked(button) =>
            thicknessPopup.show(this, 0, button.bounds.height)
        }
    }

    val thicknessPopup = new PopupMenu {
      contents += new MenuItem(new Action("Thin Line") {
        override def apply(): Unit =
          drawingPanel.setLineThickness(1)
      }) {
        icon = new LineIcon(1)
      }
      contents += new MenuItem(new Action("Medium Line") {
        override def apply(): Unit =
          drawingPanel.setLineThickness(3)
      }) {
        icon = new LineIcon(3)
      }
      contents += new MenuItem(new Action("Gruba linia") {
        override def apply(): Unit =
          drawingPanel.setLineThickness(5)
      }) {
        icon = new LineIcon(5)
      }
      contents += new MenuItem(new Action("Bardzo gruba linia") {
        override def apply(): Unit =
          drawingPanel.setLineThickness(7)
      }) {
        icon = new LineIcon(7)
      }

    }


    contents = new BoxPanel(Orientation.Vertical) {
      contents += new FlowPanel {
        contents ++= toolButtons
      }
      contents += new FlowPanel {
        contents += colorChooserButton
        contents += saveButton
        contents += loadButton
        contents += lineThicknessButton
      }
      contents += drawingPanel
    }

    size = new Dimension(800, 600)
  }

}
