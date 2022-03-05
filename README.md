# shelterApp

This app I've created using:
- Spring boot
- Spring security
- Spring data
- Spring data REST
- HATEOS
- AWS service
- Html, CSS, JavaScript
- MySql|

Default security login and password is both "user".

To change default location to save images go to com.ziola.shelter.configs then edit StorageProperties file.
To change security options go to com.ziola.shelter.configs then edit WebSecurityConfig file.
To change email options (to make auto sending emails or question possible) go to com.ziola.shelter then edit ShelterApplication file and 
resources/application.properties file.
To change database connection option go to resources/ then edit application.properties file.
You can enable spring.jpa.hibernate.ddl-auto in application.properties.

Application is designed to manage shelter - you can add workers with limited permissions and admin with full permissions. You can add animals, details of them, images which are stored on Amazon AWS. Some users may delete these images (with admin role). 
