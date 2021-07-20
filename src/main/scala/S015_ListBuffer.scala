import scala.collection.mutable.ListBuffer

object S015_ListBuffer extends  App {
 // implementaion of mutable collection
  // based on linked list
  // Index look up possible, expensive O(n)
  val languages = ListBuffer[String] ("Scala", "Java", "PySpark")

  languages += "R" //3rd index
  languages.insert(4, "SQL")

  println(languages)

  // remove
  languages -= "PySpark"
  languages.remove(2) // "R"
  println(languages)

  // remove all
  languages.clear()
}
