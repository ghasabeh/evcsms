FROM openjdk:17
CMD ["mkdir", "/root/config"]
COPY src/main/resources/*.yml /root/config/
COPY target/evcsms.jar /root/
WORKDIR /root/
ENTRYPOINT ["java", "-jar", "/root/evcsms.jar", "--spring.config.location=file:///root/config/"]