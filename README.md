# Chat App Backend

Backend của hệ thống Chat App được xây dựng bằng Spring Boot và MySQL.

## Công nghệ sử dụng

- Java 21
- Spring Boot 3
- Spring Security
- JWT Authentication
- Spring Data JPA
- MySQL
- WebSocket
- STOMP
- MapStruct
- Lombok
- Maven

## Tính năng

- Xác thực người dùng bằng JWT
- Đăng ký tài khoản
- Đăng nhập
- Quản lý người dùng
- Quản lý cuộc trò chuyện
- Chat cá nhân
- Chat nhóm
- Upload avatar
- Realtime Messaging
- RESTful API
- WebSocket API

## Yêu cầu hệ thống

- Java 21
- Maven 3.9+
- MySQL 8+

## Cài đặt

Clone source code:

```bash
git clone https://github.com/your-username/chat-app-backend.git
cd chat-app-backend
```

## Tạo database

```sql
CREATE DATABASE chatapp;
```

## Cấu hình application.yml

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/chatapp
    username: root
    password: root

  jpa:
    hibernate:
      ddl-auto: update
```

## Chạy dự án

```bash
mvn spring-boot:run
```

hoặc

```bash
./mvnw spring-boot:run
```

Backend sẽ chạy tại:

```txt
http://localhost:8080
```

## API Documentation

### Authentication

```txt
POST /api/auth/register
POST /api/auth/login
```

### Users

```txt
GET    /api/users
GET    /api/users/{id}
POST   /api/users
PUT    /api/users/{id}
DELETE /api/users/{id}
```

### Conversations

```txt
GET    /api/conversations
POST   /api/conversations
GET    /api/conversations/{id}
```

### Messages

```txt
GET    /api/messages
POST   /api/messages
```

## WebSocket Endpoint

```txt
/ws
```

Subscribe:

```txt
/topic/conversation/{conversationId}
```

Send:

```txt
/app/chat.send
```

## Cấu trúc thư mục

```txt
src/main/java
├── config
├── controller
├── dto
├── entity
├── exception
├── mapper
├── repository
├── security
├── service
└── websocket
```

## Kiến trúc

```txt
Controller
    ↓
Service
    ↓
Repository
    ↓
MySQL
```
