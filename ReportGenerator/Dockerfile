FROM maven:3.9.0-amazoncorretto-17

ENV HOME=/home/usr/app

RUN mkdir -p $HOME

WORKDIR $HOME

# 1. add pom.xml only here

ADD pom.xml $HOME

# 2. start downloading dependencies

RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never"]

# 3. add all source code and start compiling

ADD . $HOME

RUN ["mvn", "clean", "package", "-X"]

EXPOSE 8089

ENTRYPOINT ["mvn", "spring-boot:run", "-Dspring-boot.run.arguments=--env=prod"]