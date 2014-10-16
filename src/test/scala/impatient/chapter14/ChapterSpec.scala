package impatient.chapter14

import org.scalatest._
import scala.collection._

class ChapterSpec extends FunSuite {
    //
    // 연습문제 1
    //

    //
    // 연습문제 2
    //

    //
    // 연습문제 3
    //

    //
    // 연습문제 4
    //


    //
    // 연습문제 5
    //
    //val inputList = List((3,8), 2, 5)
    def leafSum(lst:List[Any]):Int = lst match {
      case ::(h:Int, Nil) => h
      case ::(h:Int, t:List[Any]) => h + leafSum(t)
      case ::(h:List[Any], t:List[Any]) => leafSum(h) + leafSum(t)
      case _ => 0
    }

  test("compute list element sum") {
      val inputList = List(List(3,8), 2, 5)
      assert(leafSum(inputList) == 18)
    }


  //
    // 연습문제 6
    //

    //
    // 연습문제 7
    //

    //
    // 연습문제 8
    //

    //
    // 연습문제 9
    //

    //
    // 연습문제 10
    //
}
