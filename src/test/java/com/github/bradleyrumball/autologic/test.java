import com.github.bradleyrumball.autologic.Triangle;
import org.junit.Test;
import static org.junit.Assert.*;

public class test {
	@Test
	public void test1() {
		assertEquals("INVALID", String.valueOf(Triangle.classify(1672024167,-40853048,-769735378)));
	}

	@Test
	public void test2() {
		assertEquals("EQUILATERAL", String.valueOf(Triangle.classify(1986744824,1986744824,1986744824)));
	}

	@Test
	public void test3() {
		assertEquals("INVALID", String.valueOf(Triangle.classify(-246719590,569339723,-246719591)));
	}

	@Test
	public void test4() {
		assertEquals("EQUILATERAL", String.valueOf(Triangle.classify(1988505920,1988505920,1988505920)));
	}

	@Test
	public void test5() {
		assertEquals("INVALID", String.valueOf(Triangle.classify(845495991,-827624305,-827624306)));
	}

	@Test
	public void test6() {
		assertEquals("INVALID", String.valueOf(Triangle.classify(-1455503311,-1455503311,-1455503311)));
	}

	@Test
	public void test7() {
		assertEquals("INVALID", String.valueOf(Triangle.classify(-716842220,-716842220,-716842220)));
	}

	@Test
	public void test8() {
		assertEquals("EQUILATERAL", String.valueOf(Triangle.classify(1895231711,1895231711,1895231711)));
	}

	@Test
	public void test9() {
		assertEquals("EQUILATERAL", String.valueOf(Triangle.classify(677015790,677015790,677015790)));
	}

	@Test
	public void test10() {
		assertEquals("SCALENE", String.valueOf(Triangle.classify(295489448,1754588062,1754588060)));
	}

	@Test
	public void test11() {
		assertEquals("ISOSCELES", String.valueOf(Triangle.classify(2020904490,2020904490,1279169506)));
	}

	@Test
	public void test12() {
		assertEquals("SCALENE", String.valueOf(Triangle.classify(1630105319,353067015,1630105314)));
	}

	@Test
	public void test13() {
		assertEquals("EQUILATERAL", String.valueOf(Triangle.classify(1169860187,1169860187,1169860187)));
	}

	@Test
	public void test14() {
		assertEquals("SCALENE", String.valueOf(Triangle.classify(178769239,250427832,178769239)));
	}

}