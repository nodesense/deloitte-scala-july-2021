object S010_CaseClass extends  App {
  // case class is more of record, with fields, values etc
  // case class is a syntatic sugar, scala will generate code automatically

  // for case class, scala compilter generate a class code, and also generate a companion object
  // with apply function

  // ****** id, name are members, of value type, Immutable *****
  case class Product(id: Int, name: String) {
    def getName() = name
  }

  // new keyword is discouraged, becase case class has companion object, we can create object
  // without new keyword

  val p1 = new Product(1, "apple") // new works
  val p2 = Product(2, "Samsung")  // recommended
  val p3 = Product(3, "Realme")

  println(p1)

  println(p2)

  println(p3)

  println(p3.getName())

}
