FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/stevechan.jar /stevechan/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/stevechan/app.jar"]
