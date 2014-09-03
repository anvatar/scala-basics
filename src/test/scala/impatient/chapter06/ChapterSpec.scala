package impatient.chapter06

import org.scalatest._

class ChapterSpec extends FlatSpec with Matchers {
  //
  // 연습문제 1
  //
  /*
          scala> object Conversions {
               |   def inchesToCentimeters(inches: Double) = { inches * 2.54 }
               |   def gallonsToLiters(gallons: Double) = { gallons * 3.78541178 }
               |   def milesToKilometers(miles: Double) = { miles * 1.609344 }
               | }
          defined object Conversions

          scala> Conversions.inchesToCentimeters(1)
          res0: Double = 2.54
   */
  "ConversionsTest" should "be 2.54, 3.78541178, 1.609344" in {
    2.54 shouldEqual Conversions.inchesToCentimeters(1)
    3.78541178 shouldEqual Conversions.gallonsToLiters(1)
    1.609344 shouldEqual Conversions.milesToKilometers(1)
  }

  //
  // 연습문제 2
  //
  /*
          scala> abstract class UnitConversion(val conversionValue: Double) {
               |   def convert(value: Double) = { value * conversionValue }
               | }
          defined class UnitConversion

          scala> object InchesToCentimeters extends UnitConversion(2.54) {}
          defined object InchesToCentimeters

          scala> object GallonsToLiters extends UnitConversion(3.78541178) {}
          defined object GallonsToLiters

          scala> object MilesToKilometers extends UnitConversion(1.609344) {}
          defined object MilesToKilometers

          scala> InchesToCentimeters.convert(1)
          res3: Double = 2.54
   */
  "UnitConversionsTest" should "be 2.54, 3.78541178, 1.609344" in {
    2.54 shouldEqual InchesToCentimeters.convert(1)
    3.78541178 shouldEqual GallonsToLiters.convert(1)
    1.609344 shouldEqual MilesToKilometers.convert(1)
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
