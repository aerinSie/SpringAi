# ENV
```
docker compose up
```
# RUN

- run Unitest
```JAVA
    @Test
    void chat() throws IOException {
        var result = searchService.ragSearch("什麼是spring ai?");
        System.out.println(result);
    }
```
