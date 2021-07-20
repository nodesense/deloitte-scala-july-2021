object S002_Main {
  // writing java style main function
  // and command line arguments

  def main(args: Array[String]): Unit = {
    // command line arguments can be passed from Intelli configuration
      println("Hello World")
      println("Args length ", args.length)
      args.toList.foreach(println)
  }

}
