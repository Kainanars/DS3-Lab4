FROM openjdk

WORKDIR /app

COPY target/*.jar /app/market-spring.jar

ENTRYPOINT ["java","-jar","market-spring.jar"]

