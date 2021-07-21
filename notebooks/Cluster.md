
start 1 Spark Master first
 then spark master shall bind to a port 7077, where workers should connect too.


open a command prompt

%SPARK_HOME%/bin/spark-class org.apache.spark.deploy.master.Master

Now check, http://localhost:8080/

copy the spark master url for below reference


2 Spark Workers, we should input the spark master port and ip address spark://ip-addr:7077

open second command prompt, change the ip as per your master [worker 1]


%SPARK_HOME%/bin/spark-class org.apache.spark.deploy.worker.Worker  spark://192.168.1.110:7077


%SPARK_HOME%/bin/spark-class org.apache.spark.deploy.worker.Worker  spark://192.168.1.110:7077


open third command prompt [worker 2]


%SPARK_HOME%/bin/spark-class org.apache.spark.deploy.worker.Worker  spark://172.20.10.3:7077



spark-submit --master spark://172.20.10.3:7077 --class workshop.RDD004_Partitions C:\Users\Gopalakrishnan\deloitte-scala-july-2021\target\scala-2.11\deloitte-spark-workshop_2.11-0.1.jar 