FROM openjdk:12-jdk-alpine

COPY ./target/neuro-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch neuro-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","neuro-0.0.1-SNAPSHOT.jar"]
