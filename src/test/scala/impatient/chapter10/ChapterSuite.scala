package impatient.chapter10

import java.awt.Point
import java.beans.{PropertyChangeEvent, PropertyChangeListener}

import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer

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

  trait MockLogger extends Logger {
    val messages = new ArrayBuffer[String]()

    def log(msg: String): Unit = {
      messages += msg
    }
  }

  test("CryptoLogger") {
    val mockLogger = new MockLogger with CryptoLogger

    mockLogger.log("abcDEF")
    assertResult("defGHI")(mockLogger.messages.last)

    mockLogger.caesarDistance = -3

    mockLogger.log("defGHI")
    assertResult("abcDEF")(mockLogger.messages.last)
  }

  //
  // 연습문제 10-5
  //

  trait PropertyChangeSupport {
    val propertyChangeSupport = new java.beans.PropertyChangeSupport(this)

    def addPropertyChangeListener(listener: PropertyChangeListener): Unit = propertyChangeSupport.addPropertyChangeListener(listener)

    def removePropertyChangeListener(listener: PropertyChangeListener): Unit = propertyChangeSupport.removePropertyChangeListener(listener)

    def getPropertyChangeListeners: Array[PropertyChangeListener] = propertyChangeSupport.getPropertyChangeListeners

    def addPropertyChangeListener(propertyName: String, listener: PropertyChangeListener): Unit = propertyChangeSupport.addPropertyChangeListener(propertyName, listener)

    def removePropertyChangeListener(propertyName: String, listener: PropertyChangeListener): Unit = propertyChangeSupport.removePropertyChangeListener(propertyName, listener)

    def getPropertyChangeListeners(propertyName: String): Array[PropertyChangeListener] = propertyChangeSupport.getPropertyChangeListeners(propertyName)

    def firePropertyChange(propertyName: String, oldValue: scala.Any, newValue: scala.Any): Unit = propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue)

    def firePropertyChange(propertyName: String, oldValue: Int, newValue: Int): Unit = propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue)

    def firePropertyChange(propertyName: String, oldValue: Boolean, newValue: Boolean): Unit = propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue)

    def firePropertyChange(evt: PropertyChangeEvent): Unit = propertyChangeSupport.firePropertyChange(evt)

    def fireIndexedPropertyChange(propertyName: String, index: Int, oldValue: scala.Any, newValue: scala.Any): Unit = propertyChangeSupport.fireIndexedPropertyChange(propertyName, index, oldValue, newValue)

    def fireIndexedPropertyChange(propertyName: String, index: Int, oldValue: Int, newValue: Int): Unit = propertyChangeSupport.fireIndexedPropertyChange(propertyName, index, oldValue, newValue)

    def fireIndexedPropertyChange(propertyName: String, index: Int, oldValue: Boolean, newValue: Boolean): Unit = propertyChangeSupport.fireIndexedPropertyChange(propertyName, index, oldValue, newValue)

    def hasListeners(propertyName: String): Boolean = propertyChangeSupport.hasListeners(propertyName)
  }

  class PropertyChangeSupportedPoint(x: Int, y: Int) extends java.awt.Point(x, y) with PropertyChangeSupport {
    override def setLocation(p: Point): Unit = {
      val oldLocation = getLocation
      super.setLocation(p)
      firePropertyChange(oldLocation)
    }

    override def setLocation(x: Int, y: Int): Unit = {
      val oldLocation = getLocation
      super.setLocation(x, y)
      firePropertyChange(oldLocation)
    }

    override def setLocation(x: Double, y: Double): Unit = {
      val oldLocation = getLocation
      super.setLocation(x, y)
      firePropertyChange(oldLocation)
    }

    override def move(x: Int, y: Int): Unit = {
      val oldLocation = getLocation
      super.move(x, y)
      firePropertyChange(oldLocation)
    }

    override def translate(dx: Int, dy: Int): Unit = {
      val oldLocation = getLocation
      super.translate(dx, dy)
      firePropertyChange(oldLocation)
    }

    private def firePropertyChange(oldLocation: Point) {
      firePropertyChange("x", oldLocation.getX, getX)
      firePropertyChange("y", oldLocation.getY, getY)
    }
  }

  test("PropertyChangeSupport") {
    val listener = new PropertyChangeListener {
      val changedPropertyNames = ArrayBuffer[String]()

      override def propertyChange(evt: PropertyChangeEvent): Unit = {
        changedPropertyNames += evt.getPropertyName
      }
    }

    val point = new PropertyChangeSupportedPoint(0, 0)
    point.addPropertyChangeListener(listener)

    point.move(1, 1)
    assertResult(Array("x", "y"))(listener.changedPropertyNames)

    point.translate(1, 0)
    assertResult(Array("x", "y", "x"))(listener.changedPropertyNames)
  }

  //
  // 연습문제 10-6
  //

  /*
      자바에서는 JContainer 클래스가 JComponent 클래스와 Container 클래스를 동시에 확장할 수 없다.
      JComponent와 Container에 구현되어 있는 메써드를 상속받아 이용하고 싶은 상황이므로 어느 한 쪽을 인터페이스로 변경하기 곤란하다.

      스칼라에서는 Component - JComponent 계층은 클래스 상속 관계를 유지하고,
      Container - JContainer 계층은 ContainerLike - JContainerLike 계층 구조의 트레이트로 변경하면 될 것 같다.
   */

  //
  // 연습문제 10-7
  //

  /*
      TODO: 건너뜀
   */

  //
  // 연습문제 10-8
  //

  trait BufferedInputStream extends java.io.InputStream {
    val bufferSize = 8 * 1024
    private val buffer = ArrayBuffer[Int]()

    abstract override def read(): Int = {
      if (buffer.isEmpty) {
        var res = 0
        while (res != -1 && buffer.length < bufferSize) {
          res = super.read()
          if (res != -1) buffer += res
        }
      }

      if (buffer.isEmpty) -1
      else buffer.remove(0)
    }
  }

  class MockInputStream(str: String) extends java.io.InputStream {
    val data = str.toCharArray.toBuffer

    override def read(): Int = {
      if (data.isEmpty) -1
      else data.remove(0)
    }
  }

  test("BufferedInputStream") {
    val str = "1234567890" * 2000

    val inputStream = new MockInputStream(str) with BufferedInputStream
    for (c <- str) {
      val read = inputStream.read()
      assertResult(c)(read.toChar)

      val readDataLength = str.length - inputStream.data.length
      assert(readDataLength == str.length || readDataLength % inputStream.bufferSize == 0)
    }
    inputStream.close()
  }

  //
  // 연습문제 10-9
  //

  class LoggedMockInputStream(str: String) extends MockInputStream(str) with MockLogger {
    override def read(): Int = {
      val c = super.read()
      if (c != -1) log("read: [" + c.toChar + "]")
      c
    }
  }

  test("BufferedInputStream with Logger") {
    val str = "1234567890" * 2000

    val inputStream = new LoggedMockInputStream(str) with BufferedInputStream
    for (c <- str) {
      val read = inputStream.read()
      assertResult(c)(read.toChar)

      val readDataLength = inputStream.messages.length
      assert(readDataLength == str.length || readDataLength % inputStream.bufferSize == 0)
    }
    inputStream.close()
  }

  //
  // 연습문제 10-10
  //

  class IterableInputStream(private val in: java.io.InputStream) extends java.io.InputStream with Iterable[Byte] {
    override def read(): Int = in.read()

    override def iterator: Iterator[Byte] = new MyIterator

    override def close(): Unit = {
      in.close()
      super.close()
    }

    private class MyIterator extends Iterator[Byte] {
      var ahead = read()

      override def hasNext: Boolean = ahead != -1

      override def next(): Byte = {
        val res = ahead
        ahead = read()
        res.toByte
      }
    }
  }

  test("IterableInputStream") {
    val str = "1234567890" * 2000
    val strBuffer = str.toBuffer

    val inputStream = new IterableInputStream(new MockInputStream(str))
    for (c <- inputStream)
      assertResult(strBuffer.remove(0))(c)
    inputStream.close()
  }
}
