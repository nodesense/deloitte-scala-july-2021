// mutable map
import scala.collection.mutable.Map

object S017_Map extends  App {
  // Key / Value pair like Hashtable
  // -> means tuple
  val countryCodes: Map[String, String] = Map ("IN" -> "India", "CA" -> "Canada")

  // add new element
  countryCodes += ("UK" -> "United Kingdom")

  println(countryCodes)

  // loook up by member key
  println(countryCodes("CA")) // Canada

  // remove UK
  countryCodes -= "UK"

  println(countryCodes)
}
