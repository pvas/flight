FROM java:latest
COPY ./target/flight-1.0.0-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
ENTRYPOINT ["java", "-jar", "flight-1.0.0-SNAPSHOT.jar"]
