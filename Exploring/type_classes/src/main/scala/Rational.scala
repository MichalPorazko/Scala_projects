import scala.math.Ordering

case class Rational(num: Int, denom: Int)

given Ordering[Rational] with
  def compare(q:Rational, x: Rational): Int = 
    q.num * r.denom - r.num * q.denom


val rationals = List(Rational(2,7), Rational(2,4), Rational(5,9))

sort(rationals)
sort(rationals)(using Ordering[Rational].reverse)
    
