import com.github.bradleyrumball.autologic.Triangle;
import org.junit.Test;
import static org.junit.Assert.*;

public class test {
	@Test
	public void test1() {
		assertEquals("INVALID", String.valueOf(Triangle.classify(532059208,532059207,-646612838)));
	}

	@Test
	public void test2() {
		assertEquals("EQUILATERAL", String.valueOf(Triangle.classify(554073010,554073010,554073010)));
	}

	@Test
	public void test3() {
		assertEquals("ISOSCELES", String.valueOf(Triangle.classify(1509692187,1509692187,1373425979)));
	}

	@Test
	public void test4() {
		assertEquals("INVALID", String.valueOf(Triangle.classify(-1363900204,-1363900204,-1363900204)));
	}

	@Test
	public void test5() {
		assertEquals("SCALENE", String.valueOf(Triangle.classify(1015551619,1358368986,1358368985)));
	}

	@Test
	public void test6() {
		assertEquals("EQUILATERAL", String.valueOf(Triangle.classify(1367520597,1367520597,1367520597)));
	}

	@Test
	public void test7() {
		assertEquals("INVALID", String.valueOf(Triangle.classify(-610185661,-610185661,-610185661)));
	}

	@Test
	public void test8() {
		assertEquals("EQUILATERAL", String.valueOf(Triangle.classify(341220422,341220422,341220422)));
	}

	@Test
	public void test9() {
		assertEquals("EQUILATERAL", String.valueOf(Triangle.classify(1470416454,1470416454,1470416454)));
	}

	@Test
	public void test10() {
		assertEquals("ISOSCELES", String.valueOf(Triangle.classify(1521377772,1284336177,1521377772)));
	}

	@Test
	public void test11() {
		assertEquals("ISOSCELES", String.valueOf(Triangle.classify(372476223,1331624220,1331624220)));
	}

	@Test
	public void test12() {
		assertEquals("SCALENE", String.valueOf(Triangle.classify(992328357,992328362,997173416)));
	}

	@Test
	public void test13() {
		assertEquals("EQUILATERAL", String.valueOf(Triangle.classify(431794705,431794705,431794705)));
	}

	@Test
	public void test14() {
		assertEquals("SCALENE", String.valueOf(Triangle.classify(270572977,270572972,270572972)));
	}

}