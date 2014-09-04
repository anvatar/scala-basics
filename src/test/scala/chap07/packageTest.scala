package chap07

import org.scalatest.FunSuite

/**
 * Created by kwonyoungjoo on 14. 9. 3..
 */
class packageTest extends FunSuite{


  test("random create"){
    randomLinear.setSeed(10)
    for( i <- 1 to 5)
    {
      println("next Int " + randomLinear.nextInt())
      println("next Double " + randomLinear.nextDouble())
    }
  }

}
