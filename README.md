# ENV
- OLLAMA
```
ollama pull qwen2:0.5b
    
ollama pull mofanke/dmeta-embedding-zh:latest
```
- PGVECTOR
```cmd
docker-compose up pgvector
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
