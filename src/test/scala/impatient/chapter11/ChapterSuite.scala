package impatient.chapter11

import org.scalatest.FunSuite

class ChapterSuite extends FunSuite {

  //
  // 연습문제 11-1
  //

  /*
      `+` 연산자와 `->` 연산자의 우선순위는 같고, 둘 다 왼쪽으로 결합하는(left associative) 일반적인 경우에 해당한다.

      3 + 4 -> 5 ==> (3 + 4) -> 5

          scala> 3 + 4 -> 5
          res0: (Int, Int) = (7,5)

      3 -> 4 + 5 ==> (3 -> 4) + 5

          scala> 3 -> 4 + 5
          <console>:8: error: type mismatch;
           found   : Int(5)
           required: String
                        3 -> 4 + 5
                                 ^
   */

  //
  // 연습문제 11-2
  //

  /*
      일반 수식에서는 pow에 해당하는 연산의 우선순위가 곱셈, 나눗셈보다 높아야 하는데,
      Scala의 연산자 우선순위 규칙 상 **나 ^ 연산자를 사용했을 때는 곱셈, 나눗셈과 우선순위가 같거나 더 낮아지는 문제가 있다.
   */

  //
  // 연습문제 11-3
  //


  //
  // 연습문제 11-4
  //


  //
  // 연습문제 11-5
  //


  //
  // 연습문제 11-6
  //


  //
  // 연습문제 11-7
  //


  //
  // 연습문제 11-8
  //


  //
  // 연습문제 11-9
  //


  //
  // 연습문제 11-10
  //

}
