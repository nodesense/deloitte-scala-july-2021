// immutable list
// we cannot add/remove/delete members after creation
// default in scala
//import scala.collection.immutable.List

object S016_List extends  App {
  // immutable list
  val languages: List[String] = List("PySpark", "Scala", "Spark")

  // remove, update, insert won't work

  println(languages (1)) // Scala

  // empty collection
  val villages = Nil // Empty collection

  //form new list using constant
  // :: means concat
  // right associativity
  val cities = "BLR" :: "Pune" :: "Chennai" :: Nil
  println(cities)

  println("Tail ", languages.tail) // Scala, Spark
  println("Head", languages.head) // PySpark

  // concat two list List.contact(list1, list2)
  // ::: concat
  val collection = languages ::: cities

  println(collection)

 }
