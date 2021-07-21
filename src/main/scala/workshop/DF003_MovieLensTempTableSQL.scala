package workshop

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{DoubleType, IntegerType, LongType, StringType, StructField, StructType}
import  org.apache.spark.sql.functions._

object DF003_MovieLensTempTableSQL extends  App {
  val MoviesPath = "ml-latest-small/movies.csv"
  val RatingsPath = "ml-latest-small/ratings.csv"


  // spark session
  val spark = SparkSession.builder()
    //.master("local[*]")
    .master("spark://192.168.1.110:7077")
    .appName("MovieLensBasics")
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
  val movieDf = spark.read
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

  movieDf.printSchema()
  ratingDf.printSchema()

  movieDf.show(2)
  ratingDf.show(2)

  // Spark can work like database without ACID compliance
  // cannot assure consistency, integrity etc
  // Spark SQL , flavor of SQL, not compatible with ANSI SQL standard
  // three types of tables

  // 1. temp table/temp view - per session basic, not persisted. ***
  // 2. managed table - data is managed by spark [insert,update, delete stuff]
  // 3. external table - data is located in datalake/hdfc, dfs, s3


  // spark has database, the default database name is called "default"

  spark.sql("SHOW DATABASES").show()

  spark.sql("SHOW TABLES").show()

  // 1. temp table/temp view - per session basic, not persisted. ***
  // temp views can be created within spark session, can't be accessible outside session
  // create a temporary table/view from dataframe
  // doesn't it copies data to spark db sort of, not like that
  // data still in RDD
  movieDf.createOrReplaceTempView("movies")
  ratingDf.createOrReplaceTempView("ratings")

  spark.sql("SHOW TABLES").show()
  // run a sql query on movies view/temp table
  // spark.sql execute the sql and return the dataframe
  val df1 = spark.sql("SELECT * from movies")

  df1.printSchema()
  df1.show(2)

  spark.sql("SELECT upper(title) as name, lower(genres) as category from movies").show(2)


  val df2 = spark.sql("""
    SELECT movieId, avg(rating) AS avg_rating , count(rating) AS total_rating FROM ratings
    GROUP BY movieId
    HAVING total_rating >= 100 AND avg_rating >= 3
    ORDER BY avg_rating DESC
    """)

  df2.printSchema()
  df2.show(20)


  // create a temp view from SELECT state
  // CTAS , Create Temp as SELECT ****

  // how to create a temp view popular_movies from sql statement

  spark.sql("""
    CREATE TEMP VIEW popular_movies AS
    SELECT movieId, avg(rating) AS avg_rating , count(rating) AS total_rating FROM ratings
    GROUP BY movieId
    HAVING total_rating >= 100 AND avg_rating >= 3
    ORDER BY avg_rating DESC
    """)


  spark.sql("SHOW TABLES").show()

  spark.sql("SELECT * FROM popular_movies" ).show(5)

  spark.sql(
    """
       CREATE TEMP VIEW most_popular_movies AS
       SELECT pm.movieId, title, avg_rating, total_rating  from popular_movies pm
       INNER JOIN movies on movies.movieId == pm.movieId
    """)

  spark.sql("SHOW TABLES").show()

  spark.sql("SELECT * FROM most_popular_movies" ).show(5)


  // how to get dataframe from any  temp view or any managed/external spark table

  val mostPopularMoviesDf = spark.table("most_popular_movies")

  spark.sql("""
    SELECT movieId, avg(rating) AS avg_rating , count(rating) AS total_rating FROM ratings
    GROUP BY movieId
    HAVING total_rating >= 100 AND avg_rating >= 3
    """)
    .sort(desc("avg_rating"))
    .show(200)


  // on the same machine, open browser, http://localhost:4040
  println("Press enter to exit")
  scala.io.StdIn.readLine()
}
