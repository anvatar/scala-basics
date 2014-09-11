package impatient.chapter10

import java.awt.Point

import org.scalatest.FunSuite

class ChapterSuite extends FunSuite {

  //
  // 연습문제 10-1
  //

  trait RectangleLike {
    def translate(dx: Int, dy: Int) = {
      val r = getBounds
      r.translate(dx, dy)
      setFrame(r.getX, r.getY, r.getWidth, r.getHeight)
    }

    def grow(h: Int, v: Int) = {
      val r = getBounds
      r.grow(h, v)
      setFrame(r.getX, r.getY, r.getWidth, r.getHeight)
    }

    def getBounds: java.awt.Rectangle

    def setFrame(x: Double, y: Double, width: Double, height: Double): Unit
  }

  test("RectangleLike") {
    val egg = new java.awt.geom.Ellipse2D.Double(5, 10, 20, 30) with RectangleLike
    egg.translate(10, -10)
    egg.grow(10, 20)

    assertResult(5 + 10 - 10)(egg.getMinX)
    assertResult(10 - 10 - 20)(egg.getMinY)
    assertResult((5 + 10 - 10) + (20 + 10 * 2))(egg.getMaxX)
    assertResult((10 - 10 - 20) + (30 + 20 * 2))(egg.getMaxY)
  }

  //
  // 연습문제 10-2
  //

  class OrderedPoint(x: Int, y: Int) extends java.awt.Point(x, y) with math.Ordered[java.awt.Point] {
    override def compare(that: Point): Int = {
      val temp = this.getX compare that.getX
      if (temp == 0) this.getY compare that.getY
      else temp
    }
  }

  test("OrderedPoint") {
    assert(new OrderedPoint(0, 0) <= new OrderedPoint(0, 0))
    assert(new OrderedPoint(0, 0) >= new OrderedPoint(0, 0))
    assert(new OrderedPoint(0, 0) < new OrderedPoint(1, 1))
    assert(new OrderedPoint(0, 0) > new OrderedPoint(-1, -1))
    assert(new OrderedPoint(0, 0) < new OrderedPoint(1, -1))
    assert(new OrderedPoint(0, 0) > new OrderedPoint(-1, 1))
  }

  //
  // 연습문제 10-3
  //

  /*
      src/test/resources/impatient/ 아래의 BitSet.mm(FreeMind)과 BitSet.png 파일에 일부 슈퍼클래스와 트레이트를 표현함

      이 다이어그램에 따르면, (DFS)

          lin(scala.collection.immutable.BitSet) =
              ... " AbstractIterable " Iterable " IterableLike " Equals " GenIterable " GenIterableLike "
              AbstractTraversable " Traversable " GenTraversable " GenericTraversableTemplate " TraversableLike "
              GenTraversableLike " Parallelizable " TraversableOnce " GenTraversableOnce " FilterMonadic " HasNewBuilder

      와 같이 전개되고, 이는 http://www.scala-lang.org/api/current/#scala.collection.immutable.BitSet 에 "Inherited" 항목에
      나타나는 순서와 같다.
   */

  //
  // 연습문제 10-4
  //

  trait Logger {
    def log(msg: String)
  }

  trait CryptoLogger extends Logger {
    var caesarDistance = 3

    abstract override def log(msg: String): Unit = {
      super.log(msg.map(c => (c + caesarDistance).toChar))
    }
  }

  test("CryptoLogger") {
    class MockLogger extends Logger {
      var lastMessage = ""

      def log(msg: String): Unit = {
        lastMessage = msg
      }
    }

    val mockLogger = new MockLogger with CryptoLogger

    mockLogger.log("abcDEF")
    assertResult("defGHI")(mockLogger.lastMessage)

    mockLogger.caesarDistance = -3

    mockLogger.log("defGHI")
    assertResult("abcDEF")(mockLogger.lastMessage)
  }

  //
  // 연습문제 10-5
  //


  //
  // 연습문제 10-6
  //


  //
  // 연습문제 10-7
  //


  //
  // 연습문제 10-8
  //


  //
  // 연습문제 10-9
  //


  //
  // 연습문제 10-10
  //

}
