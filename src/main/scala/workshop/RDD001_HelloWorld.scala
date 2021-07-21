package workshop

import org.apache.spark.{SparkConf, SparkContext}

// We are running spark in embedded mode
// spark shall run inside this JVM app
// local mode
// This is Spark Driver/Application
object RDD001_HelloWorld extends  App {
   val conf = new SparkConf().setAppName("HelloWorld")
     .setMaster("spark://172.20.10.3:7077") // run spark inside this program embedded

  // Spark Context, brain of spark execution
  val sc = new SparkContext(conf)

  sc.setLogLevel("WARN") // Disable INFO, DEBUG logs, show WARN, ERROR

   val data = 1 to 10
   // rarely used only in learning side/hardcoded data
   // load the data into spark memory, distribute across spark cluster
  // this is lazy method
  // until or unless if there is action used, the data never read from file, or loaded into memory
  // RDD Creation
  val rdd1 = sc.parallelize(data)

   //Transformation, Lazy, data cleaning, data transformation, unit convertion, calibration, join etc, group by
  // RDD Lineage, reuse a existing RDD and create RDD lineage
  // rdd1 is parent RDD, rdd2 is child rdd, lineage
  // filter allow only even numbers to pass through
  // if fitler return true, spark project/pick that value
  // input: 1, 2,3,4,5,6,7,8,9, 10
  //output: 2, 4, 6, 8, 10
  val rdd2 = rdd1.filter ( n => n % 2 == 0) // predicate , truncate elements

  // Input: 2, 4, 6, 8, 10
  // Output: 20, 40, 60, 80, 100
  val rdd3 = rdd2.map (n => n * 10) // transformation, no truncate

  println(rdd3.toDebugString) // print rdd lineage

  // Collect the results to the driver, this program
  // result is Scala collection, NOT RDD
  // collect is an action method
  val result = rdd3.collect()
  result.foreach(println)


}
