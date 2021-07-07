FROM azul/zulu-openjdk-alpine:15
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} /service.jar
RUN apk add curl
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /service.jar" ]