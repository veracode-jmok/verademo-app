FROM tomcat:jdk8-openjdk

# website files
COPY target/verademo.war /usr/local/tomcat/webapps

# required?
EXPOSE 8080   
CMD ["catalina.sh","run"]
