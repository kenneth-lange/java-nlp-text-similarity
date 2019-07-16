package com.kennethlange.nlp.similarity;

import java.util.List;

/**
 * A tokenizer that can split a string into token. It can be used in
 * {@link com.kennethlange.nlp.similarity.TextSimilarity}.
 */
public interface Tokenizer {

    /**
     * Splits a text into tokens.
     * @param document The text that should be split into tokens.
     * @return         A list where each element is a token.
     */
    List<String> tokenize(String document);
}
