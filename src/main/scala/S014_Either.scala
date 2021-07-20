object S014_Either extends  App {
  // Either
  //   api call GET /product/100
  //    product with id may exist in db or may not exist
  //   if product found, then return 200 OK with product data [Positive]
  //   if product not found, return 404 Not found [Negative]
  // if db error, return 500 Server Error [Nagative]

  // Either has two options, Left, Right
  // conventions
  // Right means positive result, success
  // Left means negative result, failure
  // Either[String, Int] , String is left, int is right
  def parseInt(input: String): Either[String, Int] = {
    try {
      Right(input.toInt)
    }catch {
      case t: Throwable => Left("Error while processing " + t.getMessage)
    }
  }

  val x: Either[String, Int] = parseInt("100")
  if (x.isRight) {
    val result = x.right.get // 100
    println("result ", 100)
  }

  val y : Either[String, Int] = parseInt("--")

  if (y.isLeft) {
    val errorMessage = y.left.get // Error while processing Div by zero
    println("Error is ", errorMessage)
  }

  // for comprehention with Either
  // right is known as good value
  // left is known for wrong /negative result

  // FIXME: using either with scala 2.11
}
