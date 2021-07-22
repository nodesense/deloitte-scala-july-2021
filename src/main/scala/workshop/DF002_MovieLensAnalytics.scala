package workshop



import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{DoubleType, IntegerType, LongType, StringType, StructField, StructType}
// import all from a package, functions like sum, agg, desc, asc, etc...
import  org.apache.spark.sql.functions._


object DF002_MovieLensAnalytics extends  App {
  val MoviesPath = "ml-latest-small/movies.csv"
  val RatingsPath = "ml-latest-small/ratings.csv"


  // spark session
  val spark = SparkSession.builder().master("local[*]")
    .appName("MovieLensBasics")
    .config("hive.metastore.uris", "thrift://65.21.50.85:9083" ) // hive meta data server centralized
    .config("spark.sql.warehouse.dir", "hdfs://65.21.50.85:8020/user/hive/warehouse" )
    // data is from hadoop
    .config("hive.metastore.warehouse.dir", "hdfs://65.21.50.85:8020/user/hive/warehouse" )
    .getOrCreate()

  // if we need spark context
  val sc = spark.sparkContext
  sc.setLogLevel("WARN")

  // let us define schema
  // avoid using inferschema
  val MovieSchema = StructType (
      List ( // List of columns
        StructField("movieId", IntegerType, true), // true means nullable
        StructField("title", StringType, true),
        StructField("genres", StringType, true)
      )
  )


  val RatingSchema = StructType(
    List(
      StructField("userId", IntegerType, true),
      StructField("movieId", IntegerType, true),
      StructField("rating", DoubleType, true),
      StructField("timestamp", LongType, true)
    )
  )


  // we no need to use inferSchema
  val movieDf2 = spark.read
    .format("csv")
    .option("header",  true)
    .option("delimitter", ",")
    .schema(MovieSchema) // use the Schema
    .load(MoviesPath)


  // we no need to use inferSchema
  val ratingDf = spark.read
    .format("csv")
    .option("header",  true)
    .option("delimitter", ",")
    .schema(RatingSchema) // use the Schema
    .load(RatingsPath)

  // movieDf2 is cached across all the executors
  // avoid shuffle
  // data will be in local memory for joins
  // don't use this for fact table
  // use for dimension/master tables
  val movieDf = broadcast(movieDf2)

  movieDf.printSchema()
  ratingDf.printSchema()

  ratingDf.cache() // calls rdd.cache, memory and disk

  movieDf.show(2)
  ratingDf.show(2)


  // various capablities of dataframe examples
  // dataframe/rdd are immutable
  // whenever new operations applied, it returns new data frame
  // dataframe/rdd transformation are lazy
  // the job is not created until there is an action
  // filter, sort, groupBy, agg, distinct, select drop ....... are basically transform function, mean lazy ones

  // actions, write, saveAsTable, forEach, take, collect forEachPartition, etc actions, which will be executed as job
  // col is from org.apache.spark.sql.functions._
  // this creates a new DF, new RDD , new partitions
  // df1 shall have filtered output
  val df1 = ratingDf.filter(col("rating") >= 4)
  // df1.printSchema()
  println("ratings >= 4")
  df1.show(5)
  import spark.implicits._
  // $ is a function,  alias for col, available as implicit spark.implicit
  ratingDf.filter($"rating" >= 4).show(2)

  println($"rating" == col("rating")) // true


  // use select to project 1 or few columns instead of all the columns
  // it will create new df with only mentioned columns
  val df2 = ratingDf.select("movieId", "rating")
  df2.printSchema()
  df2.show(2)

  // distinct rating value
  ratingDf.select("rating").distinct().show()

  // distinct rating value in ascending order
  ratingDf.select("rating").distinct().sort("rating").show()

  // distinct rating value in decending order
  ratingDf.select("rating").distinct().sort(desc("rating")).show()

  // drop columns, pick everthing except the dropped once
  ratingDf.drop("userId", "timestamp").show(5)

  // withColumn, create a new column deriving from existing one
  movieDf.withColumn("NameUpper", upper($"title")).show(5)

  // add a constant as placeholder
    // literal / constant
  movieDf.withColumn("tickets", lit(0)).show(5)

  // creating new column but concating two columns with constant is
 // movieDf.withColumn("description", concat(col("title"),lit(" is ") , col("genres")))
 //   .show(5)

  // concat to concat columns

  movieDf.withColumn("description",
    concat(
      col("title"),
      lit (" is categorized as "),
      col("genres") )
  )
    .show(10, false)  // show function not showing completing content, false ensure no truncate


  // rename title to name column
  movieDf.withColumnRenamed("title", "name").show(5)

  // aggregation, group by, sorting etc
  // get most popular movies
  // 1. avg rating per movie id,
  // 2. count number of users voted for that movie
  // and filter total ratings >= 100 and avg_rating > 3
  // avg("rating") creates a column named  avg(rating)
  // count("userId") creates a column named  count(userId)

  val popularMovies = ratingDf
                      .groupBy("movieId")
    .agg(avg("rating").alias("avg_rating"), count("userId"))
    .withColumnRenamed("count(userId)", "total_rating")
    .filter( ($"total_rating" >= 100) && ($"avg_rating" >= 3))
    .sort(desc("avg_rating"))


  popularMovies.show(200)

  // movieId, total_rating, avg_rating
  // we don't know the movie title yet

  // inner join, condition has to be met
  val mostPopularMovies = popularMovies.join(movieDf, popularMovies("movieId") === movieDf("movieId") )
                                        .select(popularMovies("movieId"), $"title", $"avg_rating", $"total_rating")

  mostPopularMovies.show(200)


  // hive meta store
  spark.sql("CREATE DATABASE IF NOT EXISTS gk_moviedb").show()

  // create a table if not exist in hive meta store
  // write the content to hdfs??
//  mostPopularMovies.write
//                   .mode("overwrite")
//    .saveAsTable("gk_moviedb.most_popular_movies")


  // don't use explain in production deployment
  // use it during development, understand the spark execution
  // explain also uses action

  // What is plan? only for DF, SQL, NOT FOR RDD
  // Spark shall optimize the queires, executions to get best performance
  // == Parsed Logical Plan ==
  //   your code as is written either in sql, df way
  //   it won't resolve if any column exists or not, column data type

  // == Analyzed Logical Plan ==
    // spark will check if the table exist, column exist, columns are good to go
  // code as is, no optimization

  // == Optimized Logical Plan ==
    // cost and optimization performed,
    // filter and then sort

  // == Physical Plan ==
  println("EXPLAIN ")
  popularMovies.explain() // shall give the plan of execution

  println("EXPLAIN EXTENDED")
  popularMovies.explain(true) // shall give the plan of execution


  println("EXPLAIN EXTENDED JOIN.")
  mostPopularMovies.explain(true)



  // Write the data frame as parquet format,  90% less size

  movieDf.write.mode("overwrite").parquet("hdfs://bigdata.training.sh:8020/user/krish/moviedb/movies-parquet")
  ratingDf.coalesce(1).write.mode("overwrite").parquet("hdfs://bigdata.training.sh:8020/user/krish/moviedb/ratings-parquet")
  // by default, groupBy, join create 200 partititions, all partitions data shall be written to hdfs as smaller files
  // reduce the number of files , reduce number of partitions
  // NEVER USE IT for INCREMENTING PARTITIONS
  mostPopularMovies.coalesce(1).write.mode("overwrite").parquet("hdfs://bigdata.training.sh:8020/user/krish/moviedb/most-popular-movies-parquet")


  // on the same machine, open browser, http://localhost:4040
  println("Press enter to exit")
  scala.io.StdIn.readLine()
}
