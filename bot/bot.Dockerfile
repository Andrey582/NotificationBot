FROM openjdk
WORKDIR /app
COPY target/bot.jar bot.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "/bot.jar"]
