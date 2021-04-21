package com.github.bradleyrumball.autologic.test_case_generation;

import com.github.bradleyrumball.autologic.GA.Individual;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;


import static org.junit.Assert.assertEquals;

public class JUnitOutputManagerTest {

    @Test
    public void checkByteCasting() throws ClassNotFoundException, NoSuchMethodException {
        Method method = Class.forName("com.github.bradleyrumball.autologic.junit_test_classes.TrianglesJunit").getMethod("classify", int.class, int.class, int.class);
        Individual individual = new Individual(method, 1);
        ArrayList<Individual> individualArrayList =new ArrayList<Individual>();
        individualArrayList.add(individual);
        JUnitOutputManager jUnitOutputManager = new JUnitOutputManager(individualArrayList, "ClassPath", "ClassName", "methodname", "outputpath");

        assertEquals("(byte)", jUnitOutputManager.casting("class java.lang.Byte"));

    }

    @Test
    public void checkShortCasting() throws ClassNotFoundException, NoSuchMethodException {
        Method method = Class.forName("com.github.bradleyrumball.autologic.junit_test_classes.TrianglesJunit").getMethod("classify", int.class, int.class, int.class);
        Individual individual = new Individual(method, 1);
        ArrayList<Individual> individualArrayList =new ArrayList<Individual>();
        individualArrayList.add(individual);
        JUnitOutputManager jUnitOutputManager = new JUnitOutputManager(individualArrayList, "ClassPath", "ClassName", "methodname", "outputpath");

        assertEquals("(short)", jUnitOutputManager.casting("class java.lang.Short"));

    }

    @Test
    public void checkLongCasting() throws ClassNotFoundException, NoSuchMethodException {
        Method method = Class.forName("com.github.bradleyrumball.autologic.junit_test_classes.TrianglesJunit").getMethod("classify", int.class, int.class, int.class);
        Individual individual = new Individual(method, 1);
        ArrayList<Individual> individualArrayList =new ArrayList<Individual>();
        individualArrayList.add(individual);
        JUnitOutputManager jUnitOutputManager = new JUnitOutputManager(individualArrayList, "ClassPath", "ClassName", "methodname", "outputpath");

        assertEquals("(long)", jUnitOutputManager.casting("class java.lang.Long"));

    }

    @Test
    public void checkFloatCasting() throws ClassNotFoundException, NoSuchMethodException {
        Method method = Class.forName("com.github.bradleyrumball.autologic.junit_test_classes.TrianglesJunit").getMethod("classify", int.class, int.class, int.class);
        Individual individual = new Individual(method, 1);
        ArrayList<Individual> individualArrayList =new ArrayList<Individual>();
        individualArrayList.add(individual);
        JUnitOutputManager jUnitOutputManager = new JUnitOutputManager(individualArrayList, "ClassPath", "ClassName", "methodname", "outputpath");

        assertEquals("(float)", jUnitOutputManager.casting("class java.lang.Float"));

    }

    @Test
    public void checkStringCasting() throws ClassNotFoundException, NoSuchMethodException {
        Method method = Class.forName("com.github.bradleyrumball.autologic.junit_test_classes.TrianglesJunit").getMethod("classify", int.class, int.class, int.class);
        Individual individual = new Individual(method, 1);
        ArrayList<Individual> individualArrayList =new ArrayList<Individual>();
        individualArrayList.add(individual);
        JUnitOutputManager jUnitOutputManager = new JUnitOutputManager(individualArrayList, "ClassPath", "ClassName", "methodname", "outputpath");

        assertEquals("", jUnitOutputManager.casting("class java.lang.String"));

    }

    @Test
    public void checkIntCasting() throws ClassNotFoundException, NoSuchMethodException {
        Method method = Class.forName("com.github.bradleyrumball.autologic.junit_test_classes.TrianglesJunit").getMethod("classify", int.class, int.class, int.class);
        Individual individual = new Individual(method, 1);
        ArrayList<Individual> individualArrayList =new ArrayList<Individual>();
        individualArrayList.add(individual);
        JUnitOutputManager jUnitOutputManager = new JUnitOutputManager(individualArrayList, "ClassPath", "ClassName", "methodname", "outputpath");

        assertEquals("", jUnitOutputManager.casting("class java.lang.Integer"));

    }

    @Test
    public void checkDoubleCasting() throws ClassNotFoundException, NoSuchMethodException {
        Method method = Class.forName("com.github.bradleyrumball.autologic.junit_test_classes.TrianglesJunit").getMethod("classify", int.class, int.class, int.class);
        Individual individual = new Individual(method, 1);
        ArrayList<Individual> individualArrayList =new ArrayList<Individual>();
        individualArrayList.add(individual);
        JUnitOutputManager jUnitOutputManager = new JUnitOutputManager(individualArrayList, "ClassPath", "ClassName", "methodname", "outputpath");

        assertEquals("", jUnitOutputManager.casting("class java.lang.Double"));

    }

}
