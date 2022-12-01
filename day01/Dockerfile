FROM gradle:alpine AS base
WORKDIR app/src
COPY ./ .
RUN gradle build

FROM openjdk:17-alpine
COPY --from=BASE app/src/build/libs/app.jar /app.jar
COPY input.txt .
CMD java -jar /app.jar