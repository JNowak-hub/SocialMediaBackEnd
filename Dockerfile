FROM maven:3.6.1-jdk-13-alpine as build
ADD . /server
WORKDIR /server
RUN mvn install
RUN mv /server/target/demo-*.jar /server/demo.jar
FROM gcr.io/distroless/java:11
COPY --from=build /server/demo.jar /server/demo.jar
WORKDIR /server
EXPOSE 8080
CMD ["/server/demo.jar"]