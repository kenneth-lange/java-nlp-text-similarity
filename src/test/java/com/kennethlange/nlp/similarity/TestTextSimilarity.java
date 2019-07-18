package com.kennethlange.nlp.similarity;

import junit.framework.TestCase;

import java.util.List;

public class TestTextSimilarity extends TestCase {

    public void testHappyPath() {
        TextSimilarity m = new TextSimilarity();
        m.addDocument("doc1", "My cat is yellow.");
        m.addDocument("doc2", "My cat is nice.");
        m.addDocument("doc3", "My dog is called kitty, kitty");
        m.calculate();

        List<String> related = m.getSimilarDocuments("doc1", 1);
        assertEquals(1, related.size());
        assertEquals("doc2", related.get(0));

        related = m.getSimilarDocuments("doc1", 2);
        assertEquals(2, related.size());
        assertEquals("doc2", related.get(0));
        assertEquals("doc3", related.get(1));

        related = m.getSimilarDocuments("doc1", 3); // Limit above number of documents that can be returned.
        assertEquals(2, related.size());
        assertEquals("doc2", related.get(0));
        assertEquals("doc3", related.get(1));
    }

    public void testNonExistingId() {
        try {
            TextSimilarity m = new TextSimilarity();
            m.addDocument("doc1", "My cat is yellow.");
            m.addDocument("doc2", "My cat is nice.");
            m.addDocument("doc3", "My dog is called kitty, kitty");
            m.calculate();

            List<String> related = m.getSimilarDocuments("nonExistingDoc");
        } catch(IllegalArgumentException e) {
            // Ignore. Should be thrown.
        }
    }
}
