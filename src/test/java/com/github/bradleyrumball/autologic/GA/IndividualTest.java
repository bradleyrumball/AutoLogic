package com.github.bradleyrumball.autologic.GA;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;



import static org.junit.Assert.assertEquals;

public class IndividualTest {

    @Test
    public void checkGeneCount() throws ClassNotFoundException, NoSuchMethodException {
        Method method = Class.forName("com.github.bradleyrumball.autologic.junit_test_classes.TrianglesJunit").getMethod("classify", int.class, int.class, int.class);
        Individual individual = new Individual(method, 1);
        assertEquals(2, individual.getGeneCount());
    }

    @Test
    public void checkGeneCount_alternative() throws ClassNotFoundException, NoSuchMethodException {
        Method method = Class.forName("com.github.bradleyrumball.autologic.junit_test_classes.CharMatchJunit").getMethod("bigC", char.class);
        Individual individual = new Individual(method, 1);
        assertEquals(0, individual.getGeneCount());
    }

    @Test
    public void checkMethodreturnvalue() throws ClassNotFoundException, NoSuchMethodException {
        Method method = Class.forName("com.github.bradleyrumball.autologic.junit_test_classes.TrianglesJunit").getMethod("classify", int.class, int.class, int.class);
        Individual individual = new Individual(method, 1);
        assertEquals(null, individual.getMethodReturnValue());
    }

    @Test
    public void checkGetMethod() throws ClassNotFoundException, NoSuchMethodException {
        Method method = Class.forName("com.github.bradleyrumball.autologic.junit_test_classes.TrianglesJunit").getMethod("classify", int.class, int.class, int.class);
        Individual individual = new Individual(method, 1);
        assertEquals(method, individual.getMethod());
    }

}
