
// App is trait , equalant of Java Interface
// App trait already implementing main function as default
// if we extend from App, we don't write Main function
object S001_HelloWorld extends App {
  println("Hello world")

  println("Args length ", args.length)
  args.toList.foreach(println)
}
