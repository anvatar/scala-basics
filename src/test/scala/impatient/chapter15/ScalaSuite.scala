package impatient.chapter15

import java.util.concurrent.{Callable, Executors}

import org.scalatest.FunSuite

class ScalaSuite extends FunSuite {

  //
  // 연습문제 15-6
  //

  object Volatile {
    /*
        다음 필드를 @volatile로 선언하지 않으면 두 번째 쓰레드가 무한루프에 빠져야 문제의 의도에 맞을 것 같은데, 실제로는 무한루프에 빠지지 않았다.
        실행 환경의 차이 때문에 다른 환경에서는 무한루프에 빠질 가능성도 있다.
     */
    /*@volatile*/ var done = false
  }

  test("@volatile") {
    val threadPool = Executors.newFixedThreadPool(2)

    val futures = Array(
      threadPool.submit(new Callable[Unit] {
        override def call(): Unit = {
          Thread.sleep(500)
          Volatile.done = true
          println("did")
        }
      }),
      threadPool.submit(new Callable[Unit] {
        override def call(): Unit = {
          while (!Volatile.done) Thread.sleep(100)
          println("done")
        }
      })
    )

    futures.map(_.get)
  }

  //
  // 연습문제 15-7
  //

  class Counter {
    final def countFinal(xs: Seq[Long], partial: Long): Long = if (xs.isEmpty) partial else countFinal(xs.tail, partial + 1)

    def countNonFinal(xs: Seq[Long], partial: Long): Long = if (xs.isEmpty) partial else countNonFinal(xs.tail, partial + 1)
  }

  test("tail recursive optimization") {
    assert((new Counter).countFinal(1L to 1000000, 0) === 1000000)

    intercept[StackOverflowError] {
      assert((new Counter).countNonFinal(1L to 1000000, 0) === 1000000)
    }
  }
}
