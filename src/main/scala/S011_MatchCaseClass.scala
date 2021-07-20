object S011_MatchCaseClass extends  App {
  // for every class, case class , Object is a super class / Java

  // pattern matching with case classes
  case class Product(id: Int, price: Double);
  case class Book(id: Int, price: Double);
  case class Mobile(id: Int, price: Double);

  def calculateGrandTotal(order: Object): Double = {
    order match {
      // variable pattern, assign a variabel in case statement
      case p: Product => p.price + p.price * .18
      // extractor pattern
      // we don't take reference to book object, instead took only price
      // _ here means, the id, but ignore
      case Book (_, price) => (price - price * .10) * 1.05 // apply 10% discount, 5% gst

      // case class with if guard
      case m: Mobile if m.price > 10000 =>   (m.price - m.price * .20) * 1.18

        // matched only m.price <= 10000
      case m: Mobile =>    (m.price - m.price * .10) * 1.18

    }
  }

  println(calculateGrandTotal (Product(1, 100))  )
  println(calculateGrandTotal (Book(2, 1000))  )
  println(calculateGrandTotal (Mobile(4, 8000))  )
  println(calculateGrandTotal (Mobile(5, 20000))  )

}
