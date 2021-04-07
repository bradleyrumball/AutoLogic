import com.github.bradleyrumball.autologic.Triangle;
import org.junit.Test;
import static org.junit.Assert.*;

public class TriangleclassifyTest {
	@Test
	public void test1() {
		assertEquals("INVALID", String.valueOf(classify(17,-449374340,1720949146)));
	}

	@Test
	public void test2() {
		assertEquals("EQUILATERAL", String.valueOf(classify(13,13,13)));
	}

	@Test
	public void test3() {
		assertEquals("INVALID", String.valueOf(classify(-12,-12,-28308035)));
	}

	@Test
	public void test4() {
		assertEquals("EQUILATERAL", String.valueOf(classify(13,13,13)));
	}

	@Test
	public void test5() {
		assertEquals("INVALID", String.valueOf(classify(-1,-459605950,-459605950)));
	}

	@Test
	public void test6() {
		assertEquals("EQUILATERAL", String.valueOf(classify(13,13,13)));
	}

	@Test
	public void test7() {
		assertEquals("INVALID", String.valueOf(classify(-1499153614,-889080371,-889080371)));
	}

	@Test
	public void test8() {
		assertEquals("EQUILATERAL", String.valueOf(classify(13,13,13)));
	}

	@Test
	public void test9() {
		assertEquals("EQUILATERAL", String.valueOf(classify(13,13,13)));
	}

	@Test
	public void test10() {
		assertEquals("ISOSCELES", String.valueOf(classify(2105768255,2105768255,13)));
	}

	@Test
	public void test11() {
		assertEquals("ISOSCELES", String.valueOf(classify(2105768255,2105768255,13)));
	}

	@Test
	public void test12() {
		assertEquals("SCALENE", String.valueOf(classify(7,200271752,200271747)));
	}

	@Test
	public void test13() {
		assertEquals("EQUILATERAL", String.valueOf(classify(13,13,13)));
	}

	@Test
	public void test14() {
		assertEquals("SCALENE", String.valueOf(classify(1896334083,1896334083,2042572839)));
	}

}