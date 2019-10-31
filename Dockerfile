FROM openjdk:12
ADD target/campaign.jar campaign.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "campaign.jar"]