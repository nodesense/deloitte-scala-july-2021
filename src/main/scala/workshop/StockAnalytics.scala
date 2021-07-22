package workshop

import org.apache.spark.sql.SparkSession

import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

object StockAnalytics extends  App {

  val spark: SparkSession  = SparkSession
    .builder()
    .master("local") // spark run inside hello world app
    .appName("testapp")
    .config("hive.metastore.uris", "thrift://bigdata.training.sh:9083" ) // consist of meta data server
    .config("hive.metastore.warehouse.dir", "hdfs://bigdata.training.sh:8020/user/hive/warehouse" ) // for the hive data location

    .getOrCreate()

  val sc = spark.sparkContext
  sc.setLogLevel("WARN")

  import spark.implicits._

  val DATA_PATH = "hdfs://bigdata.training.sh:8020/user/stock/daily"


  def gainers() =  {
    val dailyDF = DailyEoD.getDailyEoD (spark, DATA_PATH )

    dailyDF.printSchema ()

    dailyDF.show (5)

    println (dailyDF.count () )

    val gainers = dailyDF.withColumn ("Gain", $"CLOSE_PRICE" - $"PREV_CL_PR")
      .withColumn ("GainP", ($"CLOSE_PRICE" - $"PREV_CL_PR") / $"PREV_CL_PR" * 100.0)
      .filter ($"Gain" > 0)
      .sort (desc ("GainP") )
      .select ("SECURITY", "Gain", "GainP")


    gainers
  }

  def losers() =  {
    val dailyDF = DailyEoD.getDailyEoD (spark, DATA_PATH )

    dailyDF.printSchema ()

    dailyDF.show (5)

    println (dailyDF.count () )

    val losers = dailyDF.withColumn ("Loss",  $"CLOSE_PRICE" - $"PREV_CL_PR" )
      .withColumn ("LossP", abs(col("Loss")) / $"PREV_CL_PR" * 100.0)
      .filter ($"Loss" < 0)
      .sort ( "LossP" )
      .select ("SECURITY", "Loss", "LossP")

    losers
  }



  val gainersDf = gainers()
  gainersDf.show(5)


  val losersDf = losers()
  losersDf.show(5)


  def advanceDecline() =  {
    val dailyDF = DailyEoD.getDailyEoD (spark, DATA_PATH )



    val advanceDecline = dailyDF.withColumn ("State",   when($"CLOSE_PRICE" - $"PREV_CL_PR" > 0,"A") // Advance
      .when($"CLOSE_PRICE" - $"PREV_CL_PR" < 0,"D") // Decline
      .otherwise("N")) // neutral )
      .groupBy("State")
      .agg(count("State").alias("Count"))

    advanceDecline
  }


  def advanceDeclinePivot() =  {
    val dailyDF = DailyEoD.getDailyEoD (spark, DATA_PATH )

    val countries = List("A", "D", "N")


    val advanceDecline = dailyDF.withColumn ("State",   when($"CLOSE_PRICE" - $"PREV_CL_PR" > 0,"A") // Advance
      .when($"CLOSE_PRICE" - $"PREV_CL_PR" < 0,"D") // Decline
      .otherwise("N")) // neutral )
      .groupBy("State")
      .pivot("State")
      .agg(count("State").alias("Count"))
      .na
      .fill(0)

    advanceDecline
  }


  advanceDecline().show()

  // Pivot the dataframe
  advanceDeclinePivot().show()

}