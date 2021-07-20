object S012_Exception extends  App {

  // exception are runtime error, which will blow up and halt the program execution
  // try, catch, finally - try..catch is an expression, that returns value

  try {
    val result = 42 / 0 // if any error, throw exception at this point
    // no error, it will reach here
    println("Result ", result)
  }
  catch {
    // will be called only if there is exception
    // we handle the exception
    case t: Throwable => println(t)
  }
  finally  {
    // called whether exception or no exception
    // useful to close the open file, open socket/db connection
    // no error, it will reach here
    println("At finally block")
  }

  println("Gracefully working, exception handled")
}
