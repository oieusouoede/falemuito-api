FROM eclipse-temurin:11
ADD target/falemuito-0.0.1-SNAPSHOT.jar falemuito-0.0.1-SNAPSHOT.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "falemuito-0.0.1-SNAPSHOT.jar"]