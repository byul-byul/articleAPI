# articleAPI
article API application

Prerequisites:
	- docker;
	- postgres db named 'articledb'
	
Overview:
1. CRUD: (localhost:8080/api/v1)
	1.1 GET "/user/articles/all": receive all articles;
	1.2 GET "/user/articles/{int}": recive articles by page (default_value:1);
	1.3 GET "/admin/articles/statistics": show published articles on daily bases (default_days_count:7);
	1.4 POST "/user/articles": add an article (mandatory fields cannot be blank);
	1.5 PUT "/admin/articles/{int}": update article by id;
	1.6 DELETE "/admin/articles/{int}": delete article by id;

2. Infrastructure:
	2.1 Builder: maven;
	2.2 IDE: Intellij;
	2.3 DB service: postgres (docker);
	2.4 Framework: Spring Boot 3.0.0;

3. Notes:
	3.1 https enabled and uses self-signed-sertificate
	3.2 Troubles with initial execute ./resources/data.sql => need to execute manually;
	3.3 Troubles with Annotation @Size(min=1) => CONSTRAINT at db side (data.sql);

