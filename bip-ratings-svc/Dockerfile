FROM container-registry.dev.bip.va.gov/ironbank/openjdk11:1.11

# Add a volume pointing to /tmp
VOLUME /tmp

# The application's jar file
ARG JAR_FILE=target/bip-ratings-svc.jar

# Add the application's jar to the container
ADD ${JAR_FILE} bip-ratings-svc.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","./bip-ratings-svc.jar"]

ADD ./scripts/wait-for-it.sh /wait-for-it.sh