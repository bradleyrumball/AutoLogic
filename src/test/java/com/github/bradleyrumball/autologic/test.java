import com.github.bradleyrumball.autologic.Triangle;
import org.junit.Test;
import static org.junit.Assert.*;

public class test {
	@Test
	public void test1() {
		assertEquals("INVALID", String.valueOf(Triangle.classify(-1445947101,-1445947102,-1445947100)));
	}

	@Test
	public void test2() {
		assertEquals("INVALID", String.valueOf(Triangle.classify(-510200684,-510200684,-510200684)));
	}

	@Test
	public void test3() {
		assertEquals("INVALID", String.valueOf(Triangle.classify(-633641946,892545740,-2115315264)));
	}

	@Test
	public void test4() {
		assertEquals("INVALID", String.valueOf(Triangle.classify(-673805938,-673805938,-673805938)));
	}

	@Test
	public void test5() {
		assertEquals("INVALID", String.valueOf(Triangle.classify(175193357,-729544612,-729544613)));
	}

	@Test
	public void test6() {
		assertEquals("EQUILATERAL", String.valueOf(Triangle.classify(1232115084,1232115084,1232115084)));
	}

	@Test
	public void test7() {
		assertEquals("INVALID", String.valueOf(Triangle.classify(-1240995992,-1240995992,-1240995992)));
	}

	@Test
	public void test8() {
		assertEquals("EQUILATERAL", String.valueOf(Triangle.classify(682301376,682301376,682301376)));
	}

	@Test
	public void test9() {
		assertEquals("EQUILATERAL", String.valueOf(Triangle.classify(1744965751,1744965751,1744965751)));
	}

	@Test
	public void test10() {
		assertEquals("ISOSCELES", String.valueOf(Triangle.classify(1852241061,1852241061,1852241056)));
	}

	@Test
	public void test11() {
		assertEquals("ISOSCELES", String.valueOf(Triangle.classify(383855367,383855367,383855362)));
	}

	@Test
	public void test12() {
		assertEquals("SCALENE", String.valueOf(Triangle.classify(1104245582,1399597963,1399597968)));
	}

	@Test
	public void test13() {
		assertEquals("EQUILATERAL", String.valueOf(Triangle.classify(2054593217,2054593217,2054593217)));
	}

	@Test
	public void test14() {
		assertEquals("SCALENE", String.valueOf(Triangle.classify(803050175,803050170,803050170)));
	}

}