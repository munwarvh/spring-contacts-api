FROM eclipse-temurin:11-jre
ADD target/contacts.jar contacts.jar
ENTRYPOINT ["java", "-jar","contacts.jar"]
EXPOSE 8081
