FROM debian
#ARG MAVEN_VERSION=3.6.3
RUN apt-get -y update && apt-get -y upgrade
RUN apt -y install curl
RUN curl -sL https://deb.nodesource.com/setup_12.x | bash -
RUN apt -y install nodejs

# Stop running as root at this point
RUN useradd -m jopa
WORKDIR /usr/src/app/
RUN chown jopa:jopa /usr/src/app/
USER jopa

# Copy pom.xml and prefetch dependencies so a repeated build can continue from the next step with existing dependencies
FROM maven:3.6.3
RUN useradd -m jopa
WORKDIR /usr/src/app/
RUN chown jopa:jopa /usr/src/app/
USER jopa

COPY --chown=jopa pom.xml ./
RUN mvn dependency:go-offline -Pproduction

# Copy all needed project files to a folder
COPY --chown=jopa:jopa src src
COPY --chown=jopa:jopa frontend frontend
COPY --chown=jopa:jopa package.json  webpack.config.js ./


# Build the production package, assuming that we validated the version before so no need for running tests again
RUN mvn clean package -DskipTests -Pproduction

# Running stage: the part that is used for running the application
RUN ls target
COPY target/projectmanager-1.2.0.jar  /usr/app/app.jar
USER jopa
EXPOSE 8080
CMD java -jar /usr/app/app.jar