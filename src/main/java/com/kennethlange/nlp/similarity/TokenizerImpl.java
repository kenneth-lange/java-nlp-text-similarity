package com.kennethlange.nlp.similarity;

import java.util.*;

/**
 * The standard implementation of {@link com.kennethlange.nlp.similarity.Tokenizer}.
 */
public final class TokenizerImpl implements Tokenizer {
    private final Set<String> stopWords;

    /**
     * Constructs a new tokenizer using a set of user-provided stop words.
     * @param stopWords A set of stop words that should be removed from the text.
     */
    public TokenizerImpl(Set<String> stopWords) {
        this.stopWords = stopWords;
    }

    /**
     * Constructs a new tokenizer using the list of English stop words from:
     * https://www.textfixer.com/tutorials/common-english-words.txt
     */
    public TokenizerImpl() {
        this(new HashSet<>(Arrays.asList("a","able","about","across","after","all","almost","also","am","among","an","and","any","are","as","at","be","because","been","but","by","can","cannot","could","dear","did","do","does","either","else","ever","every","for","from","get","got","had","has","have","he","her","hers","him","his","how","however","i","if","in","into","is","it","its","just","least","let","like","likely","may","me","might","most","must","my","neither","no","nor","not","of","off","often","on","only","or","other","our","own","rather","said","say","says","she","should","since","so","some","than","that","the","their","them","then","there","these","they","this","tis","to","too","twas","us","wants","was","we","were","what","when","where","which","while","who","whom","why","will","with","would","yet","you","your")));
    }

    @Override
    public List<String> tokenize(String document) {
        List<String> tokens = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(document, " -/.,;:()'!?\"\t\n\r\f");
        while (st.hasMoreTokens()) {
            String token = st.nextToken().toLowerCase();
            if(!stopWords.contains(token) && isAlphanumeric(token) && token.length() >= 2) {
                tokens.add(token);
            }
        }
        return tokens;
    }

    private boolean isAlphanumeric(String str) {
        char[] charArray = str.toCharArray();
        for(char c : charArray) {
            if (!Character.isLetterOrDigit(c) && c != '-')
                return false;
        }
        return true;
    }
}
