package com.kennethlange.nlp.similarity;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;

public class TestIdf extends TestCase {

    public void testHappyPath() {
        Idf idf = new Idf();
        idf.addDocument(Arrays.asList("my","cat","is","yellow"));
        idf.addDocument(Arrays.asList("my","cat","is","nice"));
        idf.addDocument(Arrays.asList("my","dog","is","called","kitty", "kitty"));
        idf.calculateWeights();

        assertEquals(0, idf.getWeight("is"), 0);
        assertEquals(-0.176, idf.getWeight("cat"), 0.001);
        assertEquals(-0.477, idf.getWeight("yellow"), 0.001);
        assertEquals(-0.477, idf.getWeight("kitty"), 0.001);

        List<String> allTerms = idf.getAllTerms();
        assertEquals(6, allTerms.size());
        assertEquals("called", allTerms.get(0));
        assertEquals("cat",    allTerms.get(1));
        assertEquals("dog",    allTerms.get(2));
        assertEquals("kitty",  allTerms.get(3));
        assertEquals("nice",   allTerms.get(4));
        assertEquals("yellow", allTerms.get(5));
    }
}
