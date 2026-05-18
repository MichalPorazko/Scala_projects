package org.example.game.entities

class Player(name:String, val symbol: Symbol) {


  /*Visibility of Fields:
    name:
    This parameter is not accessible outside the class because
     it is not declared with val or var.
     It is treated as a private field and
     can only be used internally within the class.

    symbol:
    This parameter is declared as val,
    which makes it a publicly readable field.
    However, since it is a val,
    it cannot be modified after the object
    is instantiated (it is immutable).*/

  //because it a class
  //not a case class
  //Your program should always produce a
  // human-readable representation when converting an instance of Player to text.
  // If you fail to do so,
  // the compiler will use toString‘s default definition
  // and return a string similar to Player@12345.
  override def toString: String = s"Player $name with symbol $symbol"

}

/*
 you define a general method in the companion object how
  to convert a String into a Player
* */
object Player{
  
  def apply(text: String): Player =
    text.split(":", 2) match {
      case Array(player, symbol) =>
        new Player (player.trim, Symbol.fromString (symbol) )
      case _ =>
        val errorMsg = s"Invalid player $text. " +
          "Please, use the format <name>: <symbol>"
        throw new IllegalArgumentException(errorMsg)
    }

  def unapply(player: Player): Option[Symbol] =
    Some(player.symbol)
    
}
