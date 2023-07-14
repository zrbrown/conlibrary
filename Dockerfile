FROM maven:3.9.2-eclipse-temurin-20-alpine AS build-artifacts
ARG BUILDSRC=/buildsrc
COPY ./ ${BUILDSRC}
WORKDIR ${BUILDSRC}
RUN mvn package

FROM eclipse-temurin:20-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG DEPENDENCY=/buildsrc/target/dependency
COPY --from=build-artifacts ${DEPENDENCY}/BOOT-INF/classes /app
COPY --from=build-artifacts ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build-artifacts ${DEPENDENCY}/META-INF /app/META-INF

ENTRYPOINT ["java","--enable-preview", "-Dspring.config.name=application,localdocker", "-cp","app:app/lib/*", "com.geekway.conlibrary.ConlibraryApplication"]