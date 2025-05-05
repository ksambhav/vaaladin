FROM eclipse-temurin:24-jdk-noble AS builder
WORKDIR /myapp
COPY webapp/target/*.jar app.jar
RUN java -Djarmode=tools -jar app.jar extract


FROM eclipse-temurin:24-jre-noble
WORKDIR /myapp
COPY --from=builder /myapp/app/app.jar ./
COPY --from=builder /myapp/app/lib ./lib
ENTRYPOINT ["java", "-jar", "app.jar"]