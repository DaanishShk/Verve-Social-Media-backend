# Verve Social-Media (Backend)

Backend for a full-stack social media application, built with Spring Boot and PostgreSQL. Pairs with the [frontend repo](https://github.com/DaanishShk/Verve-Social-Media-frontend).

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![PostgreSQL](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white) ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white) ![Nginx](https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white) ![Azure](https://img.shields.io/badge/azure-%23007CC6.svg?style=for-the-badge&logo=microsoftazure&logoColor=white)

## Description

The Verve backend exposes the REST API that powers the frontend: authentication, posts (text/image/video), friends and followers, comments, reactions, user tagging, achievements, and notifications. Key capabilities include:

- **Auth & accounts:** user registration, login, and profile management.
- **Social graph:** friend requests, follow/unfollow, and the relationships that drive each user's feed.
- **Content:** CRUD for posts across text, image, and video, including tagging and media attachments.
- **Engagement:** likes/dislikes, comments, and notification events for achievements, mentions, and reactions.
- **Real-time updates:** WebSocket-based notifications so the frontend reflects activity without polling.

The service is containerized with Docker and fronted by Nginx, and was deployed first on AWS, then migrated to Azure with GitHub Actions for CI/CD.

## Built with

1. [Spring Boot](https://spring.io/projects/spring-boot) - Java backend framework
2. [PostgreSQL](https://www.postgresql.org/) - relational database
3. [Docker](https://www.docker.com/) - containerization platform
4. [Nginx](https://www.nginx.com/) - reverse proxy / web server

## Getting Started

For building and running the application you need:

- [Java 18](https://www.java.com/en/) or higher
- [Maven](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/)
- [PostgreSQL](https://www.postgresql.org/)

### Running the application locally

This repo is designed to run alongside the [frontend repo](https://github.com/DaanishShk/Verve-Social-Media-frontend) via Docker Compose.

1. Clone this repo and the [frontend repo](https://github.com/DaanishShk/Verve-Social-Media-frontend).
2. Build the frontend and copy its build output plus the UI Dockerfile into a `frontend_build` directory in this repo's root (see the frontend README for exact steps).
3. Build the backend jar:
   ```
   mvn compile
   ```
4. From this repo's root, start the full stack:
   ```
   docker-compose up
   ```
5. Once both containers are up, the app is accessible at `localhost:80`.

## Author

Daanish Shaikh - [@github](https://github.com/DaanishShk)
repo link - [Verve-Social-Media-backend](https://github.com/DaanishShk/Verve-Social-Media-backend)

## License

This project is licensed under the MIT License.
