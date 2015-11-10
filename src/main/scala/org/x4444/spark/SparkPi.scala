package org.x4444.spark

import org.apache.spark.SparkContext

class SparkPi(sc: SparkContext, samples: Int) extends Runnable {
  override def run(): Unit = {
    SparkPi.runJob(sc, samples)
  }
}

object SparkPi {

  def runJob(sc: SparkContext, samples: Int): Any = {
    val count = sc.parallelize(1 to samples).map { i =>
      val x = Math.random()
      val y = Math.random()
      if (x * x + y * y < 1) 1 else 0
    }.reduce(_ + _)
    val pi = 4.0 * count / samples
    println("Pi is roughly " + pi)
    pi
  }

}
