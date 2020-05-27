FROM tomcat:jdk8-openjdk

# website files
COPY target/verademo.war /usr/local/tomcat/webapps
COPY setenv.sh /usr/local/tomcat/bin

# run Tomcat
CMD ["catalina.sh","run"]
