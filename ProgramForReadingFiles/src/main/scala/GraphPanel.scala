import java.awt.{Color, Graphics, Graphics2D}
import javax.swing.JPanel

class GraphPanel(list: Seq[(Double, Double)]) extends JPanel {


  private val maxValPair: (Double, Double) = findMaxValuePair(list)

  private val minValPair = findMinValuePair(list)

  private val maxArg = findMaxArg(list)

  private val minArg = findMinArg(list)

  val maxVal = maxValPair._2
  val minVal = minValPair._2

  def findMinArg(list: Seq[(Double, Double)]): Double =
    list.minBy(_._1)._1

  def findMaxArg(list: Seq[(Double, Double)]): Double =
    list.maxBy(_._1)._1

  def findMaxValuePair(list: Seq[(Double, Double)]): (Double, Double) =
    list.maxBy(_._2)

  def findMinValuePair(list: Seq[(Double, Double)]): (Double, Double) =
    list.minBy(_._2)

  private def toPixelX(x: Double): Int = ((x - minArg) / (maxArg - minArg) * (getWidth - 2 * marginX) + marginX).toInt

  private def toPixelY(y: Double): Int = ((maxVal - y) / (maxVal - minVal) * (getHeight - 2 * marginY) + marginY).toInt

  def toRealX(pixelX: Int): Double = minArg + pixelX.toDouble / getWidth * (maxArg - minArg)

  def toRealY(pixelY: Int): Double = maxVal - pixelY.toDouble / getHeight * (maxVal - minVal)

  def findline(goLeft: Boolean): (Double, Double) = {
    val step = if ( !goLeft)  -1 else 1
    val endIndex = if (!goLeft) 0 else list.size
    var xIndex: Int = list.map(p => p._1).indexOf(maxValPair._1)
    val searchValue = maxVal / 2

    //xIndex != endIndex && xIndex + step >= 0 && xIndex + step < list.size
    while (xIndex + step != endIndex) {
      val firstPoint = list(xIndex)
      val secondPoint = list(xIndex + step)

      if ((firstPoint._2 > searchValue && secondPoint._2 < searchValue) || (firstPoint._2 < searchValue && secondPoint._2 > searchValue)){
        val a = (firstPoint._2 - secondPoint._2) / (firstPoint._1 - secondPoint._1)
        val b = firstPoint._2 - a * firstPoint._1
        return (a, b)
      xIndex += step
    }}
    throw new RuntimeException("Other functions have been changed so findLine does not work")
  }

  def findFWHMx(parametrs: (Double, Double)): Double =
    ((maxVal / 2 - parametrs._2) / parametrs._1)

  val marginX = 50
  val marginY = 50

  val x1 = findFWHMx(findline(false))
  val x2 = findFWHMx(findline(true))

  def drawGraph(g: Graphics2D): Unit = {

    val zeroPixelX = toPixelX(0)
    val zeroPixelY = toPixelY(0)
    g.setColor(Color.GREEN)
    g.drawLine(marginX, getHeight - marginY, getWidth, getHeight - marginY)
    g.drawLine(marginX, 0, marginX, getHeight - marginY)

    g.setColor(Color.BLACK)
    g.drawLine(0, zeroPixelY, getWidth, zeroPixelY)
    g.drawLine(zeroPixelX, 0, zeroPixelX, getHeight)

    var oldPixelX = toPixelX(minArg)
    var oldPixelY = toPixelY(minVal)


    list.foreach { pair =>
      g.drawLine(oldPixelX, oldPixelY, toPixelX(pair._1), toPixelY(pair._2))
      oldPixelX = toPixelX(pair._1)
      oldPixelY = toPixelY(pair._2)
    }


  }

  override def paintComponent(g: Graphics): Unit = {
    super.paintComponent(g)
    val g2d = g.asInstanceOf[Graphics2D]

    drawGraph(g2d)
    drawExtremaPoints(g2d)
    drawFWHM(g2d)
  }

  def drawExtremaPoints(g: Graphics2D): Unit = {
    val maxValX = maxValPair._1
    val minValX = minValPair._1

    // Rysowanie punktów ekstremalnych
    val maxPointX = toPixelX(maxValX)
    val maxPointY = toPixelY(maxVal)
    val minPointX = toPixelX(minValX)
    val minPointY = toPixelY(minVal)

    g.setColor(Color.RED)
    g.fillOval(maxPointX - 5, maxPointY - 5, 10, 10)
    g.fillOval(minPointX - 5, minPointY - 5, 10, 10)

    // Dodanie etykiet do punktów ekstremalnych
    g.drawString(f"Max: $maxVal%.2f (x=$maxValX)", maxPointX + 10, maxPointY)
    g.drawString(f"Min: $minVal%.2f (x=$minValX)", minPointX + 10, minPointY)

    // Zaznaczanie na osi X wartości X dla maksymalnego i minimalnego Y
    g.setColor(Color.BLUE)
    g.drawLine(maxPointX, toPixelY(0), maxPointX, toPixelY(0) + 5)
    g.drawString(f"$maxValX", maxPointX, toPixelY(0) + 20)

    g.drawLine(minPointX, toPixelY(0), minPointX, toPixelY(0) + 5)
    g.drawString(f"$minValX", minPointX, toPixelY(0) + 20)


  }

  def drawFWHM(g: Graphics2D): Unit ={
    val pixelYHalfMax = toPixelY(maxVal / 2)
    val pixelX1 = toPixelX(x1)
    val pixelX2 = toPixelX(x2)

    // Rysowanie linii FWHM

    g.setColor(Color.BLUE)
    g.drawLine(pixelX1, pixelYHalfMax, pixelX2, pixelYHalfMax)

    // Etykietowanie punktów FWHM
    g.drawString(f"FWHM Start: $x1%.2f", pixelX1, pixelYHalfMax - 5)
    g.drawString(f"FWHM End: $x2%.2f", pixelX2, pixelYHalfMax - 5)

    // Rysowanie pionowych linii w punktach FWHM
    g.drawLine(pixelX1, pixelYHalfMax, pixelX1, getHeight - marginY)
    g.drawLine(pixelX2, pixelYHalfMax, pixelX2, getHeight - marginY)
  }
}
