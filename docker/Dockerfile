FROM azul/zulu-openjdk-alpine:15
VOLUME /tmp
ARG JAR_FILE
ARG VERSION
COPY ${JAR_FILE} /service.jar
RUN echo ${VERSION} >> service.version
RUN apk add curl
HEALTHCHECK --interval=3s --timeout=1s --start-period=2s CMD curl -f http://localhost:8081/actuator/health || exit 1
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Dservice.version=$(cat /service.version) -Djava.security.egd=file:/dev/./urandom -jar /service.jar" ]