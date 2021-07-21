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

  //TODO: SQL + DF Sorting
}
