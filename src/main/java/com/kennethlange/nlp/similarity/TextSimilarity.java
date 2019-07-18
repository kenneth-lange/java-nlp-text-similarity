package com.kennethlange.nlp.similarity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Find similar documents in a collection of documents.
 */
@SuppressWarnings("WeakerAccess")
public final class TextSimilarity {
    private Tokenizer tokenizer;
    private TfIdf tfIdf = new TfIdf();
    private Map<String, double[]> vectors = null;

    /**
     * Constructs a new instance using {@link com.kennethlange.nlp.similarity.TokenizerImpl} for splitting text into tokens.
     */
    public TextSimilarity() {
        this(new TokenizerImpl());
    }

    /**
     * Constructs a new instance with an user-provided tokenizer.
     * @param tokenizer The tokenizer that should be used.
     */
    public TextSimilarity(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    /**
     * Adds a new document to the class.
     * @param id   A unique identifier of the document. Typically, a primary key from a database, or an URL.
     * @param text The actual text of the document.
     */
    @SuppressWarnings("WeakerAccess")
    public void addDocument(String id, String text) {
        if(vectors == null) {
            tfIdf.addDocument(id, tokenizer.tokenize(text));
        } else {
            throw new IllegalStateException("You cannot add new documents after calling calculate.");
        }
    }

    /**
     * Calculates the similarity of all the documents added so far. After this method has been called, you can no
     * longer add new documents.
     */
    @SuppressWarnings("WeakerAccess")
    public void calculate() {
        tfIdf.calculate();
        this.vectors = tfIdf.getVectors();

        // Enable the GC to collect these objects (as tfIdf can be big)
        tokenizer = null;
        tfIdf = null;
    }

    /**
     * Gets the document that are similar to document identified by the id.
     * @param id    The id of the document that you wish to find similar documents to.
     * @param limit The maximum number of similar documents you want returned. If limit is above the number of documents
     *              that has been added, then all documents are returned.
     * @return      The most similar document. They are ordered by similarity, so the most similar is first in the
     *              returned list.
     */
    @SuppressWarnings("WeakerAccess")
    public List<String> getSimilarDocuments(String id, int limit) {
        double[] myVector = vectors.get(id);
        if(myVector == null) {
            throw new IllegalArgumentException("No document with the id given as argument exists.");
        }

        Map<String, Double> similarity = new HashMap<>();

        for(Map.Entry<String, double[]> vector : vectors.entrySet()) {
            if(!vector.getKey().equals(id)) {
                similarity.put(vector.getKey(), cosineSimilarity(myVector, vector.getValue()));
            }
        }

        List<String> bestMatches = new ArrayList<>();
        for(int i = 0; i < limit && !similarity.isEmpty(); i++) {

            String bestMatchId = "";
            double bestMatch = 0.0;

            for(Map.Entry<String, Double> s : similarity.entrySet()) {
                if(s.getValue() >= bestMatch) {
                    bestMatchId = s.getKey();
                    bestMatch = s.getValue();
                }
            }

            bestMatches.add(bestMatchId);
            similarity.remove(bestMatchId);
        }

        return bestMatches;
    }

    /**
     * Same as the other getSimilarDocument, except that it returns, at most, 5 documents.
     */
    @SuppressWarnings("WeakerAccess")
    public List<String> getSimilarDocuments(String id) {
        return getSimilarDocuments(id, 5);
    }

    private double cosineSimilarity(double[] v1, double[] v2) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < v1.length; i++) {
            dotProduct += v1[i] * v2[i];
            normA += Math.pow(v1[i], 2);
            normB += Math.pow(v2[i], 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
