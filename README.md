This is READ.me file of PayMyBuddy application.


- It was developed with Java version 1.8.


- The safe command in a terminal to run application is :

mvn spring-boot:run "-Dspring-boot.run.arguments='--spring.datasource.username=%username%' '--spring.datasource.password=%password%'"

by replacing %username% and %password% by your credentials.


- The application.properties file contains at least :

#Global config
spring.application.name=PayMyBuddy

#Database config
spring.datasource.url=jdbc:mysql://localhost:3306/paymybuddy?serverTimezone=UTC
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
spring.datasource.username=root
spring.datasource.password=root
#JPA has features for DDL generation and these can be set up to run on startup against the database
spring.jpa.hibernate.ddl-auto=none
#init of schema.sql database MySql v8 and populates it with data.sql
spring.sql.init.mode=always
# to use hibernate AND datasource init scripts
spring.jpa.defer-datasource-initialization=false


It will create a database with tables and populate it with data, from schema.sql and data.sql in resources folder.


Modèle Physique de Données
![mpd](https://github.com/jnoelOC/PayMyBuddy/blob/develop/images/Chambe_Jean-Noel_5_mpd_012022.png)

Diagramme UML
![UML diagram](https://github.com/jnoelOC/PayMyBuddy/blob/develop/images/Chambe_Jean-Noel_6_diagrammeUML_012022.png)

Script schema.sql
![schema.sql](https://github.com/jnoelOC/PayMyBuddy/tree/develop/src/main/resources/schema.sql)

Script data.sql
![data.sql](https://github.com/jnoelOC/PayMyBuddy/tree/develop/src/main/resources/data.sql)
