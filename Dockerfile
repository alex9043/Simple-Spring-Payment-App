FROM openjdk:21-ea-oracle
WORKDIR /project
COPY ./build/*.jar ./app.jar
ENTRYPOINT ["java","-jar", "app.jar"]
