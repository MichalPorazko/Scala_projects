import java.awt.{BasicStroke, Color, Component, Graphics, Graphics2D, Stroke}
import java.lang
import javax.swing.Icon
import scala.swing.Point


trait DrawingTool {
  def onMousePress(point:Point):Unit = {}
  def onMouseDragged(point:Point):Unit = {}
  def onMouseReleased(point: Point):Unit = {}
  def onKeyPressed(z: Char): Unit = {}
  def paintToolLayer(folia: Graphics2D): Unit = {}
  def setLineThickness(thickness: Float): Unit = {}
}

class StraightLineTool(canvas: Canvas) extends DrawingTool:

  private var startPoint: Option[Point] = None
  private var endPoint: Option[Point] = None

  override def paintToolLayer(folia: Graphics2D): Unit = {
    for{
      start <- startPoint
      end <- endPoint
    } do {
      folia.drawLine(start.x, start.y, end.x, end.y)
    }
  }

  override def onMousePress(point: Point): Unit =
    startPoint = Some(point)

  override def onMouseDragged(point: Point): Unit = (
    endPoint = Some(point)
  )

  override def onMouseReleased(point: Point): Unit =
    startPoint match {
      case Some(sp) => canvas.getGraphics().drawLine(sp.x, sp.y, point.x, point.y)
      case None =>
    }
    startPoint = None
    endPoint = None

class RectangleTool(canvas: Canvas) extends DrawingTool:
  private var startPoint: Option[Point] = None
  private var endPoint: Option[Point] = None

  override def onMousePress(point: Point): Unit =
    startPoint = Some(point)

  override def paintToolLayer(folia: Graphics2D): Unit =
    for {
      start <- startPoint
      end <- endPoint
    } do{
      val width = Math.abs(end.x - start.x)
      val height = Math.abs(end.y - start.y)
      val x = Math.min(end.x, start.x)
      val y = Math.min(end.y, start.y)
      folia.drawRect(x, y, width, height)}

  override def onMouseDragged(point: Point): Unit = {
    endPoint = Some(point)
  }

  override def onMouseReleased(point: Point): Unit = {
    startPoint match {
      case Some(sp) =>
        val width = Math.abs(point.x - sp.x)
        val height = Math.abs(point.y - sp.y)
        val x = Math.min(point.x, sp.x)
        val y = Math.min(point.y, sp.y)
        canvas.getGraphics().drawRect(x, y, width, height)
      case None =>
    }
    endPoint = None
    startPoint = None
  }

  override def setLineThickness(thickness: Float): Unit =
    canvas.getGraphics().setStroke(new BasicStroke(thickness))

class CurveLineTool(canvas: Canvas) extends DrawingTool:

  private var points: List[Point] = List()

  override def onMousePress(point: Point): Unit = {
    points = point :: points
  }

  override def onMouseDragged(point: Point): Unit = {
    points = point :: points

    if (points.size > 1)
      val recentPoint = points.tail.head
      canvas.getGraphics().drawLine(recentPoint.x, recentPoint.y, points.head.x, points.head.y)

  }

  override def onMouseReleased(point: Point): Unit = {
    if (points.size == 1)
      val singlePoint = points.head
      canvas.getGraphics().fillOval(singlePoint.x - 2, singlePoint.y - 2, 4, 4)
    points = List()
  }

class RubberTool(canvas: Canvas) extends CurveLineTool(canvas){

  private var startPoint: Option[Point] = None


  override def onMousePress(point: Point): Unit = {
    startPoint = Some(point)
    canvas.getGraphics().setColor(Color.WHITE)
    canvas.getGraphics().fillOval(point.x - 5, point.y - 5, 10, 10)
    canvas.getGraphics().setColor(canvas.currentColor)
  }

  override def onMouseDragged(point: Point): Unit =
   startPoint.foreach{ sp =>
     val g = canvas.getGraphics()
     canvas.getGraphics().setColor(Color.WHITE)
     g.drawLine(sp.x, sp.y, point.x, point.y)
     canvas.getGraphics().setColor(canvas.currentColor)
   }
   startPoint = Some(point)

  override def onMouseReleased(point: Point): Unit = {
    startPoint = None
  }

}

class CircleTool(canvas: Canvas) extends DrawingTool:
  private var startPoint: Option[Point] = None
  private var endPoint: Option[Point] = None

  override def onMousePress(point: Point): Unit =
    startPoint = Some(point)

  override def paintToolLayer(folia: Graphics2D): Unit =
    for{
      start <- startPoint
      end <- endPoint
    } do {
      val radius = Math.sqrt(Math.pow(start.x - end.x, 2) + Math.pow(start.y - end.y, 2)).toInt
      folia.drawOval(start.x - radius, start.y - radius, radius * 2, radius * 2)
    }

  override def onMouseDragged(point: Point): Unit = {
    endPoint = Some(point)
  }

  override def onMouseReleased(point: Point): Unit = {
    startPoint match {
      case Some(sp) =>
        val radius = Math.sqrt(Math.pow(sp.x - point.x, 2) + Math.pow(sp.y - point.y, 2)).toInt
        canvas.getGraphics().drawOval(sp.x - radius, sp.y - radius, radius * 2, radius * 2)
      case None =>
    }
    startPoint = None
    endPoint = None
  }

class LineIcon(thickness: Int) extends Icon {
  override def getIconWidth: Int = 50
  override def getIconHeight: Int = 10

  override def paintIcon(c: Component, g: Graphics, x: Int, y: Int): Unit = {
    g.setColor(java.awt.Color.BLACK)
    g.asInstanceOf[Graphics2D].setStroke(new BasicStroke(thickness))
    g.drawLine(x, y + getIconHeight / 2, x + getIconWidth, y + getIconHeight / 2)
  }
}

class TextTool(textInputSupport: TextInputSupport, canvas: Canvas) extends DrawingTool {

  var pointChoosen: Option[Point] = None

  override def onMouseReleased(point: Point): Unit =

    if textInputSupport.isTextActivated() then
      textInputSupport.deactivate()
      pointChoosen.foreach(point =>
        val editedText = textInputSupport.getText().split('\n')
        var y = point.y
        editedText.foreach( line => {
         canvas.getGraphics().drawString(line, point.x, y)
          y += canvas.getGraphics().getFontMetrics.getHeight
        })
      )
    else
      pointChoosen = Some(point)
      pointChoosen.foreach(p => textInputSupport.activate(p))


}



