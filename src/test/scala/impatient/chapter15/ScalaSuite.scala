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

  //
  // 연습문제 15-8
  //

  /*
      javap로 보면, 다음과 같이 primitive 타입을 위한 메써드가 자동 생성되어 있는 것을 볼 수 있다.

          Compiled from "ScalaUtil.scala"
          public final class impatient.chapter15.ScalaUtil$ {
            ...
            public <T extends java/lang/Object> boolean allDifferent(T, T, T);
            public boolean allDifferent$mZc$sp(boolean, boolean, boolean);
            public boolean allDifferent$mBc$sp(byte, byte, byte);
            public boolean allDifferent$mCc$sp(char, char, char);
            public boolean allDifferent$mDc$sp(double, double, double);
            public boolean allDifferent$mFc$sp(float, float, float);
            public boolean allDifferent$mIc$sp(int, int, int);
            public boolean allDifferent$mJc$sp(long, long, long);
            public boolean allDifferent$mSc$sp(short, short, short);
            public boolean allDifferent$mVc$sp(scala.runtime.BoxedUnit, scala.runtime.BoxedUnit, scala.runtime.BoxedUnit);
          }
   */

  //
  // 연습문제 15-9
  //

  /*
        $ javap -classpath /.../scala-library-2.11.1.jar scala.collection.immutable.Range | grep foreach
          public final <U extends java/lang/Object> void foreach(scala.Function1<java.lang.Object, U>);
          public final void foreach$mVc$sp(scala.Function1<java.lang.Object, scala.runtime.BoxedUnit>);

        trait Function1[@specialized(scala.Int, scala.Long, scala.Float, scala.Double) -T1,
                        @specialized(scala.Unit, scala.Boolean, scala.Int, scala.Float, scala.Long, scala.Double) +R] extends AnyRef

      모르겠다.

      Range#foreach의 특성 상 Function1[Int, Unit] 타입의 함수 객체를 인자로 받는 경우가 많을텐데,
      어쩌면 Function1도 [Int, Unit]에 대해 특화된 버전을 제공하므로 특화된 Range#foreach[Function[Int, Unit]]이 성능 상의 이점이 있는 게 아닐까?
   */
}
