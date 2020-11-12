FROM openjdk:11-jre-slim
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY ./build/libs/* ./app.jar
EXPOSE 8080
ENTRYPOINT exec java -jar app.jar
