FROM tomcat:9-jdk8-openjdk
RUN rm -rf /usr/local/tomcat/webapps/*
COPY ./target/avaji-logiweb.war /usr/local/tomcat/webapps/ROOT.war
CMD ["catalina.sh","run"]
