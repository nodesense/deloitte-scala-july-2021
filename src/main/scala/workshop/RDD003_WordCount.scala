package workshop

import org.apache.spark.{SparkConf, SparkContext}

object RDD003_WordCount extends  App {
  val conf = new SparkConf().setAppName("HelloWorld")
    .setMaster("local") // run spark inside this program embedded

  // Spark Context, brain of spark execution
  val sc = new SparkContext(conf)

  sc.setLogLevel("WARN") // Disable INFO, DEBUG logs, show WARN, ERROR


  // load the file content
  //val lines = sc.textFile("./input/notes.md")
  val lines = sc.textFile("./input/shakespeare.txt")

  // remove white space around the line
  // map
  val cleanedLines = lines.map (line => line.trim()) // remove leading and trailing white space

  // filter the empty lines
  val nonEmptyLines = cleanedLines.filter(line => !line.isEmpty) // non empty lines

  // split line into word array
  val wordsArray = nonEmptyLines.map (line => line.split(" "))

  // flatten the list
  // convert list of list of words into list of word
  // flatMap
  // flatmap shall take an array convert them into element
  val words = wordsArray.flatMap(arr => arr) // take array as input, flatten and turn them

  // we have empty space carry forwarded from space between string
  // filter the empty spaces now
  val cleanedWords = words.filter (word => !word.isEmpty)

  // now create a pair rdd, tuple (key, value), key is word, value is 1 (scala, 1), (spark, 1), (scala, 1)
  val wordPair = cleanedWords.map ( word => (word, 1)  ) // tuple

  // word count using reduceByKey
  val wordCount = wordPair.reduceByKey( (acc, value) => acc + value)


  wordCount.collect().foreach(println)


  // delete the word-count directory if already exist
  // create folder and write the output files as partitions
  // _SUCCESS : result is generated
  // part-00000 , part means partition, 00000 means partition id
  // crc - cyclic redundancy check , ensure whether file is corrupted or not
  wordCount.saveAsTextFile("./output/word-count")
  // wordsArray.collect().foreach( words => println(words.toList))
}
