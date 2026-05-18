object JSONDeriving {

  trait PersonSerializer[T]{
    def serialize(person: T): String
    def deserialize(person: String): T
  }

  case class Person(name: String, age: Int)

  given personSerializer: PersonSerializer[Person] with {
    def serialize(person: Person): String = ???
    def deserialize(person: String): Person = ???
  }



}
