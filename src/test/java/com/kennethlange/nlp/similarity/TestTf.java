package com.kennethlange.nlp.similarity;

import junit.framework.TestCase;

import java.util.Arrays;

public class TestTf extends TestCase {

    public void testHappyPath() {
        Tf tf = new Tf();
        tf.addTerms(Arrays.asList("my","cat","is","called","cat"));

        assertEquals(0.4, tf.getFrequency("cat"), 0.01);
        assertEquals(0.2, tf.getFrequency("my"), 0.01);
    }
}
