import com.github.bradleyrumball.autologic.Triangle;
import org.junit.Test;
import static org.junit.Assert.*;

public class test {
	@Test
	public void test1() {
		assertEquals("INVALID", String.valueOf(Triangle.classify(-558762202,-783333672,-1312395464)));
	}

	@Test
	public void test2() {
		assertEquals("INVALID", String.valueOf(Triangle.classify(-739124616,-739124616,-739124616)));
	}

	@Test
	public void test3() {
		assertEquals("INVALID", String.valueOf(Triangle.classify(1055317796,1055317796,-807821484)));
	}

	@Test
	public void test4() {
		assertEquals("EQUILATERAL", String.valueOf(Triangle.classify(1216317071,1216317071,1216317071)));
	}

	@Test
	public void test5() {
		assertEquals("INVALID", String.valueOf(Triangle.classify(177948929,-883635082,-436550967)));
	}

	@Test
	public void test6() {
		assertEquals("INVALID", String.valueOf(Triangle.classify(-2002192882,-2002192882,-2002192882)));
	}

	@Test
	public void test7() {
		assertEquals("INVALID", String.valueOf(Triangle.classify(-187085992,-187085992,-187085992)));
	}

	@Test
	public void test8() {
		assertEquals("EQUILATERAL", String.valueOf(Triangle.classify(388538833,388538833,388538833)));
	}

	@Test
	public void test9() {
		assertEquals("EQUILATERAL", String.valueOf(Triangle.classify(415358343,415358343,415358343)));
	}

	@Test
	public void test10() {
		assertEquals("SCALENE", String.valueOf(Triangle.classify(-1005681738,1585791353,-1005681737)));
	}

	@Test
	public void test11() {
		assertEquals("ISOSCELES", String.valueOf(Triangle.classify(1440858,1652595791,1652595791)));
	}

	@Test
	public void test12() {
		assertEquals("SCALENE", String.valueOf(Triangle.classify(-1354476521,-1354476516,1169143657)));
	}

	@Test
	public void test13() {
		assertEquals("EQUILATERAL", String.valueOf(Triangle.classify(1184552926,1184552926,1184552926)));
	}

	@Test
	public void test14() {
		assertEquals("SCALENE", String.valueOf(Triangle.classify(1331111829,-598145904,-598145904)));
	}

}