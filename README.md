# campanha
Build war with maven 

Steps:

1. Download a fresh [Tomcat 8 distribution](https://tomcat.apache.org/download-80.cgi)
2. Clone the repository to your local machine
3. Run mvn package
4. Copy the generated `campanha.war` to the Tomcat `webapps` folder
5. Start Tomcat by running `bin\startup.bat` (or `bin\startup.sh` for Linux)
6. Tomcat will automatically deploy the war
7. Start MongoDB
8. Run mvn clean test

## Technologies

- Spring MVC
- Spring Data
- Design pattern : Repository
- Mongo DB
- JUnit + RestAssured Testeeee
