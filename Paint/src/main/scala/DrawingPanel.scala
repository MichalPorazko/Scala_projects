import java.awt.image.BufferedImage
import java.awt.{BasicStroke, Color, FlowLayout, Graphics, LayoutManager}
import javax.swing.{JPanel, JTextArea}
import scala.swing.event.{MouseDragged, MousePressed, MouseReleased, UIElementResized}
import scala.swing.{ Dimension, FlowPanel, Graphics2D, Panel, Point, TextArea}

trait Canvas {
  def getGraphics(): Graphics2D
  def currentColor: Color
}

trait TextInputSupport{
  def activate(point: Point):Unit
  def deactivate():Unit
  def getText(): String
  def isTextActivated(): Boolean
}

class DrawingPanel() extends FlowPanel, Canvas, TextInputSupport {
  
  private var bitMap = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB)
  var currentColor: Color = Color.BLACK

  def getGraphics(): Graphics2D = 
  {
    graphics
  }
  private var graphics: Graphics2D = bitMap.createGraphics
  peer.setLayout(null)

  var drawingTool: DrawingTool = _

  def setDrawingTool(dt: DrawingTool): Unit =
    /*dt match{
      case x: RubberTool => setCurrColor(Color.WHITE)
      case _ => setCurrColor(currentColor)
    }*/
    drawingTool = dt

  def getDrawingTool(): DrawingTool =
    this.drawingTool


  def setBitMapAndGraphics(bufferedImage: BufferedImage): Unit =
    bitMap = bufferedImage
    graphics = bitMap.createGraphics()
    repaint()

  def getBitMap(): BufferedImage =
    bitMap

  def setCurrColor(color: Color): Unit =
    /*
    * drawingTool match
      case RubberTool => graphics.setColor(Color.WHITE)
      case _ => {
        currentColor = color
        graphics.setColor(color)}
    * */
    currentColor = color

    graphics.setColor(color)
    //textInput.peer.setFont()
    
  

  def setLineThickness(thickness: Float): Unit =
    graphics.setStroke(new BasicStroke(thickness))



  listenTo(this, mouse.clicks, mouse.moves)


  reactions += {
    case e: UIElementResized =>
      val newBitmap = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB)
      val newGraphics = newBitmap.createGraphics()
      newGraphics.drawImage(bitMap, 0, 0, null)
      newGraphics.setColor(Color.BLACK)
      graphics.dispose()
      bitMap = newBitmap
      graphics = newGraphics
      repaint()


    case MousePressed(_, point, _, _, _) =>
      drawingTool.onMousePress(point)
    case MouseDragged(_, point, _) =>
      drawingTool.onMouseDragged(point)
      repaint()
    case MouseReleased(_, point, _, _, _) =>
      drawingTool.onMouseReleased(point)
      repaint()
  }

  
  override def paint(g: Graphics2D): Unit =
    
    super.paint(g)
    g.drawImage(bitMap, 0, 0, null)
    g.setColor(graphics.getColor)
    g.setStroke(graphics.getStroke)
    drawingTool.paintToolLayer(g)



  val textInput: TextArea = new TextArea()
  this.contents += textInput
  textInput.peer.setBounds(200, 200, 200,300)
  textInput.visible = false
  textInput.text = "napis"



  override def activate(point: Point): Unit =
    textInput.requestFocus()
    textInput.peer.setBounds(point.x, point.y, 200, 300)
    textInput.visible = true
    textInput.text = ""

  override def deactivate(): Unit =
    textInput.visible = false

  override def getText(): String =
    textInput.text

  override def isTextActivated(): Boolean =
    textInput.visible
}
