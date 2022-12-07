FROM gradle as BASE
WORKDIR /src
COPY ./ .
RUN gradle clean build

FROM openjdk
COPY input.txt .
COPY --from=BASE /src/build/libs/ .
CMD java -jar demo.jar