FROM openjdk
WORKDIR /app
COPY target/scrapper.jar scrapper.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/scrapper.jar"]
