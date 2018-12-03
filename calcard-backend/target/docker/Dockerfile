#########################
### build environment ###
#########################

# base image
FROM java:8

# set working directory
RUN mkdir -p /opt/app
ENV PROJECT_HOME /opt/app
WORKDIR $PROJECT_HOME

# add app
COPY target/calcard-backend-0.1.0.jar $PROJECT_HOME/app.jar

# expose port 
EXPOSE 8080

# run server
CMD ["java", "-jar", "-Dspring.profiles.active=prod" ,"./app.jar"]