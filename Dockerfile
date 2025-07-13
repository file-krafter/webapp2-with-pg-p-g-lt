# ---------- Stage 1: Build WAR using Maven ----------
FROM maven:3.9.4-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy Maven project files
COPY pom.xml .
COPY src ./src

# Build WAR file
RUN mvn clean package -DskipTests

# ---------- Stage 2: Deploy to Tomcat ----------
FROM tomcat:10.1.24-jdk21-temurin

# Remove default Tomcat apps
RUN rm -rf /usr/local/tomcat/webapps/app2

# Copy built WAR file from previous stage
COPY --from=builder /app/target/*.war /usr/local/tomcat/webapps/app2.war

# Expose port 8080 (already default for Tomcat)
EXPOSE 8080

# Default CMD is already set by tomcat image
