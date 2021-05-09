# todo

Simple Application to manage list
of todo items, with given categories.

#### Running PostgresSql

mkdir ${HOME}/postgres-data/

docker run -d --name dev-postgres -e POSTGRES_PASSWORD=Pass2020! -v ${HOME}/postgres-data/:/var/lib/postgresql/data -p 5432:5432 postgres

#### Running application:
mvn spring-boot:start

#### Stopping application

mvn spring-boot:stop

#### Accessing API via Swagger-UI

http://localhost:8080/swagger-ui/index.html
