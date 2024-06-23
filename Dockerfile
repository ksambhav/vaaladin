#FROM eclipse-temurin:21-jdk AS builder
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} application.jar
#RUN java -Djarmode=layertools -jar application.jar extract
#
#
#FROM eclipse-temurin:21-jdk
#COPY --from=builder dependencies/ ./
#COPY --from=builder snapshot-dependencies/ ./
#COPY --from=builder spring-boot-loader/ ./
#COPY --from=builder application/ ./
#ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]

# Layered jar not working for a Vaadin application

FROM eclipse-temurin:22-jre-jammy
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar","application.jar"]