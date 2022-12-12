# articleAPI
article API application

see task.txt for task details

Prerequisites:
	- docker;
	- postgres db named 'articledb' with all grants for user 'postgres' with password 'postgres';
	- manually executed provided data.sql after starting application (for initial db populating);

Overview:
1. CRUD (localhost:8080/api/v1) (USER: byulbyul/password, USER&ADMIN: admin/password):
	1.1 GET "/articles": receive first page;
	1.2 GET "/articles/all": receive all articles without paging (additional point);
	1.3 GET "/articles/{int}": recive articles by page;
	1.4 GET "/statistics" (Admins only): show published articles on daily bases (default_days_count:7);
	1.5 POST /articles": add an article (mandatory fields cannot be blank);
	1.6 PUT "/articles/{int}": update article by id (additional point);
	1.7 DELETE "/articles/{int}": delete article by id (additional point);

2. Infrastructure:
	2.1 Builder: maven;
	2.2 IDE: Intellij;
	2.3 DB service: postgres (docker);
	2.4 Framework: Spring Boot 3.0.0;

3. To improve:
	3.1 Complete Tests;
	3.2 Automate data.sql executing;
	3.3 Configure volume for saving db data from docker;
	3.4 Adding new users (separate db table for registred users);
	3.5 Consider using annotation @Size(min=1) instead 'CONSTRAINT' at db side (data.sql).
