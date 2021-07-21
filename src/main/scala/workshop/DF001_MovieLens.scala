package workshop

import org.apache.spark.sql.SparkSession

// Data Frames, SQL are APIS, need SparkSession which is entry point to DF, SQL
// SprakSession has spark context
// DataSet
// RDD is core, unstructured data, custom data handling, no standard

// DataFrames is structured data, meta concepts called schema, schema consists
// columns, column name, data types
// Data rows, data itself
object DF001_MovieLens extends  App {

  // spark session
  val spark = SparkSession.builder().master("local[*]")
    .appName("MovieLensBasics")
    .getOrCreate()

  // if we need spark context
  val sc = spark.sparkContext
  sc.setLogLevel("WARN")

  // avoid using inferSchema for large files

  val movieDf = spark.read.format("csv")
    .option("header", true)
    .option("delimitter", ",")
    .option("inferSchema", true) // will scan the csv to identify the right data type ie movieId will be classified as Int
    .load("ml-latest-small/movies.csv")

  // print the schema of the dataframe
  movieDf.printSchema()
  movieDf.show(2) // 20 records shall be shown


  // RDD
  // every DF, SQL operate of top of RDD, RDD parition, RDD cache, RDD broadcast, RDD Task, Stage....
  println("RDD partitions", movieDf.rdd.getNumPartitions)
}
