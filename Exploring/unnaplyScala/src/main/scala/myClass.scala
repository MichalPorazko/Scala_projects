class myClass(val x : Int, val y: Int)

/*
* In this example, MyClass is a class with two parameters,
* and MyClass (the object) is its companion object.
* The apply * method in the companion object is a factory method
* to create instances of MyClass.
* */

object myClass{

  def apply(x: Int, y: Int): myClass = new myClass(x, y)
}

