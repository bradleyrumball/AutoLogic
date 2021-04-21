package com.github.bradleyrumball.autologic.GA;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Population now requires method reflection object hence deprecation
 */


public class PopulationTest {

    @Test
    public void checkGetIndividuals() {
        Individual individual = mock(Individual.class);
        ArrayList list = new ArrayList<>(Arrays.asList(individual));
        Population population = new Population(list);
        assertEquals(population.getIndividuals(), list);
    }

    @Test
    public void checkGetIndividual() {
        Individual individual = mock(Individual.class);
        Individual individual2 = mock(Individual.class);

        ArrayList list = new ArrayList<>(Arrays.asList(individual, individual2));
        Population population = new Population(list);
        assertEquals(population.getIndividual(0), individual);
        assertEquals(population.getIndividual(1), individual2);
    }

    @Test
    public void checkAddIndividual() {
        Individual individual = mock(Individual.class);
        Individual individual2 = mock(Individual.class);

        ArrayList list = new ArrayList<>(Arrays.asList(individual));
        Population population = new Population(list);
        population.addIndividual(individual2);
        assertEquals(population.getIndividual(1), individual2);
    }

    @Test
    public void checkSetBranchID() {
        Individual individual = mock(Individual.class);
        TypeValue mockedTypeValue = mock(TypeValue.class);

        when(individual.getMethod()).thenReturn(null);
        TypeValue[] typeValue = new TypeValue[]{mockedTypeValue};
        when(individual.getGenes()).thenReturn(typeValue);


        ArrayList list = new ArrayList<>(Arrays.asList(individual));
        Population population = new Population(list);

        Population p = population.setBranchID(1);
        Individual expected = new Individual(null, 1, typeValue);
        assertEquals(p.getIndividual(0), expected);
    }


    @Test
    public void checkGetFittest() {
        Individual individual = mock(Individual.class);
        Individual individual2 = mock(Individual.class);

        when(individual.getFitness()).thenReturn(100.00);
        when(individual2.getFitness()).thenReturn(1.00);


        ArrayList list = new ArrayList<>(Arrays.asList(individual, individual2));
        Population population = new Population(list);

        Individual i = population.getFittest();


        assertEquals(i, individual2);
    }

    @Test
    public void checkToString() {
        Individual individual = mock(Individual.class);
        Individual individual2 = mock(Individual.class);


        ArrayList list = new ArrayList<>(Arrays.asList(individual, individual2));
        Population population = new Population(list);

        String s = population.toString();

        assertEquals(s, individual.toString() + "\n" + individual2.toString() + "\n");
    }

    @Test
    public void checkConstructor() throws NoSuchMethodException, ClassNotFoundException {
        Method method = Class.forName("com.github.bradleyrumball.autologic.junit_test_classes.TrianglesJunit").getMethod("classify", int.class, int.class, int.class);
        Population population = new Population(1, method,1);

        assertThat(population, isA(Population.class));

    }





}