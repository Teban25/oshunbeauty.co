FROM amazoncorretto:11-alpine-jdk
COPY target/oshunbeauty-0.1.0 oshunbeauty-0.1.0
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","/oshunbeauty-0.1.0.jar"]