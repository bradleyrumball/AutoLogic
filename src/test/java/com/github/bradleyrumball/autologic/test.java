import com.github.bradleyrumball.autologic.Triangle;
import org.junit.Test;
import static org.junit.Assert.*;

public class test {
	@Test
	public void test1() {
		assertTrue(Triangle.classify(-2015854138,-2015854139,978297693) == SCALENE);
	}

	@Test
	public void test2() {
		assertTrue(Triangle.classify(-423621282,-423621282,-423621282) == INVALID);
	}

	@Test
	public void test3() {
		assertTrue(Triangle.classify(-588952642,-1098516640,2090840836) == SCALENE);
	}

	@Test
	public void test4() {
		assertTrue(Triangle.classify(1091528759,1091528759,1091528759) == EQUILATERAL);
	}

	@Test
	public void test5() {
		assertTrue(Triangle.classify(-1313191332,-1313191333,-2104131035) == INVALID);
	}

	@Test
	public void test6() {
		assertTrue(Triangle.classify(-167333880,-167333880,-167333880) == INVALID);
	}

	@Test
	public void test7() {
		assertTrue(Triangle.classify(-883321511,-883321511,-883321511) == INVALID);
	}

	@Test
	public void test8() {
		assertTrue(Triangle.classify(322577567,322577567,322577567) == EQUILATERAL);
	}

	@Test
	public void test9() {
		assertTrue(Triangle.classify(311676697,311676697,311676697) == EQUILATERAL);
	}

	@Test
	public void test10() {
		assertTrue(Triangle.classify(1968572936,-963994327,-1280950344) == SCALENE);
	}

	@Test
	public void test11() {
		assertTrue(Triangle.classify(1931384227,1931384227,1931384222) == ISOSCELES);
	}

	@Test
	public void test12() {
		assertTrue(Triangle.classify(-1609388685,-1609388680,1941210955) == SCALENE);
	}

	@Test
	public void test13() {
		assertTrue(Triangle.classify(596171788,596171788,596171788) == EQUILATERAL);
	}

	@Test
	public void test14() {
		assertTrue(Triangle.classify(1428167234,1428167234,1428167239) == SCALENE);
	}

}