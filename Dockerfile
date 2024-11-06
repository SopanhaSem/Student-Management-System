FROM ghcr.io/graalvm/jdk-community:21
WORKDIR app
ADD ./build/libs/s-m-s-1.0.jar /app/
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "/app/s-m-s-1.0.jar"]
