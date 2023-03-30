This repo demonstrates a bug in Spring Boot 3 when using `@EntityGraph` with `@ElementCollection/@CollectionTable`.

The `main` branch uses Spring Boot version `3.0.5`, while `v2.7.10` uses Spring Boot version `2.7.10`.

Run the app using:

    mvn spring-boot:run


When accessing the endpoint:

    http://localhost:8080/user/1/roles

On `3.0.5`, the response is:

> ["ROLE1"]

On `2.7.10`, the response is:

> ["ROLE1","ROLE2"]