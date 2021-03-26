import com.github.bradleyrumball.autologic.Triangle;
import org.junit.Test;
import static org.junit.Assert.*;

public class src\main\resources\test {
	@Test
	public void test1() {
		assertTrue(Triangle.classify(5,5,5) == Triangle.Type.EQUILATERAL);
	}

}