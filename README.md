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

- postgresml

```postgresql

docker run \
-it \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=postgres1234 \
-v postgresml_data:/var/lib/postgresql \
-p 5432:5432 \
-p 8000:8000 \
ghcr.io/postgresml/postgresml:2.9.3 \
bash -c "sudo -u postgresml psql -d postgresml"


CREATE EXTENSION IF NOT EXISTS pgml;
SELECT pgml.version();
```
