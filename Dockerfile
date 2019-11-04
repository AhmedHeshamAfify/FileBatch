FROM openjdk:8-jdk-alpine

EXPOSE 8085

# Add a volume pointing to /tmp
VOLUME /tmp

# The application's jar file
ARG JAR_FILE=target/FileBatch-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} FileBatch-0.0.1-SNAPSHOT.jar

# Run the jar file 
ENTRYPOINT ["java","-jar","FileBatch-0.0.1-SNAPSHOT.jar"]

