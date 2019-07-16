package com.kennethlange.nlp.similarity;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Package private class that implements Inverse Document Frequency (IDF).
 * See http://www.tfidf.com/ for more details.
 */
final class Idf {
    private Map<String, Double> weights = new HashMap<>();
    private List<List<String>> documents = new ArrayList<>();

    public void addDocument(List<String> document) {
        documents.add(document);
    }

    public void calculateWeights() {
        for(List<String> document : documents) {
            List<String> uniqueTerms = document.stream().distinct().collect(Collectors.toList()); // Remove duplicates
            for(String term : uniqueTerms) {
                double weight = weights.getOrDefault(term, 0.0);
                weight++;
                weights.put(term, weight);
            }
        }

        for (Map.Entry<String, Double> entry : weights.entrySet()) {
            weights.put(entry.getKey(), Math.log10(entry.getValue() / documents.size()));
        }
    }

    public double getWeight(String key) {
        return weights.getOrDefault(key, 0.0);
    }

    public List<String> getAllTerms() {
        List<String> significantTerms = new ArrayList();

        for(Map.Entry<String, Double> weight : weights.entrySet()) {
            if(weight.getValue() != 0.0) {
                significantTerms.add(weight.getKey());
            }
        }

        Collections.sort(significantTerms);

        return significantTerms;
    }
}
