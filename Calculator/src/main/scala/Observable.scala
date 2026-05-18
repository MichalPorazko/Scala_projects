trait Observable {

  private var listOfObservers: List[Observer] = List()


  def addObserver(observer: Observer): Unit =
    listOfObservers = observer :: listOfObservers

  def removeObserver(observer: Observer):Unit =
    listOfObservers = listOfObservers.filterNot(_ == observer)

  def notifyObservers(): Unit =
    listOfObservers.foreach(_.update())

}
