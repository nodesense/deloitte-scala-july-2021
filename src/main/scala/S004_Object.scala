object S004_Object extends  App {

  // What is object
  // Object is an instance
  // Object represent singleton object, only one object instance
  // We cannot create instance of object
  // in Java static members can be brought into Object in Scala

  object Logger {
    // object body constructor
    // shall be invoked when the object is used first time
    println("Logger initializer")

    var logLevel = "info"
    def info(msg: String) = println("INFO", msg)
    def error(msg: String) = println("ERROR", msg)
  }

  // Use Logger
  Logger // this will call Logger constructor first time

  Logger // this is second time we use logger, the constructor is not called

  println(Logger.logLevel)
  Logger.info("starting application")
  Logger.error("DB connection error")

}
