package chap2

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * Created by kwonyoungjoo on 14. 8. 21..
 */
class example extends FlatSpec with ShouldMatchers {
  for (x <- 0.1.to(3.0, 0.1); n <- -3 to 3) {
      "" + x + "^" + n should "be " + math.pow(x, n) in {
        math.abs(pow(x, n) - math.pow(x, n)) < 0.0001 shouldEqual true
      }
    }

  def pow(x:Double, n:Int): Double={
    if(n >0 && n%2 ==0){
      val y = pow(x,n/2)
      y*y
    } else if (n >0 && n%2 ==1) {
      x*pow(x,n-1)
    } else if( n == 0){
      1
    } else{
      1/pow(x,n*(-1))
    }

  }


}

