package impatient.chapter07

// 이 파일에서는 impatient.chapter07 패키지에는 접근할 수 있지만 impatient 패키지에는 접근할 수 없음

object TopOfFile2 {
  //val x = new X     => 컴파일 에러
  val x = new _root_.impatient.X
  val y = new Y
}