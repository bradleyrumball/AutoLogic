import com.github.bradleyrumball.autologic.Triangle;
import org.junit.Test;
import static org.junit.Assert.*;

public class test {
	@Test
	public void test1() {
		assertEquals("INVALID", String.valueOf(Triangle.classify(1412467282,-481112183,-481112183)));
	}

	@Test
	public void test2() {
		assertEquals("EQUILATERAL", String.valueOf(Triangle.classify(1270528738,1270528738,1270528738)));
	}

	@Test
	public void test3() {
		assertEquals("INVALID", String.valueOf(Triangle.classify(-260101909,887723002,-1692376678)));
	}

	@Test
	public void test4() {
		assertEquals("EQUILATERAL", String.valueOf(Triangle.classify(967529501,967529501,967529501)));
	}

	@Test
	public void test5() {
		assertEquals("INVALID", String.valueOf(Triangle.classify(-1245312838,-1277391257,-1277391257)));
	}

	@Test
	public void test6() {
		assertEquals("EQUILATERAL", String.valueOf(Triangle.classify(1020335573,1020335573,1020335573)));
	}

	@Test
	public void test7() {
		assertEquals("INVALID", String.valueOf(Triangle.classify(-348479382,-348479382,-348479382)));
	}

	@Test
	public void test8() {
		assertEquals("EQUILATERAL", String.valueOf(Triangle.classify(137929443,137929443,137929443)));
	}

	@Test
	public void test9() {
		assertEquals("EQUILATERAL", String.valueOf(Triangle.classify(1486570068,1486570068,1486570068)));
	}

	@Test
	public void test10() {
		assertEquals("SCALENE", String.valueOf(Triangle.classify(1745720706,1312774156,1745720707)));
	}

	@Test
	public void test11() {
		assertEquals("ISOSCELES", String.valueOf(Triangle.classify(2145583967,197120316,2145583967)));
	}

	@Test
	public void test12() {
		assertEquals("SCALENE", String.valueOf(Triangle.classify(1757892859,1757892854,1660481380)));
	}

	@Test
	public void test13() {
		assertEquals("EQUILATERAL", String.valueOf(Triangle.classify(1581117639,1581117639,1581117639)));
	}

	@Test
	public void test14() {
		assertEquals("SCALENE", String.valueOf(Triangle.classify(1827937056,1827937056,1827937061)));
	}

}