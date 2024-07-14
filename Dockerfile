# Dockerfile

# jdk17 Image Start
FROM openjdk:17

ARG JAR_FILE=build/libs/iccas_question-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} iccas_question.jar
ENTRYPOINT ["java","-jar","-Duser.timezone=Asia/Seoul","iccas_question.jar"]