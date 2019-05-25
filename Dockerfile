FROM hypriot/rpi-java

ADD target/eightballapi-0.0.1-SNAPSHOT.jar /opt/eightballapi-0.0.1-SNAPSHOT.jar

EXPOSE 7000

ENTRYPOINT ["java", "-jar", "/opt/eightballapi-0.0.1-SNAPSHOT.jar"]
