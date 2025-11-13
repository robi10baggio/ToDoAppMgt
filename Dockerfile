#
# Build stage
#


FROM amazoncorretto:21 AS build
COPY ./ /home/app
RUN cd /home/app && ./gradlew build

FROM amazoncorretto:21-alpine
COPY --from=build /home/app/build/libs/TodoMgtApp-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dfile.encoding=UTF-8","/app.jar"]