package org.x4444.spark

import java.util.concurrent.{Executors, TimeUnit}

import org.apache.spark.{SparkConf, SparkContext}

object Main {

  def main(args: Array[String]) {

    val master = "spark://10.0.28.54:7077"
    val conf = new SparkConf().setAppName("MultiJobApp").setMaster(master)

    val sc = new SparkContext(conf)
    try {
      val pool = Executors.newFixedThreadPool(12)
      try {
        for (i <- 1 to 2) {
          pool.submit(new SparkPi(sc, 1000000))
        }
      } finally {
        pool.shutdown()
        pool.awaitTermination(100, TimeUnit.SECONDS)
      }
    } finally {
      sc.stop()
    }
  }
}
