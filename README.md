# CI 615 Project

## Goals and Purpose

To mimic a system used to manage bookings at a music conservatory.

## Quick Start

Once the Tomcat server is running, the webapp can be accessed at: http://localhost:8080/CI615_war/

## Documentation

For detailed setup instructions including:
- Maven installation and PATH configuration
- Tomcat installation and setup
- Project configuration (pom.xml, web.xml, JSP)
- IntelliJ IDEA run configuration
- Troubleshooting guide

Please see: **[DOCUMENTATION.md](doc/DOCUMENTATION.md)**

## Technology Stack

- **Java:** OpenJDK 25
- **Build Tool:** Maven 3
- **Server:** Apache Tomcat 10.1.50
- **Framework:** Jakarta EE 10 (Servlet 6.0, JSP 3.1)
- **IDE:** IntelliJ IDEA 2024.1.3+

## Project Structure

```
CI615/
├── apache-tomcat-10.1.50/    # Local Tomcat server
├── src/
│   └── main/
│       ├── java/             # Servlets and Java classes
│       ├── resources/        # Configuration files
│       └── webapp/           # JSP files and web resources
│           └── WEB-INF/      # web.xml and protected resources
├── pom.xml                   # Maven configuration
└── README.md                 # This file
```
