# 📚 Course Registration System

## 📌 Overview
The **Course Registration System** is a Java-based web application that allows users to register, log in, and enroll in courses, while providing administrators with full control over course and user management.  
This project implements **JWT authentication**, **role-based access control**, and **secure password storage** with **BCrypt**.

---

## 🚀 Features

### 🔑 Authentication & Authorization
- **JWT-based authentication** for secure API access.
- **Role-based access control**:
  - **User**: Can register, log in, view available courses, and enroll.
  - **Admin**: Full CRUD operations on courses and users, view registered users.

### 🛠 CRUD Operations
- **Course Management (Admin only)**:
  - Create, Read, Update, Delete courses.
- **User Management (Admin only)**:
  - View and manage registered users.
- **Course Registration (User)**:
  - View course list and register for selected courses.

### 🔒 Security
- **BCrypt password hashing** for secure password storage in the database.
- **JWT tokens** for session-less authentication.

---

## 🗄 Database
- Stores **user details**, **course details**, and **course registration records**.
- Passwords are **never stored in plain text** — all are hashed using BCrypt.

---

## ⚙️ Tech Stack
- **Backend**: Java (Spring Boot / Jakarta EE)
- **Authentication**: JWT (JSON Web Token)
- **Password Security**: BCrypt
- **Database**: MySQL / PostgreSQL (configurable)
- **Build Tool**: Maven / Gradle

---

## 📂 Installation & Setup

### 1️⃣ Clone the repository
```bash
git clone https://github.com/your-username/your-repo-name.git
cd your-repo-name
