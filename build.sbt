name := "deloitte-spark-workshop"

version := "0.1"

scalaVersion := "2.11.12"
val sparkVersion = "2.4.7"

// space core engine , RDD, Driver, scheduling etc
libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion
// SQL, Dataframe
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion
// Hive Meta Data, Warehouse
libraryDependencies += "org.apache.spark" %% "spark-hive" % sparkVersion