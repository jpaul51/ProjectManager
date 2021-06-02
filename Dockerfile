FROM debian:buster

RUN apt -y  update
RUN apt -y install curl
RUN curl -sL https://deb.nodesource.com/setup_12.x | bash -
#ADD https://deb.nodesource.com/setup_12.x setup.sh
#RUN bash setup.sh
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
RUN chown jopa:jopa /usr/src/app/target/
# Running stage: the part that is used for running the application
RUN ls -al target
#COPY target/projectmanager-1.2.0.jar  /usr/app/app.jar
RUN cp /usr/src/app/target/projectmanager-1.2.0.jar  /usr/src/app/app.jar
RUN chown jopa:jopa /usr/src/app/app.jar
USER jopa
EXPOSE 8080
CMD java -jar app.jar
