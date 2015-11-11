package org.x4444.spark

import java.util.concurrent.{Executors, TimeUnit}

import org.apache.spark.{SparkConf, SparkContext}

object MultiJobPi {

  def main(args: Array[String]) {
    if (args.length != 2) {
      println("Usage: MultiJobPi <jobs_total> <jobs_per_context>")
      System.exit(1)
    }

    val conf = new SparkConf().setAppName("MultiJobPi")
    val sc = new SparkContext(conf)

    val jobsTotal = args(0).toInt
    var jobsPerContext = args(1).toInt
    val piIterations = 1000000
    val timeoutMin = 1000

    try {
      // warm up
      SparkPi.runJob(sc, piIterations)
      val pool = Executors.newFixedThreadPool(jobsPerContext)
      val time1 = System.currentTimeMillis()
      try {
        (1 to jobsTotal).foreach(x => pool.submit(new SparkPi(sc, piIterations)))
      } finally {
        pool.shutdown()
        pool.awaitTermination(timeoutMin, TimeUnit.SECONDS)
      }
      val time2 = System.currentTimeMillis()
      println("jobs total: " + jobsTotal)
      println("jobs per context: " + jobsPerContext)
      println("time: " + (time2 - time1) / 1000.0)
    } finally {
      sc.stop()
    }
  }
}
