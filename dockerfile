FROM adoptopenjdk/openjdk8-openj9
ARG JAR_FILE=target/gateway.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]