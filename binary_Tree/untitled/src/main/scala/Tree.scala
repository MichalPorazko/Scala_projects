trait Tree[+T]
  case class Leaf[T](value: T)
  case class Node[T](left: Tree[T], right: Tree[T])



object Tree {

  def findValue[T](tree: Tree[T], predicate: T => Boolean): Boolean = {
    tree match {
      case Leaf(v: T) => predicate(v)
      case Node(left, right) => findValue(left, predicate) || findValue(right, predicate)
    }
  }
}



