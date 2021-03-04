# Stage that builds the application, a prerequisite for the running stage
FROM openjdk:15-jdk-oraclelinux8

ARG MAVEN_VERSION=3.6.3
RUN curl -sL https://deb.nodesource.com/setup_12.x | bash -
RUN pacman -Syu && pacman -S --no-install-recommends nodejs

# Stop running as root at this point
RUN useradd -m jopa
WORKDIR /usr/src/app/
RUN chown jopa:jopa /usr/src/app/
USER jopa

# Copy pom.xml and prefetch dependencies so a repeated build can continue from the next step with existing dependencies
COPY --chown=jopa pom.xml ./
RUN mvn dependency:go-offline -Pproduction

# Copy all needed project files to a folder
COPY --chown=jopa:jopa src src
COPY --chown=jopa:jopa frontend frontend
COPY --chown=jopa:jopa package.json pnpm-lock.yaml webpack.config.js ./


# Build the production package, assuming that we validated the version before so no need for running tests again
RUN mvn clean package -DskipTests -Pproduction

# Running stage: the part that is used for running the application
FROM openjdk:15
COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar
RUN useradd -m jopa
USER jopa
EXPOSE 8080
CMD java -jar /usr/app/app.jar
