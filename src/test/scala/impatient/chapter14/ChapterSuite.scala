package impatient.chapter14

import org.scalatest.FunSuite

class ChapterSuite extends FunSuite {

  //
  // 연습문제 14-1
  //

  /*
    TODO: 일단 생략
   */

  //
  // 연습문제 14-2
  //

  def tupleSwap(pair: (Int, Int)): (Int, Int) = pair match {
    case (a, b) => (b, a)
  }

  test("tupleSwap") {
    assertResult((1, 2))(tupleSwap((2, 1)))
  }

  //
  // 연습문제 14-3
  //

  val arraySwap: PartialFunction[Array[Int], Array[Int]] = {
    case Array(a, b, rest@_*) => Array(b, a) ++ rest
  }

  test("arraySwap") {
    assertResult(Array(1, 2))(arraySwap(Array(2, 1)))
    assertResult(Array(1, 2, 3))(arraySwap(Array(2, 1, 3)))
    intercept[MatchError] {
      arraySwap(Array(1))
    }
  }

  //
  // 연습문제 14-4
  //

  sealed abstract class Item

  case class Article(description: String, price: Double) extends Item

  case class Bundle(description: String, discount: Double, items: Item*) extends Item

  case class Multiple(amount: Int, item: Item) extends Item

  def price(it: Item): Double = it match {
    case Article(_, p) => p
    case Bundle(_, discount, its@_*) => its.map(price).sum - discount
    case Multiple(amount, item) => amount * price(item)
  }

  test("Item") {
    val bundle = Bundle("Father's day special", 20.0,
      Article("Scala for the Impatient", 39.95),
      Bundle("Anchor Distillery Sampler", 10.0,
        Article("Old Potrero Straight Rye Whiskey", 79.95),
        Article("Junîpero Gin", 32.95)))
    assert(price(bundle) == 39.95 + (79.95 + 32.95 - 10.0) - 20.0)

    assert(price(Multiple(7,
      Article("Scala for the Impatient", 39.95))) == 7 * 39.95)

    assert(price(Multiple(3,
      Bundle("Anchor Distillery Sampler", 10.0,
        Article("Old Potrero Straight Rye Whiskey", 79.95),
        Article("Junîpero Gin", 32.95)))) == 3 * (79.95 + 32.95 - 10.0))
  }

  //
  // 연습문제 14-5
  //


  //
  // 연습문제 14-6
  //


  //
  // 연습문제 14-7
  //


  //
  // 연습문제 14-8
  //


  //
  // 연습문제 14-9
  //


  //
  // 연습문제 14-10
  //

}
