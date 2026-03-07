# 🏢 Employee Management System (EMS)

A **role-based Employee Management System** built with **React.js, TailwindCSS, DaisyUI, and Spring Boot**.  
This project demonstrates a full-stack application with **Admin, HR, and Employee dashboards**, secure authentication, document management, and theme toggling.

[![React](https://img.shields.io/badge/Frontend-React-blue)](https://reactjs.org/) 
[![Spring Boot](https://img.shields.io/badge/Backend-SpringBoot-brightgreen)](https://spring.io/projects/spring-boot) 
[![TailwindCSS](https://img.shields.io/badge/CSS-Tailwind-blueviolet)](https://tailwindcss.com/)
[![DaisyUI](https://img.shields.io/badge/UI-DaisyUI-purple)](https://daisyui.com/)

---

## 📸 Screenshots

**Login Page**  
![Login Page](https://github.com/kartikade27/employee-management-system/blob/b0474c65178dd12b69e8c745cfa33692b654ac05/Screenshot%202026-03-07%20134327.png)

**Admin Dashboard**  
![Admin Dashboard](https://github.com/kartikade27/employee-management-system/blob/b0474c65178dd12b69e8c745cfa33692b654ac05/Screenshot%202026-03-07%20133856.png)

**HR Dashboard**  
![HR Dashboard](https://github.com/kartikade27/employee-management-system/blob/b0474c65178dd12b69e8c745cfa33692b654ac05/Screenshot%202026-03-07%20133806.png)

**Employee Dashboard**  
![Employee Dashboard](https://github.com/kartikade27/employee-management-system/blob/b0474c65178dd12b69e8c745cfa33692b654ac05/Screenshot%202026-03-07%20134007.png)

---

## ⚡ Features

- **Role-based authentication** (Admin, HR, Employee)  
- **Responsive UI** with TailwindCSS & DaisyUI  
- **Theme toggler** with multiple themes: 💼 Corporate, 🌿 Emerald, 🌙 Dark, 🧊 Winter, ☕ Coffee, Night  
- **Document management** for employees  
- **Attendance, Leave, and Salary management**  
- **Swagger API documentation** for backend endpoints  
- **Fully responsive** for desktop and mobile  

---

## 🛠 Tech Stack

| Layer          | Technology |
|----------------|------------|
| Frontend       | React.js, TailwindCSS, DaisyUI, React Router, React Icons |
| Backend        | Spring Boot, Spring Security, JPA/Hibernate |
| Database       | MySQL / H2 (configurable) |
| Authentication | JWT / Session-based |
| Documentation  | Swagger (OpenAPI) |

---

## 🔗 Backend APIs

| Module      | Description                                      | Endpoint           |
|------------|-------------------------------------------------|------------------|
| Auth       | Login, logout, forgot/reset password           | `/api/auth`       |
| Employee   | CRUD operations, role assignment               | `/api/employees`  |
| Department | CRUD operations for departments                | `/api/departments`|
| Attendance | Record and view employee attendance            | `/api/attendance` |
| Leave      | Apply, approve, reject leaves                  | `/api/leave`      |
| Document   | Upload, download, delete documents             | `/api/documents`  |
| Salary     | Manage employee salaries                        | `/api/salary`     |

---

## 🌐 Swagger API Documentation

Explore all backend APIs here:  
[🔗 Swagger UI](http://localhost:8080/swagger-ui/index.html)

---

## 💻 Installation

### 1️⃣ Clone Repository
```bash
git clone https://github.com/kartikade27/employee-management-system.git
cd employee-management-system
./mvnw spring-boot:run
