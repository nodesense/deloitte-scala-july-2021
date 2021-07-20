object S013_Options extends  App {
  // Buy Ticket ->  get some ticket or None
  // Options either represent Some value or None
  // used to handle the errors/exceptions smoothly instead of blow up exception

  // convert a string into integer
  def toInt(input: String): Option[Int] = {
     try {
       Some(input.toInt)
     } catch {
       case t:Throwable => None
     }
  }

  val result:Option[Int] = toInt("100") // 100
  val result2:Option[Int]  = toInt("NaN") // throw exception

  println("Result", result)
  println("result2", result2)

  if (result.isDefined) { // value present
    // result.get will get actual result ie Int 100
    println("Result is ", result.get)
  }

  if (result.isEmpty){
    println("No Result")
  }

  // for comprehension
  // sugar part to ensure the developers can right clean code
  // alternative to use isDefined check and then get the value

  // result of for comprehention with option is always Option type
  val result4: Option[Int] = for {
    a <- toInt("100") // this automatically check isDefined, only if defined, then takes the values
    b <- toInt("200") // this automatically check isDefined, only if defined, then takes the values
  } yield  a + b

  println("Result4 is ", result4)

  val result5: Option[Int] = for {
    a <- toInt("100") // this automatically check isDefined, only if defined, then takes the values
    b <- toInt("NaN") // this automatically check isDefined, which is false, then it returns None
  } yield  a + b

  println("Result5 is ", result5) // None

}
