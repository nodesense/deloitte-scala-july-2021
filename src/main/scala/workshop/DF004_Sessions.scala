package workshop


import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{DoubleType, IntegerType, LongType, StringType, StructField, StructType}
import  org.apache.spark.sql.functions._


object DF004_Sessions extends  App {
  val MoviesPath = "ml-latest-small/movies.csv"
  val RatingsPath = "ml-latest-small/ratings.csv"


  // spark session, the temp tables, udf / user defined funtions are local to session
  val spark = SparkSession.builder()
    .master("local")
    //.master("spark://192.168.1.110:7077")
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


  // create view in "spark" session
  movieDf.createOrReplaceTempView("movies")
  // works
  println("temp view")
  spark.sql("select * from movies").show(2)

  // view 1 and view 2
  // two tables orders (retail), orders (ecommerce)
  // session 1 [order, retail]
  // session 2[ order, ecommerce]

  // create new session
  // session is entry point for df, sql
  // session provide isolation of temp tables and udf\
  val spark2 = spark.newSession() // new session
  // query movies here, it will fail
  // spark2.sql("select * from movies").show(2) // EXCEPTION

  // how do i make sure that the temp view can be accessible from all sessions
  // GLOBAL TEMP VIEW
  // GLOBAL TEMP VIEW IS SHARED amoungs session

  // this will create a temp table under global_temp.ratings
  // global_temp is shared amoung all session
  ratingDf.createOrReplaceGlobalTempView("ratings")

  spark.sql("select * from global_temp.ratings").show(5)
  // spark 2 session can access global_temp.ratings
  spark2.sql("select * from global_temp.ratings").show(5)

  println("spark.sparkContext == spark2.sparkContext", spark.sparkContext == spark2.sparkContext)


  // UDF - User Defined functions
  // select upper(title) abs(rating) etc there are many build in functions exists already
  // sq of the number, custom code

 // udf is from  org.apache.spark.sql.functions.
 val sq = udf( (n: Double) => n * n )

  // udf is local to the session
  // this enable us to use square inside the sql functions
  spark.udf.register("square", sq)

  // UDF(rating) is column name
  spark.sql ("SELECT movieId, rating, square(rating) from global_temp.ratings").show(5)

  // alias name
  spark.sql ("SELECT movieId, rating, square(rating) as sq_rating from global_temp.ratings").show(5)

  // Crash due to udf is not avaialbe in another session
 // spark2.sql ("SELECT movieId, rating, square(rating) from global_temp.ratings").show(5)


  // using UDF in data frame
  ratingDf.select(col("rating"), sq(col("rating"))).show(5)


  // alias
  ratingDf.select(col("rating"),
                       sq(col("rating")).alias("sq_rating")).show(5)


  // filter and where exactly name, alias
}
