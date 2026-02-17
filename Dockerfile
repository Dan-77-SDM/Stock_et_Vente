# Base image Tomcat 10 avec JDK 17
FROM tomcat:10.1-jdk17

# Supprimer les applications par défaut de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copier le WAR généré par Maven dans Tomcat
COPY target/*.war /usr/local/tomcat/webapps/ROOT.war

# Exposer le port Tomcat
EXPOSE 8080

# Lancer Tomcat
CMD ["catalina.sh", "run"]

