package impatient.chapter07

import org.scalatest.FunSuite

class ChapterSuite extends FunSuite {
  //
  // 연습문제 7-1
  //

  /*
    프로덕션 코드의 Classes.scala, TopOfFile1.scala, TopOfFile2.scala 참고
   */

  //
  // 연습문제 7-2
  //

  test("Package scope puzzler") {
    /*
      Classes.scala에 있는 _root_.impatient.Z 클래스의 객체가 생성되었다고 생각하기 쉽지만,
      실제로는 hidden/Z.scala에 있는 _root_.impatient.chapter07.impatient.Z 클래스의 객체가 생성됨
     */
    val z = new impatient.Z

    /*
      아래 테스트는 다음과 같은 에러 메시지와 함께 실패한다:
          Expected "This is []Z", but got "This is [not ]Z"
     */
    //assertResult("This is Z")(z.say)

    /*
      대신 아래 테스트는 성공한다.
     */
    assertResult("This is not Z")(z.say)
  }

  //
  // 연습문제 7-3
  //

  test("package random") {
    import random._

    setSeed(System.currentTimeMillis().toInt)

    for (i <- 1 to 5)
      println("nextInt(): " + nextInt())

    for (i <- 1 to 5)
      println("nextDouble(): " + nextDouble())
  }

  //
  // 연습문제 7-4
  //

  /*
      본문에도 나오는 얘기지만, 패키지에 포함되어 있는 함수나 변수를 어느 소스 파일에서 찾아야 하는지를 명확하게 하려면 정의가 한 곳에 모여 있어야 한다.

      한 벌만 존재할 수 있는 패키지 함수와 변수의 특성도 기존 오브젝트 개념과 잘 맞는다.

      패키지 오브젝트와 같은 어떤 단위로 묶여 있지 않으면 private 변수 등을 이용하여 내부 구현을 감추기 곤란하다.
   */

  //
  // 연습문제 7-5
  //

  /*
      모든 com.** 패키지에서 이 함수에 접근할 수 있다는 의미.

      com.** 패키지 범위는 지나치게 넓어서 private[com] 과 같은 한정자는 유용하게 사용될 수 없다.
   */

  //
  // 연습문제 7-6
  //

  {
    import java.util.{HashMap => JavaHashMap}
    import scala.collection.mutable.{HashMap => ScalaHashMap}

    def copyFromJavaHashMapIntoScalaHashMap[K, V](javaHashMap: JavaHashMap[K, V], scalaHashMap: ScalaHashMap[K, V]): Unit = {
      val entryIterator = javaHashMap.entrySet().iterator()
      while (entryIterator.hasNext) {
        val entry = entryIterator.next()
        scalaHashMap(entry.getKey) = entry.getValue
      }
    }

    test("copy from Java HashMap into Scala HashMap") {
      val javaHashMap = new JavaHashMap[String, String]()
      javaHashMap.put("황", "Ciel")
      javaHashMap.put("안", "Holden")
      javaHashMap.put("박", "Peter")

      val scalaHashMap = ScalaHashMap[String, String]()
      copyFromJavaHashMapIntoScalaHashMap(javaHashMap, scalaHashMap)

      assertResult(3)(scalaHashMap.size)
      assertResult("Ciel")(scalaHashMap("황"))
      assertResult("Holden")(scalaHashMap("안"))
      assertResult("Peter")(scalaHashMap("박"))
    }
  }

  //
  // 연습문제 7-7
  //

  /*
      위와 같이 작성했을 때 더 안쪽으로 옮길 수 있는 import문은 없는 것 같다.
   */

  //
  // 연습문제 7-8
  //

  test("Different math.BigDecimal's") {
    val bigDecimal1: math.BigDecimal = math.BigDecimal(1)

    import java._
    val bigDecimal2: math.BigDecimal = new math.BigDecimal(1)

    assert(bigDecimal1 !== bigDecimal2)
  }

  /*
      위에서 보듯이 java._를 import 하는 것은 소스 코드를 이해하기 어렵게 만들 가능성이 있다.

      공통 클래스는 없지만, scala.annotation와 javax.annotation도 패키지 이름이 겹친다.
      java._와 javax._ 사이에는 lang, rmi, security, sql 과 같은 서브패키지 이름이 겹친다.
   */

  /*
      서브패키지 이름이 겹칠 경우, 다음 각각은 컴파일이 되지만,
   */

  {
    import java._
    val timestamp: sql.Timestamp = null
    assert(timestamp === null)
  }
  {
    import javax._
    val rowSet: sql.RowSet = null
    assert(rowSet === null)
  }

  /*
      다음과 같이 java.sql, javax.sql 패키지를 같은 범위 내에서 사용하려고 하면 컴파일 에러가 발생한다.

          import java._
          import javax._

          val timestamp: sql.Timestamp = null
          timestamp

          val rowSet: sql.RowSet = null
          rowSet
   */

  //
  // 연습문제 7-9
  //

  /*
      SecureGreeter.scala
   */

  //
  // 연습문제 7-10
  //

  /*
      다음은 덮어써진다:
          Boolean
          Byte
          Cloneable
          Double
          Float
          Iterable
          Long
          Short

      다음도 덮어써지지만, type alias에 불과하기 때문에 혼용해도 괜찮다:
          AbstractMethodError =
          ArrayIndexOutOfBoundsException =
          ClassCastException =
          Error =
          Exception =
          IllegalArgumentException =
          IndexOutOfBoundsException =
          InterruptedException =
          NullPointerException =
          RuntimeException =
          StringIndexOutOfboundsException =
          Throwable =
          UnsupportedOperationException =
   */
}
