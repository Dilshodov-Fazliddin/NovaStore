FROM openjdk:19
EXPOSE 8080
ARG JAR_FILE=build/libs/NovaStore-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} nova
ENTRYPOINT ["java","-jar","nova"]