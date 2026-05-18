package org.example.game.entities

//Symbol is now a sealed trait:
//you want to prevent your users from defining new instances.
sealed trait Symbol {

  protected def beats: List[Symbol]

  def wins(other: Symbol): Boolean =
    beats.contains(other)

}

/*
  * case object USD
    A case object is a regular singleton object for which
    the compiler automatically overrides some useful methods;

    it redefines the implementation of toString
    * to produce a human-readable string representation.
    *  For a regular object, toString returns its name
    * followed by the hexadecimal encoding of its memory address.
    * This looks similar to “USD@7b36aa0c.”
    *  When dealing with a case object, the compiler changes
    *  the definition for toString to return only the object name:

USD.toString     // returns "USD"
  * */

case object Rock extends Symbol {
  protected val beats = List(Lizard, Scissors)
}


case object Paper extends Symbol {
  protected val beats = List(Rock, Spock)
}


case object Scissors extends Symbol {
  protected val beats = List(Paper, Lizard)
}


case object Lizard extends Symbol {
  protected val beats = List(Spock, Paper)
}

case object Spock extends Symbol {
  protected val beats = List(Scissors, Rock)
}

object Symbol{

  def fromString(text: String): Symbol =
    text.trim.toLowerCase match {
      case "rock" => Rock
      case "paper" => Paper
      case "scissors" => Scissors
      case "lizard" => Lizard
      case "spock" => Spock
      case unknown =>
        val errorMsg = s"Unknown symbol $unknown. " +
          "Please pick a valid symbol [Rock, Paper, Scissors, Lizard, Spock]"
        throw new IllegalArgumentException (errorMsg)
    }
}
