FROM openjdk:18
WORKDIR /app
COPY . /app
RUN ./mvnw clean install -DskipTests
ENTRYPOINT ./mvnw spring-boot:run