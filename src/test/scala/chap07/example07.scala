package chap07

/**
 * Created by kwonyoungjoo on 14. 9. 1..
 * example 7-3
 */


  /*
    연습문제 7-2
   */



   package  object randomLinear {
     var seed: Int = 0
     val a: Int = 1664525
     val b: Int = 1013904223
     val n: Int = 32

     def next(): Double = {
       seed * a + (b % 2)
     }

     def nextInt(): Int = {
       next().toInt
     }

     def nextDouble(): Double = {
       next()
     }

     def setSeed(seed: Int) {
       this.seed = seed
     }
   }

  class randomTest {
    randomLinear.setSeed(10)
    println(randomLinear.nextDouble())
  }


