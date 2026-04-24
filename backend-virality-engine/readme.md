# 🚀 Backend Engineering Assignment: Core API & Guardrails

## 📌 Overview

This project is a high-performance Spring Boot microservice acting as a central API gateway with strict guardrails to prevent uncontrolled bot activity. It uses Redis for distributed state management and PostgreSQL as the source of truth.

---

## 🛠️ Tech Stack

* Java 17
* Spring Boot 3.x
* PostgreSQL
* Redis (Spring Data Redis)
* Docker & Docker Compose

---

## ⚙️ Setup Instructions

### 1. Clone Repository

```bash
git clone <https://github.com/JJSinghRathore/Sample>
cd project-folder
```

### 2. Start Services (Postgres + Redis)

```bash
docker-compose up -d
```

### 3. Run Application

```bash
./mvnw spring-boot:run
```

---

## 📊 Database Schema

### Entities:

* **User** → id, username, is_premium
* **Bot** → id, name, persona_description
* **Post** → id, author_id, content, created_at
* **Comment** → id, post_id, author_id, content, depth_level, created_at

---

## 🔗 API Endpoints

* `POST /api/posts` → Create Post
* `POST /api/posts/{postId}/comments` → Add Comment
* `POST /api/posts/{postId}/like` → Like Post

---

## ⚡ Redis Virality Engine

Real-time scoring using Redis:

* Bot Reply → +1
* Human Like → +20
* Human Comment → +50

Key Format:

```
post:{id}:virality_score
```

---

## 🔐 Atomic Locks (Thread Safety)

### 1. Horizontal Cap

* Max 100 bot replies per post
* Redis Key:

```
post:{id}:bot_count
```

* Implemented using atomic `INCR`

### 2. Vertical Cap

* Max depth level = 20
* Validated before DB insert

### 3. Cooldown Cap

* Prevent bot-human spam (10 mins)
* Redis Key:

```
cooldown:bot_{id}:human_{id}
```

* Implemented using `SETEX`

---

## 🔔 Notification Engine

### Redis Throttler

* If user notified in last 15 min → store in Redis list
* Else → send notification immediately

Key:

```
user:{id}:pending_notifs
```

---

### ⏰ CRON Job

* Runs every 5 minutes
* Aggregates notifications
* Logs:

```
Summarized Push Notification: Bot X and N others interacted
```

---

## 🧠 Thread Safety Strategy

* Used Redis atomic operations (`INCR`, `SETNX`, `EXPIRE`)
* No in-memory state (stateless architecture)
* Database writes only occur after Redis validation
* Prevented race conditions in high concurrency (200 requests test)

---

## 🧪 Edge Case Handling

✔ Race condition handled using Redis atomic counters
✔ Strict stateless backend (no HashMaps/static vars)
✔ Redis acts as gatekeeper, DB as source of truth

---

## 📁 Deliverables

* Spring Boot Source Code
* docker-compose.yml
* Postman Collection
* README Documentation

---

# 📬 Postman Collection Details

The Postman collection includes the following APIs:

1. Create Post
   POST /api/posts

2. Add Comment
   POST /api/posts/{postId}/comments

3. Like Post
   POST /api/posts/{postId}/like

4. Bot Comment (with guardrails testing)

---

## 🧪 Testing Scenarios Included

* Normal post creation
* Human interactions (likes/comments)
* Bot interaction limits
* Cooldown validation
* Stress testing (concurrent requests)

---

👉 To use:

* Import the JSON file into Postman
* Update base URL if needed
* Run requests

