package chapter06

import org.scalatest.{FlatSpec, Matchers}

class Chapter06 extends FlatSpec with Matchers {
  //
  // 연습문제 1
  //

  /*
          scala> 3.
          %   +   >    >>>            isInstanceOf   toDouble   toLong     unary_+   |
          &   -   >=   ^              toByte         toFloat    toShort    unary_-
          *   /   >>   asInstanceOf   toChar         toInt      toString   unary_~
   */


  //
  // 연습문제 2
  //
  {
    class UnitConversion(factor: Double) {
      def apply(value: Double): Double = value * factor
    }

    object InchesToCentimeters extends UnitConversion(2.54)
    object GallonsToLiters extends UnitConversion(3.785412)
    object MilesToKilometers extends UnitConversion(1.609344)

    assertResult(2.54)(InchesToCentimeters(1))
    assertResult(3.785412)(GallonsToLiters(1))
    assertResult(1.609344)(MilesToKilometers(1))

    val testData = Array(
      (InchesToCentimeters, 2.54),
      (GallonsToLiters, 3.785412),
      (MilesToKilometers, 1.609344)
    )

    for ((uc, expected) <- testData)
      assertResult(expected)(uc(1))
  }

  //
  // 연습문제 3
  //

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
