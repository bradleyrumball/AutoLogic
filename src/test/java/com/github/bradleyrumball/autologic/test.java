package com.github.bradleyrumball.autologic;

import com.github.bradleyrumball.autologic.Triangle;
import org.junit.Test;
import static org.junit.Assert.*;

public class test {
	@Test
	public void test1() {
		assertTrue(Triangle.classify(5,5,5) == Triangle.Type.EQUILATERAL);
	}

}