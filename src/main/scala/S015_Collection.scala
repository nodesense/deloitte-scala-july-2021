// for mutable collection, we need to explicitly import it
import scala.collection.mutable.ArrayBuffer

// by default scala uses immutable

object S015_Collection extends  App {
  // Types of collection
  //    1. Mutable [after creation, we can add/remove/update elements/members]
  //    2. Immutable  [once created, we CANNOT update/delete/insert] used heavily


  // Mutable array buffer
  //   array - collection of elements, located by using INDEX, INDEXED collection

  // while we look for element by index, Big O(1)

  val names = ArrayBuffer[String] () // create a new instance
  names += "Scala" // index 0
  names.insert(1, "PySpark", "Spark") // 1 is position
  println(names)

  // look up by index position
  println(names(2) ) // Spark

  names.update(2, "Spark 2.4")
  println(names)

  // remove

  names -= "PySpark"
  names.remove(0)
  println(names)

}
