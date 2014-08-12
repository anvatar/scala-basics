# 스칼라 기본 스터디 문제 풀이

전체가 하나의 스칼라 프로젝트로 되어 있습니다. git clone 후에 선호하는 IDE에서 import 하거나, activator로 컴파일 및 테스트를 할 수 있습니다.

## 문제 풀이 방법

적절한 패키지에 테스트를 작성하는 것으로 문제 풀이를 합니다. 예를 들어, Scala for the Impatient 1장 연습문제는 impatient.chapter01 패키지에 테스트를 작성합니다.

간단한 테스트들은 하나의 테스트 클래스에 모아서 작성해도 좋습니다. set up, tear down 과정을 포함하거나 반복적으로 사용되는 로직을 별도의 메써드로 추출해야 할 정도의 복잡한 테스트는 각각의 테스트 클래스로 분리해야 합니다.

서술형 문제의 경우, 주석에 내용을 작성합니다. (예: [Scala for the Impatient 1장 연습문제](https://github.com/anvatar/scala-basics/blob/dfcbbd68395c2eb02a84989fec4c0772ac985680/src/test/scala/impatient/chapter01/ChapterSpec.scala) 1번)

코딩 문제의 경우, 실행 가능한 테스트 메써드를 작성합니다. (예: [Scala for the Impatient 1장 연습문제](https://github.com/anvatar/scala-basics/blob/dfcbbd68395c2eb02a84989fec4c0772ac985680/src/test/scala/impatient/chapter01/ChapterSpec.scala) 7번) 필요하면 프로덕션 코드도 작성합니다.

## 스터디 진행

 [anvatar/scala-basics](https://github.com/anvatar/scala-basics) 저장소를 fork 한 뒤 chapter 단위로 다음을 반복합니다.
 
 1. 해당 chapter 문제 풀이를 위한 브랜치를 생성하고 문제 풀이를 합니다.
 1. 작업한 브랜치를 pull request 방식으로 [anvatar/scala-basics](https://github.com/anvatar/scala-basics) 저장소에 보냅니다.
 1. 자신도 문제 풀이를 마친 부분에 대해 다른 사람의 pull request를 리뷰합니다.
 1. 오프라인 모임에서 최종 리뷰를 거쳐 모범 답안을 만들어냅니다. 모범 답안을 master 브랜치에 반영합니다.