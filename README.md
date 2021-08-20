<h1 align="center">
    Spring Boot Complete App
</h1>

## 💻 About Project

<p align="center">🚀 This project aims to create an end-to-end application, 
from setup to monitoring and deploying</p>

<h4 align="center"> 
	🚧  Spring Complete App 🚀 In build...  🚧
</h4>

<p align="center">
 <a href="#-about-project">Goal</a> •
 <a href="#-features">Features</a> •
 <a href="#-how-run-project">Execute Project</a> •
 <a href="#technologies">Technologies</a> •
 <a href="#license">License</a> • 
 <a href="#author">Author</a>
</p>

## ⚙️ Features

- [x] CRUD task with all layers
- [x] User authentication with permissions

---

## 🚀 How run project

This project is divide in one part:
 
1. Backend (api server)

### Prerequirements

First of all it is necessary to run the compose docker that is at the root of the project.
Create the database with the name **taskdb**.

#### 🎲 Run App Server

```bash
# Clone this repository
$ git@github.com:andrereges/spring-completeapp.git

# Go to folder project
$ cd spring-completeapp

# Run only unit in test, dev or prod
# Only test env run integrations tests
$ mvn test -P dev

# Run docker-compose
$ docker-compose up -d
$ docker exec -ti postgres-v13 bash -c "psql -U postgres"
$ CREATE DATABASE taskdb; # if not exists
$ docker-compose up -d --force-recreate

# Push image to remote repository docker hub
$ docker login
$ mvn jib:build
```
---

### Project's URLs
<pre>
Prometheus:
http://localhost:9090/

Grafana:
http://localhost:3000/

App:
http://localhost:8080/

App Resources:
/api/v1/tasks
/actuator
/swagger-ui.html
/v3/api-docs
/h2-console
</pre>

---

## 🛠 Technologies

The following tools were used in the construction of the project:

#### **API**
- **[Spring Project](https://spring.io/projects)**
- **[OpenJDK 11](https://devdocs.io/openjdk~11/)**
- **[Junit 5](https://junit.org/junit5/docs/current/user-guide/)**
- **[Mapstruct](https://mapstruct.org/documentation/stable/reference/html/)**
- **[RestTemplate](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html)**
- **[Lombok](https://projectlombok.org/)**
- **[Maven](https://maven.apache.org/guides/)**

#### **Databases**
- **[Postgresql 13](https://www.postgresql.org/docs/13/index.html)**
- **[H2 Database](https://www.h2database.com/html/main.html)**

#### **API Documentation**
- **[Open Api 3](https://springdoc.org/)**

#### **Monitoring**
- **[Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)**
- **[Prometheus](https://prometheus.io/docs/prometheus/latest/getting_started/)**
- **[Grafana](https://grafana.com/docs/grafana/latest/?utm_source=grafana_gettingstarted)**

#### **Infrastructure**
- **[Docker](https://docs.docker.com/get-started/)**
- **[Docker Compose](https://docs.docker.com/compose/)**
- **[Docker Hub](https://hub.docker.com/)**
- **[Google Container Tools - Jib](https://github.com/GoogleContainerTools/jib)**

> See the file [pom.xml](https://github.com/andrereges/spring-completeapp/blob/main/pom.xml)


---

## 🦸 Autor

<a href="https://www.linkedin.com/in/andr%C3%AA-reges-38b746a9/">
 <img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/18141498?v=4" width="100px;" alt="Andrê Reges's Picture"/>
 <br />
 <sub><b>Andrê Reges</b></sub></a> <a href="https://www.linkedin.com/in/andr%C3%AA-reges-38b746a9/" title="Rocketseat">🚀</a>
 <br />

[![Linkedin Badge](https://img.shields.io/badge/-Andrê-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/andr%C3%AA-reges-38b746a9/)](https://www.linkedin.com/in/andr%C3%AA-reges-38b746a9/)
[![Gmail Badge](https://img.shields.io/badge/-andreriggs90@gmail.com-c14438?style=flat-square&logo=Gmail&logoColor=white&link=mailto:andreriggs90@gmail.com)](mailto:andreriggs90@gmail.com)

---

## 📝 License

This project is under license [MIT](./LICENSE).

Made with ❤️ by Andrê Reges 👋🏽 [Contact](https://www.linkedin.com/in/andr%C3%AA-reges-38b746a9/)

---