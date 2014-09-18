package impatient.chapter11

import org.scalatest.FunSuite

import scala.math.abs

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

  class Fraction(n: Int, d: Int) {
    /*
     * 책에서 제공
     */

    private val num: Int = if (d == 0) 1 else n * sign(d) / gcd(n, d)
    private val den: Int = if (d == 0) 0 else d * sign(d) / gcd(n, d)

    override def toString = num + "/" + den

    def sign(a: Int) = if (a > 0) 1 else if (a < 0) -1 else 0

    def gcd(a: Int, b: Int): Int = if (b == 0) abs(a) else gcd(b, a % b)

    /*
     * IntelliJ IDEA로 생성
     */

    def canEqual(other: Any): Boolean = other.isInstanceOf[Fraction]

    override def equals(other: Any): Boolean = other match {
      case that: Fraction =>
        (that canEqual this) &&
          num == that.num &&
          den == that.den
      case _ => false
    }

    override def hashCode(): Int = {
      val state = Seq(num, den)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
    }

    /*
     * 직접 구현
     */

    def +(other: Fraction): Fraction = Fraction(this.num * other.den + other.num * this.den, this.den * other.den)

    def -(other: Fraction): Fraction = this + Fraction(-1 * other.num, other.den)

    def *(other: Fraction): Fraction = Fraction(this.num * other.num, this.den * other.den)

    def /(other: Fraction): Fraction = this * Fraction(other.den, other.num)

  }

  object Fraction {
    def apply(n: Int, d: Int) = new Fraction(n, d)
  }

  test("Fraction") {
    assertResult(Fraction(7, 6))(Fraction(1, 2) + Fraction(2, 3))
    assertResult(Fraction(-5, 12))(Fraction(1, 3) - Fraction(3, 4))
    assertResult(Fraction(-7, 8))(Fraction(7, 6) * Fraction(3, -4))
    assertResult(Fraction(7, 4))(Fraction(7, 6) / Fraction(2, 3))
  }

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
