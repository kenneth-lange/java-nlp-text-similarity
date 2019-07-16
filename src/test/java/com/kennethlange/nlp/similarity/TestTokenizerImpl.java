package com.kennethlange.nlp.similarity;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Collections;

public class TestTokenizerImpl extends TestCase {
    public void testHappyPath() {
        Tokenizer t = new TokenizerImpl(Collections.EMPTY_SET);

        assertEquals(Arrays.asList("my","cat","is","yellow"), t.tokenize("My cat is yellow."));
        assertEquals(Arrays.asList("my","cat","is","nice"), t.tokenize("My cat is nice."));
        assertEquals(Arrays.asList("my","dog","is","called","kitty", "kitty"), t.tokenize("My dog is called kitty, kitty"));
    }

    public void testStopWords() {
        Tokenizer t = new TokenizerImpl();

        assertEquals(Arrays.asList("cat","yellow"), t.tokenize("My cat is yellow."));
        assertEquals(Arrays.asList("cat","nice"), t.tokenize("My cat is nice."));
        assertEquals(Arrays.asList("dog","called","kitty", "kitty"), t.tokenize("My dog is called kitty, kitty"));
    }
}
