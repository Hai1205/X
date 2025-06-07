# X Client

X Client is a modern social networking web application built with React, Redux Toolkit, React Query, and Tailwind CSS. It provides features similar to popular social platforms, including posting, liking, following, bookmarking, notifications, and user profile management.

## Features

- User authentication and authorization (JWT)
- User registration, login, and profile management
- Post creation, editing, deletion, and retrieval
- Like, comment, and bookmark posts
- Follow and unfollow users
- Real-time notifications
- File storage integration (e.g., AWS S3)
- Pagination and search support

## Technologies Used

- Java 17+
- Spring Boot
- Spring Security
- MongoDB
- Maven

## Project Structure

- src/ main/ java/com/Server/ controller/
- REST API controllers dto/
- Data Transfer Objects entity/
- Entity models exception/
- Custom exceptions repo/
- Repository interfaces security/
- Security configs & filters service/
- Service layer utils/
- Utilities resources/ application.properties templates/ test/ java/com/Server/
- Unit & integration tests

## Getting Started

1. **Clone the repository:**

```sh
git clone https://github.com/Hai1205/X.git
cd X_Server
```

2. **Set up the environment:**

- Set up a MongoDB database and configure the connection in src/main/resources/application.properties.
- Set up AWS S3 and configure the connection in src/main/resources/application.properties.

3. **Build the application:**

```sh
./mvnw spring-boot:run
```

API is available at: http://localhost:8080