package workshop

import org.apache.spark.{SparkConf, SparkContext}
object RDD002_HelloWorldEx extends  App {
  val conf = new SparkConf().setAppName("HelloWorld")
    .setMaster("local") // run spark inside this program embedded

  // Spark Context, brain of spark execution
  val sc = new SparkContext(conf)

  sc.setLogLevel("WARN") // Disable INFO, DEBUG logs, show WARN, ERROR

  val data = 1 to 10

  val rdd1 = sc.parallelize(data)

  // input: 1, 2,3,4,5,6,7,8,9, 10
  //output: 2, 4, 6, 8, 10
  val rdd2 = rdd1.filter( n => {
    println("Filter", n, n % 2 == 0)
    n % 2 == 0 // return true/false
  })

  // Input: 2, 4, 6, 8, 10
  // Output: 20, 40, 60, 80, 100
  val rdd3 = rdd2.map (n => {
    println("Map * 10 ", n , n * 10)
    n * 10 // return value
  })

  // action 1, every action basically create a job on the given rdd lineage
  rdd3.collect().foreach(println)

  println("-------MIN---------")
  // action 2, this create another job, perform tasks from rdd 1 to rdd 3
  val min = rdd3.min() // 20
  println("Min is ", min)

  println("-------MAX---------")
  // action 3, this create another job, perform tasks from rdd 1 to rdd 3
  val max = rdd3.max() // 100
  println("max is ", max)


  println("-------SUM---------")
  // action 4, this create another job, perform tasks from rdd 1 to rdd 3
  val sum = rdd3.sum() // 20 + 40 + 60 + 80 + 100
  println("sum is ", sum)

  // on the same machine, open browser, http://localhost:4040
  println("Press enter to exit")
  scala.io.StdIn.readLine()


}
