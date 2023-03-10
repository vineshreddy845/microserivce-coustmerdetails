FROM openjdk:17
ADD target/microservice.coustmerdetails-0.0.1-SNAPSHOT.jar microservice.coustmerdetails.jar
ENTRYPOINT ["java","-jar","/microservice.coustmerdetails.jar"]

