object S008_CurryClosureFunction extends  App {
  // a function that returns another function is called curry function

  // closure, a functional state, the value available within function even after function exit

  //seq generator, start from 0, 1, 2,3,4,..
  // seq 100, 110,  120, 130, ....
  // this method shall return a function
  def seqGenerator(start: Int = 0, step: Int = 1) = {
    // this function is newly created whenever seqGenerator called

    // Closure: a functional state, the value available within function even after function exit
    // Encapsulation: The value of current cannot be changed from outside
    var current = start // to begin with
    // the current value is reference inside seq function , visibility
    // the reference of seq is given to caller seq0By1, seq100By10
    // until seq0By1, seq100By10 are cleared from memory, the value of current will be there in memory
    val seq = () => {
      println("seq called")
      val temp = current
      current += step
      temp // return value 0, 1, 2, 3, 4, 5,
    }

    seq // returning function as result
  }

  // get a seq generator that increment values by 1 startign with 0
  // seq0By1 the function, returned from seqGenerator
  val seq0By1 = seqGenerator(0, 1)
  val seq100By10 = seqGenerator(100, 10)

  // call the seq generator
  println("seq0By1", seq0By1() ) // 0
  println("seq100By10", seq100By10() ) // 100
  println("seq0By1", seq0By1() ) // 1
  println("seq0By1", seq0By1() ) // 2

  println("seq100By10", seq100By10() ) // 110
}
