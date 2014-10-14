package impatient.chapter15;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JavaSuite {

	//
	// 연습문제 15-4
	//

	@Test
	public void sum() throws Exception {
		assertEquals(15, ScalaUtil.sum(1, 2, 3, 4, 5));
	}

}
