package workshop

import org.apache.spark.sql.SparkSession

// local development
object DF006_SparkDatabaseHiveMetaDataServer extends  App {
  // persisted table
  //   managed table
  //   external table

  // 65.21.50.85 -- hive meta server running

  val spark: SparkSession  = SparkSession
    .builder()
    .master("local") // spark run inside hello world app
    // meta data from hive meta server
    .config("hive.metastore.uris", "thrift://65.21.50.85:9083" ) // hive meta data server centralized
    .config("spark.sql.warehouse.dir", "hdfs://65.21.50.85:8020/user/hive/warehouse" )
    // data is from hadoop
    .config("hive.metastore.warehouse.dir", "hdfs://65.21.50.85:8020/user/hive/warehouse" ) // hadoop name node
    .appName("SparkDB")
    // right now, it will maintain meta data in the local machine , for this project
    .enableHiveSupport() // enable hive meta data in embedded mode derby?
    .getOrCreate()

  spark.sparkContext.setLogLevel("WARN")

  // FIXME: Cluster config data location
  spark.sql("show databases").show()

  spark.sql("DROP DATABASE IF EXISTS gk cascade").show()

  spark.sql("CREATE DATABASE IF NOT EXISTS gk").show()

  spark.sql("show databases").show()
  // managed table
  spark.sql("CREATE TABLE IF NOT EXISTS gk.src (key INT, value STRING)")
  // insert/update/delete to be done by spark
  spark.sql("INSERT INTO TABLE gk.src values (1, 'ONE')")
  spark.sql("SELECT * FROM gk.src").show()

  //spark.sql("DROP TABLE  IF EXISTS gk.src")

}
