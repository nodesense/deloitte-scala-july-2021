object S018_Tuple extends  App {
  // List, Array etc they are collection of elements of same type
  // Lsit/Array can grow/srink by size

  // Tuple also known as pair of elements of different types
  // tuple used  to represent fact/data records in absense of case classes
  // tuple is compile time based, static typing

  // notation used is paranthesis () for tuple
  // Unit means empty tuple ()

  // tuple of two elements
  val t2_ex1 = ("John", 34) // tuple of 2 elements
  println(t2_ex1._1 )
  // compile time, only _1, _2 avaiable, accessing _3 cause compile time error
  // t2_ex1._3  // error

  val t2_ex2 = ("Mary" -> 28) // tuple of 2 elements
  // tuple of 3 elements
  val t3_ex1 = ("Jone", 34, 7500.45) // _1, _2, _3

  //empty tuple, Unit
  val t0_ex1 = () // empty tuple

  // tuple 1
  //val t1_ex1 = (10,) // adding up , at end of tuple 1 make compiler to realize that you want tuple
  //t1_ex1._1 // 10
}
