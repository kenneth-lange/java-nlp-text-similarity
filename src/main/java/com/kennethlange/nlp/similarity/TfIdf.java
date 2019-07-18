package com.kennethlange.nlp.similarity;

import java.util.*;

/**
 * Package private class that implements Term Frequency - Inverse Document Frequency (TfIdf).
 * See http://www.tfidf.com/ for more details.
 */
final class TfIdf {
    private final Map<String, List<String>> documents = new HashMap<>();
    private final Map<String, double[]> vectors = new HashMap<>();

    public void addDocument(String documentId, List<String> terms) {
        documents.put(documentId, terms);
    }

    public void calculate() {
        Idf idf = new Idf();
        for(List<String> terms : documents.values()) {
            idf.addDocument(terms);
        }
        idf.calculateWeights();

        List<String> defaultDimensions = idf.getAllTerms();
        for(Map.Entry<String, List<String>> document : documents.entrySet()) {
            Tf tf = new Tf();
            tf.addTerms(document.getValue());

            double[] vector = new double[defaultDimensions.size()];

            for(int i = 0; i < defaultDimensions.size(); i++) {
                String term = defaultDimensions.get(i);
                vector[i] = tf.getFrequency(term) * idf.getWeight(term);
            }
            vectors.put(document.getKey(), vector);
        }
    }

    public int size() {
        return documents.size();
    }

    public double[] getVectorByDocumentId(String documentId) {
        return vectors.get(documentId);
    }

    public Map<String, double[]> getVectors() {
        return vectors;
    }
}
