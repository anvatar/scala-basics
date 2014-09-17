package chap07

/**
 * Created by kwonyoungjoo on 14. 9. 1..
 * example 7-3
 */


  /*
    연습문제 7-2
   */


   package  object randomLinear {
     private var seed: Int = 0
     private val a: Int = 1664525
     private val b: Int = 1013904223
     private val n: Int = 32

     def next(): Double = ( seed * a + b )  % math.pow(2, n)

     def nextInt(): Int = next().toInt

     def nextDouble(): Double = next()

     def setSeed(seed: Int){
       this.seed = seed
     }
   }


