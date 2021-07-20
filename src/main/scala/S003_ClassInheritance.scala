object S003_ClassInheritance extends  App {
  // inheritance driving a class from anotehr class, by reusing a class/base class

  class Order(val id: Int, val price: Double, val discount: Double = 0) {
    // a member function to calculate the total
    // child calss and this  class access getTotal
    protected def getTotal() = {
      // last expression, the value is returned, Double type
      price - (price * discount) / 100.0
    }
  }

  // inheritance, reuse base class
  class TaxableOrder(id: Int, price: Double, discount: Double, tax: Double) extends Order(id, price, discount) {
    def getGrandTotal() = {
      val total = super.getTotal() // reuse base class getTotal method in child class
      val grandTotal = total + total * tax / 100.0
      grandTotal // return statement
    }
  }

  val order1 = new TaxableOrder(1, 1000, 10, 18)
  val order2 = new TaxableOrder(3, 25000, 20, 12)

  println(order1.getGrandTotal())

  // println("Ttoal ", order1.getTotal()) // calling base class protected member outside its own class or derived class cause error
}
