package com.kennethlange.nlp.similarity;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestTfIdf extends TestCase {

    public void testHappyPath() {
        TfIdf tfIdf = new TfIdf();
        tfIdf.addDocument("1", Arrays.asList("my","cat","is","yellow"));
        tfIdf.addDocument("2", Arrays.asList("my","cat","is","nice"));
        tfIdf.addDocument("3", Arrays.asList("my","dog","is","called","kitty", "kitty"));
        tfIdf.calculate();

        assertEquals(3, tfIdf.size());

        double[] vector = tfIdf.getVectorByDocumentId("1");
        assertEquals(6, vector.length);
        assertEquals(0.0,   vector[0], 0.01); // called
        assertEquals(-0.04, vector[1], 0.01); // cat
        assertEquals(0.0,   vector[2], 0.01); // dog
        assertEquals(0.0,   vector[3], 0.01); // kitty
        assertEquals(0.0,   vector[4], 0.01); // nice
        assertEquals(-0.12, vector[5], 0.01); // yellow

        vector = tfIdf.getVectorByDocumentId("2");
        assertEquals(6, vector.length);
        assertEquals(0.0,   vector[0], 0.01); // called
        assertEquals(-0.04, vector[1], 0.01); // cat
        assertEquals(0.0,   vector[2], 0.01); // dog
        assertEquals(0.0,   vector[3], 0.01); // kitty
        assertEquals(-0.12, vector[4], 0.01); // nice
        assertEquals(0.0,   vector[5], 0.01); // yellow

        vector = tfIdf.getVectorByDocumentId("3");
        assertEquals(6, vector.length);
        assertEquals(-0.08, vector[0], 0.01); // called
        assertEquals(0.0,   vector[1], 0.01); // cat
        assertEquals(-0.08, vector[2], 0.01); // dog
        assertEquals(-0.16, vector[3], 0.01); // kitty
        assertEquals(0.0,   vector[4], 0.01); // nice
        assertEquals(0.0,   vector[5], 0.01); // yellow
    }
}
