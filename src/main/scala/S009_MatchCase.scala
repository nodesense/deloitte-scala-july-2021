object S009_MatchCase extends  App {
  // switch case in java/python, but in scala there is no switch case
  // instead we have match..case
  // match case is an expression that returns value

  val n: Int = 10 // 0, 1, 2, 10


  val result = n match {
    case 0 => "App Received" // return value
    case 1 => "App Accepted" // return
    case 2 => "App Rejected" // return
    case _ => "Unknown state" // _ represent default value  return value
  }

  println("REsult is ", result)

  // match with method/function
  def oddOrEven(n: Int) = n % 2 match {
    case 0 => "Even"
    case 1 => "Odd"
  }

  val result2 = oddOrEven(10) // 10, 11
  println("Result 2", result2)

}
