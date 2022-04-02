FROM maven:3.6.0-jdk-11-slim
WORKDIR /app
COPY . .
EXPOSE 8080
RUN mvn clean package
CMD ["mvn", "exec:java"]