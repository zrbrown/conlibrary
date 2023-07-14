# Convention Library
An application to run a board game event's library.

## Try it out
To quickly test the Convention Library to see if it suits your needs, just clone this project and run
```shell
docker network create geeknet
docker run --rm -e POSTGRES_USER=geekway -e POSTGRES_PASSWORD=geekway -e POSTGRES_DB=conlibrary --network geeknet --name geekway postgres:15.3
docker build -t geekway/conlibrary:0.1 .
docker run --rm --network geeknet -p 8080:8080 --name conlibrary geekway/conlibrary:0.1
```