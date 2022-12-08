# articleAPI
article API application
Overview:

1. CRUD:
	1.1 GET "/all": receive all articles;
	1.2 GET "{int}": recive articles by page (default:first_page);
	1.3 GET "/statistics": show published articles on daily bases (default:7days);
	1.4 POST "": add an article (mandatory fields cannot be blank);
	1.5 PUT "{int}": update article by id;
	1.6 DELETE "{int}": delete article by id;

2. Infrastructure:
	2.1 Builder: maven;
	2.2 IDE: Intellij;
	2.3 DB service: postgres (docker);
	2.4 Framework: Spring Boot;

3. Notes:
	3.1 https enabled and uses self-signed-sertificate
	3.2 Troubles with initial execute ./resources/data.sql => need to execute manually;
	3.3 Troubles with Annotation @Size(min=1) => CONSTRAINT at db side (data.sql);

