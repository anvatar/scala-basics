package impatient.chapter07

import org.scalatest._

class ChapterSpec extends FlatSpec with Matchers {
  //
  // 연습문제 1
  //

  //
  // 연습문제 2
  //

  //
  // 연습문제 3
  //
  "RandomPackageObjectTest" should "be" in {
    random.setSeed(1)
    1015568748 shouldEqual random.nextInt()
    1586005467 shouldEqual random.nextInt()
    1015568748.0 shouldEqual random.nextDouble()
    1586005467.0 shouldEqual random.nextDouble()
  }

  //
  // 연습문제 4
  //


  //
  // 연습문제 5
  //


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
