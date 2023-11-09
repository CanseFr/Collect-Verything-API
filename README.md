# Collect-Verything-API

Lunch app need to create application.yaml in ressource folder 

```
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/*********?serverTimezone=UTC&createDatabaseIfNotExist=true
    driver-class-name: com.*********.****.***.Driver
    username: *********

  sql:
    init:
      platform: *****
      mode: always

  jpa:
    open-in-view: false
    hibernate:
      ddl-auto: create
    database: mysql
    database-platform: org.*****.***.***
    #    show-sql: true
    properties:
      hibernate:
        format_sql: true
    defer-datasource-initialization: true

springdoc:
  default-produces-media-type: application/json

server-io:
  password: ********* 
  username: *********
  host: *********
  port: *********
```