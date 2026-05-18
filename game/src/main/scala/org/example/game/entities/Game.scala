package org.example.game.entities

import cats.effect.IO
import io.circe.generic.semiauto.deriveEncoder
import io.circe.Encoder
import org.http4s.EntityEncoder
import org.http4s.circe.jsonEncoderOf

case class Game(playerA: Player, playerB: Player) {

  private val winner: Option[Player] =
    (playerA, playerB) match
      case (pA@Player(sA), Player(sB)) if sA.wins(sB) => Some(pA)
      case (Player(sA), pB@Player(sB)) if sB.wins(sA) => Some(pB)
      case _ => None // it's a draw!

  val result: String = winner.map(player => s"player ${player} wins").getOrElse("its a draw")
}

object Game{

  /*
  * Note that you can have multiple definitions
  *  of the apply and unapply methods in a companion object,
  * as long as they have different signatures.

  In this code snippet, Game is a case class, so its companion object
  *  has two apply methods:
  *  one takes a String as its parameter (i.e., the one you have implemented)
  *  and the other takes two parameters
  * of type Player (i.e., the one the compiler generates at compile time).
  * */

  def apply(text: String): Game =
    text.split("-", 2) match
      case Array(playerA, playerB) =>
        apply(Player(playerA), Player(playerB))
        //or optionally
        //Game(Player(playerA), Player(playerB))
      case _ =>
        val errorMsg = s"Invalid game $text. " +
          s"Please, use the format <name>: <symbol> - <name>: <symbol>"
        throw new IllegalArgumentException(errorMsg)

}