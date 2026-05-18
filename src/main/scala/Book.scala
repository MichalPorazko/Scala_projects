import scala.math.Ordering.Implicits.infixOrderingOps

case class BookDetails(genre: String, pageCount: Int, publisher: Publisher)

case class Publisher(name: String, year:Int, publisherInfo: PublisherInfo){
  def getPublisherName: String = publisherInfo.name
  def getPublisherIsModern: Boolean = publisherInfo.isModern
}

object Publisher {

  def unapply(publisher: Publisher): Option[(String, Boolean)] =
    Some(publisher.publisherInfo.name, publisher.publisherInfo.isModern)
}

case class PublisherInfo(name: String, isModern: Boolean)

class Book(title: String, author: String, year: Int, private val bookDetails: BookDetails) {

  //I created those two methods to sustain the principles of privacy
  // by not exposing the Book instance directly but rather the specific information needed for the analyse
  def getGenre: String = bookDetails.genre
  def getPageCount: Int = bookDetails.pageCount
  def getPublisher: Publisher = bookDetails.publisher
  def getYear: Int = year
  def getTitle: String = title

}

object Book {

  def unapply(book: Book): Option[(String, Int, Int, Boolean)] =
    Some((book.getTitle, book.getYear, book.bookDetails.pageCount, book.bookDetails.publisher.getPublisherIsModern))

//  def unapply1(book: Book): Option[(String, Int)] =
//    Some((book.getGenre, book.bookDetails.pageCount))  
}

sealed trait Category
case object Fiction extends Category
case object NonFiction extends Category
case object Short extends Category
case object Long extends Category
case object ModernPublisher extends Category

val fictionGenre = "Fiction"
val pageCountThreshold: Int = 300



//not using the unapply method
def categorize(book: Book): Set[Category] =
  val genre = book.getGenre
  val pageCount = book.getPageCount
  (genre, pageCount) match
    case (fiction, pageCount) if pageCount >= pageCountThreshold && fiction == fictionGenre => Set(Fiction, Long)
    case (_, pageCount) if pageCount >= pageCountThreshold => Set(Long)
    case (fiction, _) if fiction == fictionGenre => Set(Fiction)
    case _ => Set(NonFiction, Short)


//using the unapply method
def analyse(book: Book): Set[Category] =
  book match
    case Book(fiction, pageCount) if pageCount>= pageCountThreshold && fiction == fictionGenre => Set(Fiction, Long)
    case Book(_, pageCount) if pageCount>= pageCountThreshold => Set(Long)
    case Book(fiction, _) if fiction == fictionGenre => Set(Fiction)
    case _ => Set(NonFiction, Short)

def analyseSecondVersion(book: Book): Set[Category] = book match 
  case Book(`fictionGenre`, pageCount, _, isModern) if pageCount >= pageCountThreshold && isModern => Set(Fiction, Long, ModernPublisher)
  case Book(`fictionGenre`, _, _, _) => Set(Fiction)
  case Book(_, pageCount, _, isModern) if pageCount >= pageCountThreshold && isModern => Set(Long, ModernPublisher)
  case Book(_, _, _, isModern) if isModern => Set(ModernPublisher)
  case _ => Set(NonFiction, Short)



val penguinPublisher = Publisher("Penguin", 2023, PublisherInfo("Penguin", true))

val books = List(
  new Book("1984", "George Orwell", 1949, BookDetails("Dystopian", 328, penguinPublisher)),
  new Book("To Kill a Mockingbird", "Harper Lee", 1960, BookDetails("Fiction", 281, penguinPublisher)),
  new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, BookDetails("Fiction", 180, penguinPublisher)),
  new Book("A Brief History of Time", "Stephen Hawking", 1988, BookDetails("Non-Fiction", 212, penguinPublisher))
)




object MainBook {


  def main(args: Array[String]): Unit = {
    books.foreach{ book =>
      val bookDetails = analyse(book)
      println(bookDetails)
    }

  }
}







