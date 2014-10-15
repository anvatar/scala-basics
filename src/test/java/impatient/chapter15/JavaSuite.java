package impatient.chapter15;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JavaSuite {

	//
	// 연습문제 15-1
	//

	/* TODO: 생략함 */

	//
	// 연습문제 15-4
	//

	@Test
	public void sum() throws Exception {
		assertEquals(15, ScalaUtil.sum(1, 2, 3, 4, 5));
	}

	//
	// 연습문제 15-5
	//

	@Test
	public void readAll() {
		/*
			ScalaUtil.readAll()이 다음과 같이 선언되어 있으면

				def readAll(filePath: String) = ???

			다음과 같이 작성해도 IOException을 catch 하라는 컴파일 에러가 발생하지 않는다.

				ScalaUtil.readAll("/tmp/non-existing/filepath");
		 */

		try {
			ScalaUtil.readAll("/tmp/non-existing/filepath");
		} catch (IOException e) {	/* IOException을 catch 하지 않으면 컴파일 에러가 발생한다. */
			// do nothing
		}
	}
}
