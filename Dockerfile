FROM openjdk:21-ea-oracle
WORKDIR /project
COPY ./target/Simple-Spring-Payment-App-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar","Simple-Spring-Payment-App-0.0.1-SNAPSHOT.jar"]
