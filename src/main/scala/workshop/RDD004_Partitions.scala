package workshop
import org.apache.spark.{SparkConf, SparkContext}

object RDD004_Partitions extends  App {
  // local - default parallization will be 1
  // local[2] - default parallization will be 2
  // driver is local to this jvm
  val conf = new SparkConf()
    .setAppName("HelloWorld")
    .set("spark.executor.memory", "4g")
    .set("spark.driver.memory", "4g")
    .set("spark.cores.max", "4")
    .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    .set("spark.jars", """C:\Users\Gopalakrishnan\deloitte-scala-july-2021\target\scala-2.11\deloitte-spark-workshop_2.11-0.1.jar""")
    .setMaster("spark://192.168.1.110:7077") // now the executors shall be run inside worker
    //.setMaster("local") //  local - default parallization will be 1, driver, executors run inside this jvm process
    //.setMaster("local[2]") // local[2] - default parallization will be 2
   // .setMaster("local[*]") // local[*] - based on number of core

  // Spark Context, brain of spark execution
  val sc = new SparkContext(conf)

  sc.setLogLevel("WARN") // Disable INFO, DEBUG logs, show WARN, ERROR

  val data = 1 to 100

  val rdd1 = sc.parallelize(data, 1) // takes default paralleism from local[??]
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
//  rdd1.glom().collect().foreach(arr => println(arr.toList))

  // collect data from partitions as they stored
 // val partitionData = rdd3.glom() // return an RDD with partition data
//  val partData = partitionData.collect() // Array[Array[Int]]
  println("rdd 3 result partition data")
//  partData.foreach(arr => println(arr.toList))

  // action
  println("-------MIN---------")
  // action 2, this create another job, perform tasks from rdd 1 to rdd 3
  val min = rdd3.min() // 20
  println("Min is ", min)

  // on the same machine, open browser, http://localhost:4040
  println("Press enter to exit")
  scala.io.StdIn.readLine()

}
