object S007_HigherOrderFunction extends  App {
  // generic for any language
  // a function that accept another function is called higher order function

  // sum of square of numbers
  // sum of cube of numbers
  // sum of numbers of identity
  // sum of anything of numbers sum of some abstraction
  // sum of 1000s of use cases
  // reusable at higher context, however there is lower level abstraction which is a function
  // this higher order doesn't know anything about lower level abstraction
  // whether it square, sqrt or cube....it doesn't know

  // let us define a higher order function
  // Int => Int is Function1[Int, Int]
  val sum = (numbers: List[Int], func: Int => Int) => {
    var s = 0
    for (n <- numbers) {
      s += func(n) // func can be anything that accept 1 int ar input, return anyting we don't know
    }

    s // return the sum value
  }

  val numbers = (1 to 10).toList
  // n => n * n  shoudl match with Int => Int means 1 input, 1 result of type int
  println ( sum (numbers,  n => n * n )) // Sum of square of numbers
  // write a cube
  println ( sum (numbers,  n => n * n * n ))

  // write a sum of abs numbers
  val numbers2 = (-10 to 0).toList

  val abs = (n: Int) => if (n < 0) -n else n // define once and reuse

  println ( sum (numbers2,  n => if (n < 0) -n else n )) // the function is inlined, no reuse
  println ( sum (numbers2,  abs)) // reusing abs function

  // write a sqrt scala.math.sqrt -- leave for later


}
