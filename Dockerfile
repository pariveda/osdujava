FROM maven:3-openjdk-8
WORKDIR /
COPY pom.xml /pom.xml
COPY ./src /src
RUN mvn test