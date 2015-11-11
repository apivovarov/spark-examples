${SPARK_HOME?"Need to set SPARK_HOME"}
${MASTER?"Need to set MASTER"}

$SPARK_HOME/bin/spark-submit \
--class org.x4444.spark.MultiJobPi \
--master $MASTER \
target/spark-examples-1.0.0-SNAPSHOT.jar $@
