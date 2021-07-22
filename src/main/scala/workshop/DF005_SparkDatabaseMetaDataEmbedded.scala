package workshop
import org.apache.spark.sql.SparkSession

// local development
object DF005_SparkDatabaseMetaDataEmbedded extends  App {
  // persisted table
  //   managed table
  //   external table

  // meta server and data from local machine
  val spark: SparkSession  = SparkSession
    .builder()
    .master("local") // spark run inside hello world app
    //.config("spark.sql.warehouse.dir", "/home/krish/warehouse" )
    .appName("SparkDB")
    // right now, it will maintain meta data in the local machine , for this project
    .enableHiveSupport() // enable hive meta data in embedded mode derby?
    .getOrCreate()

  spark.sparkContext.setLogLevel("WARN")

  spark.sql("show databases").show()

  spark.sql("CREATE DATABASE IF NOT EXISTS test").show()

  spark.sql("show databases").show()

  spark.sql("CREATE TABLE IF NOT EXISTS test.src (key INT, value STRING)")
  spark.sql("INSERT INTO TABLE test.src values (1, 'ONE')")
  spark.sql("SELECT * FROM test.src").show()

  spark.sql("DROP TABLE  IF EXISTS test.src")

}
