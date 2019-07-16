# Text Similarity in Java

The purpose of this project is to measure the similarity between text documents.

You can use it to create a **Related Posts** widget on a blog that shows other blog posts that are similar to the one that the user is currently viewing &mdash; something similar to the example below:

> Related Posts:
> * What are RESTful Web Services?
> * The SQL Developer's Guide to REST Services
> * How to Model Workflows in REST APIs
> * Avoid Data Corruption in Your REST API with ETags

The underlying implementation converts the documents to [Term Frequency Inverse Document Frequency](http://www.tfidf.com/) (tf-idf) vectors and measures the (cosine) distance between them. 

## Prerequisites

1. Maven 3
2. Java 8+
3. IntelliJ IDEA (optional)

## Code examples

### Example 1: The minimal example

The code below shows a minimal example with three short text snippets (as a document in this context can also be simple sentences): 

```java
TextSimilarity ts = new TextSimilarity();
ts.addDocument("doc1", "My cat is yellow");
ts.addDocument("doc2", "My cat is nice.");
ts.addDocument("doc3", "My dog is called Charlie");
ts.calculate();

List<String> similarDocs = ts.getSimilarDocuments("doc1");
for(String s : similarDocs) {
    System.out.println(s);
}
```

The code's output is shown below: 

```text
doc2
doc3
```

In the output, **doc2** is shown before **doc3**, because **doc2** is more similar to **doc1**.

Note that the `addDocument` method has two parameters. The first is a unique identifier of the document. This can be a primary key from a database or an URL. The only requirement is that it's unique across all the documents.


### Example 2: Related posts on a blog

You can combine `TextSimilarity` with [Jsoup](https://jsoup.org/) to find related blog posts.

For example, if I want to see what posts on my blog are similar to [7 Tips for Designing a Better REST API](https://www.kennethlange.com/7-tips-for-designing-a-better-rest-api/), I can use the code snippet below:   

```java
TextSimilarity ts = new TextSimilarity();

Document feed = Jsoup.connect("http://www.kennethlange.com/feed/").get();
for(Element linkElement : feed.select("link")) {
    Document blogPost = Jsoup.connect(linkElement.text()).get();
    ts.addDocument(linkElement.text(), blogPost.select("body").text());
}
ts.calculate();

List<String> similarDocs = ts.getSimilarDocuments("https://www.kennethlange.com/7-tips-for-designing-a-better-rest-api/");
for(String s : similarDocs) {
    System.out.println(s);
}
```

And the output is listed below (and accidentally all the posts are related to REST Services):

```
https://www.kennethlange.com/what-are-restful-web-services/
https://www.kennethlange.com/the-sql-developers-guide-to-rest-services/
https://www.kennethlange.com/how-to-model-workflows-in-rest-apis/
https://www.kennethlange.com/avoid-data-corruption-in-your-rest-api-with-etags/
https://www.kennethlange.com/dont-limit-your-rest-api-to-crud-operations/
```

## Advanced usage

## Implement your own tokenizer

The project includes a tokenizer (`TokenizerImpl`) that is used for splitting text documents into tokens.

If you want to implement your own tokenizer (to add support for stemming, non-English languages, or something else) you can make your own custom implementation of the `Tokenizer` interface &mdash; as shown below (in practice, you wouldn't use an anonymous class, but you get the idea):

```java
TextSimilarity ts = new TextSimilarity(new Tokenizer() {
    public List<String> tokenize(String document) {
        // TODO: Implement tokenization...
    }
});
```

## Use your own stop words

You can provide your own set of stop words, such a set of non-English words, to the existing tokenizer if necessary:

```java
TextSimilarity ts = new TextSimilarity(
    new TokenizerImpl(new HashSet<>(Arrays.asList("dem", "de", "deres")))
);
```

## License

This project is licensed under the MIT License &mdash; see the [LICENSE](LICENSE) file for details.
