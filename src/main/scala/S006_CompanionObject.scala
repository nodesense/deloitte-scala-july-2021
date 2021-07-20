object S006_CompanionObject extends  App {
  // The class name and object name are same means, they are known as companion object
  // rule is, both should be in same file
  // use case?
  // we use new keyword to create object
  // new keyword is not functional, by using companion object, we can eliminate use of new keyword

  // PRoduct class and Product object are same name
  // present in same file - companion object
  class Product(val id: Int, val name: String, val price: Double) {

  }

  object Product {
    // implement apply function in object
    def apply(id: Int, name: String, price: Double) = new Product(id, name, price)
  }

  // note, no new keyword to create object
  // now it become functional, we can create object by calling function
  val p1 = Product(1, "iphone", 50000) // called syntatic sugar, scala calls Product.apply (...)
  val p2 = Product(2, "RealMe", 12000)

  println("p1 price", p1.price)
  println("p2 price", p2.price)


}
