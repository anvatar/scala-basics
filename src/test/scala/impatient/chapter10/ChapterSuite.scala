package impatient.chapter10

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


  //
  // 연습문제 10-3
  //


  //
  // 연습문제 10-4
  //


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
