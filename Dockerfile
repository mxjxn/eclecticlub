FROM openjdk:8-alpine

COPY target/uberjar/eclecticlub.jar /eclecticlub/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/eclecticlub/app.jar"]
