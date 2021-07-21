package workshop
import org.apache.spark.{SparkConf, SparkContext}

object RDD004_Partitions extends  App {
  // local - default parallization will be 1
  // local[2] - default parallization will be 2
  val conf = new SparkConf().setAppName("HelloWorld")
    .setMaster("local") //  local - default parallization will be 1
    //.setMaster("local[2]") // local[2] - default parallization will be 2
   // .setMaster("local[*]") // local[*] - based on number of core

  // Spark Context, brain of spark execution
  val sc = new SparkContext(conf)

  sc.setLogLevel("WARN") // Disable INFO, DEBUG logs, show WARN, ERROR

  val data = 1 to 100000000

  val rdd1 = sc.parallelize(data) // takes default paralleism from local[??]
  //val rdd1 = sc.parallelize(data, 100) // explicit partitions

  println("RDD1 partitions", rdd1.getNumPartitions)

  // input: 1, 2,3,4,5,6,7,8,9, 10
  //output: 2, 4, 6, 8, 10
  val rdd2 = rdd1.filter( n => {
   // println("Filter", n, n % 2 == 0)
    n % 2 == 0 // return true/false
  })

  // Input: 2, 4, 6, 8, 10
  // Output: 20, 40, 60, 80, 100
  val rdd3 = rdd2.map (n => {
  //  println("Map * 10 ", n , n * 10)
    n * 10 // return value
  })

  println("RDD3 partititions", rdd3.getNumPartitions)

  println("Rdd1 partition data")
  rdd1.glom().collect().foreach(arr => println(arr.toList))

  // collect data from partitions as they stored
  val partitionData = rdd3.glom() // return an RDD with partition data
  val partData = partitionData.collect() // Array[Array[Int]]
  println("rdd 3 result partition data")
  partData.foreach(arr => println(arr.toList))

  // action
  println("-------MIN---------")
  // action 2, this create another job, perform tasks from rdd 1 to rdd 3
  val min = rdd3.min() // 20
  println("Min is ", min)

  // on the same machine, open browser, http://localhost:4040
  println("Press enter to exit")
  scala.io.StdIn.readLine()

}
