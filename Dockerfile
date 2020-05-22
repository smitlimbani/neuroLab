# FROM node:12.16.2-alpine AS build
# WORKDIR /usr/src/app
# COPY package.json ./
# RUN npm install
# COPY . .
# RUN npm run build


# FROM nginx:1.17.1-alpine
# COPY --from=build /usr/src/app/dist/neuroLab /usr/share/nginx/html
# FROM nginx:latest

# WORKDIR /usr/local/nginx/html
FROM openjdk:8-jdk-alpine

COPY ./target/neuro-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch demo-docker-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","neuro-0.0.1-SNAPSHOT.jar"]
