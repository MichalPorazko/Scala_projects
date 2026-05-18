import javax.swing.{JButton, JFileChooser, JFrame, JLabel, JOptionPane, JPanel}
import java.awt.{BorderLayout, FlowLayout}
import java.awt.event.ActionEvent
import java.io.IOException
import javax.swing.WindowConstants.EXIT_ON_CLOSE
import scala.util.Try


class UiWindow extends JFrame {

  setTitle("Wybór Wykresu")
  setSize(800, 600) // Zwiększony rozmiar okna
  setDefaultCloseOperation(EXIT_ON_CLOSE)
  setLayout(new BorderLayout())

  val label = new JLabel("Wybierz wykres")
  add(label, BorderLayout.NORTH) // Dodanie etykiety na górze

  val backButton = new JButton("Powrót") // Przycisk powrotu
  backButton.addActionListener((e: ActionEvent) => resetToInitialState())
  backButton.setVisible(false) // Na początku przycisk powrotu nie jest widoczny

  addGraphPanel(Seq.empty)

  // Przycisk
  val button = new JButton("Wybierz plik")
  button.addActionListener((e: ActionEvent) => {
    // Logika otwierania okna dialogowego
    val fileChooser = new JFileChooser()
    val result = fileChooser.showOpenDialog(this)

    if (result == JFileChooser.APPROVE_OPTION) {
      val selectedFile = fileChooser.getSelectedFile.getAbsolutePath
      try {
        val coordinates = FileReader.getCordinates(selectedFile)
        addGraphPanel(coordinates)
      } catch {
        case e: CoordinatesError => showErrorWindow(e.message)
        case e: IOException => showErrorWindow("Błąd odczytu pliku")
      }
    }
  })
  add(button, BorderLayout.SOUTH) // Dodanie przycisku na dole

  setVisible(true)

  def resetToInitialState(): Unit = {
    getContentPane.removeAll()
    getContentPane.add(label, BorderLayout.NORTH)
    getContentPane.add(button, BorderLayout.SOUTH)
    backButton.setVisible(false) // Przycisk powrotu staje się niewidoczny
    revalidate()
    repaint()
  }

  def showErrorWindow(msg: String): Unit = {
    JOptionPane.showMessageDialog(this, msg)
    resetToInitialState() // Po wyświetleniu błędu powrót do stanu początkowego
  }

  def addGraphPanel(list: Seq[(Double, Double)]): Unit = {
    val graphPanel = new GraphPanel(list)
    getContentPane.removeAll()
    getContentPane.add(graphPanel, BorderLayout.CENTER)
    getContentPane.add(backButton, BorderLayout.SOUTH) // Dodanie przycisku powrotu
    backButton.setVisible(true) // Ustawienie przycisku powrotu jako widoczny
    revalidate()
    repaint()
  }


}

// Możesz uruchomić okno w ten sposób:
object App {
  def main(args: Array[String]): Unit = {
    new UiWindow()
  }
}
