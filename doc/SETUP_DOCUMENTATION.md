# CI615 Jakarta EE Project Setup Documentation

**Project:** CI615 - Object Oriented Design and Architecture

---

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Maven Setup](#maven-setup)
3. [Tomcat Installation](#tomcat-installation)
4. [Project Configuration](#project-configuration)
5. [Web Application Structure](#web-application-structure)
6. [IntelliJ IDEA Run Configuration](#intellij-idea-run-configuration)
7. [Verification](#verification)
8. [Troubleshooting](#troubleshooting)

---

## Prerequisites

Before starting, ensure you have:
- IntelliJ IDEA installed (version 2024.1.3 or later)
- JDK installed (OpenJDK 25 used in this setup)
- Maven plugin enabled in IntelliJ IDEA

---

## Maven Setup

### Problem
Maven was installed as part of IntelliJ IDEA's bundled plugins but was not accessible from the command line (PowerShell).

### Solution

#### 1. Locate Maven Installation
Maven is bundled with IntelliJ IDEA at:
```
C:\Users\soboa\AppData\Roaming\JetBrains\IntelliJIdea2025.2\plugins\maven\lib\maven3\
```

The Maven executable files are located in:
```
C:\Users\soboa\AppData\Roaming\JetBrains\IntelliJIdea2025.2\plugins\maven\lib\maven3\bin\
```

#### 2. Add Maven to System PATH

**PowerShell Command Used:**
```powershell
$mavenBinPath = "C:\Users\soboa\AppData\Roaming\JetBrains\IntelliJIdea2025.2\plugins\maven\lib\maven3\bin"
$currentPath = [Environment]::GetEnvironmentVariable("Path", "User")
if ($currentPath -notlike "*$mavenBinPath*") {
    [Environment]::SetEnvironmentVariable("Path", "$currentPath;$mavenBinPath", "User")
    Write-Host "Maven bin directory added to User PATH successfully!"
} else {
    Write-Host "Maven bin directory is already in User PATH."
}
```

This command:
- Creates a variable with the Maven bin path
- Retrieves the current User PATH environment variable
- Checks if Maven path already exists
- Appends the Maven bin path to the User PATH if not present
- Saves the changes permanently to the system

#### 3. Set JAVA_HOME Environment Variable

Maven requires the `JAVA_HOME` environment variable to be set.

**PowerShell Command Used:**
```powershell
$javaPath = "C:\Users\soboa\.jdks\openjdk-25"
[Environment]::SetEnvironmentVariable("JAVA_HOME", $javaPath, "User")
$env:JAVA_HOME = $javaPath
Write-Host "JAVA_HOME set to: $javaPath"
```

This command:
- Sets `JAVA_HOME` to the JDK installation directory
- Saves it permanently to User environment variables
- Also sets it for the current PowerShell session

#### 4. Verify Maven Installation

**Command:**
```powershell
mvn -version
```

**Expected Output:**
```
Maven home: C:\Users\soboa\AppData\Roaming\JetBrains\IntelliJIdea2025.2\plugins\maven\lib\maven3
Java version: 25, vendor: Oracle Corporation, runtime: C:\Users\soboa\.jdks\openjdk-25
Default locale: en_GB, platform encoding: UTF-8
OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
```

---

## Tomcat Installation

### Download and Extract

#### 1. Download Apache Tomcat
- **Version:** Apache Tomcat 10.1.50
- **Source:** https://tomcat.apache.org/download-10.cgi
- **Variant:** Windows Service Installer or Core zip distribution

#### 2. Extract Tomcat

The Tomcat archive was extracted to the project root directory:
```
C:\Users\soboa\IdeaProjects\CI615\apache-tomcat-10.1.50\
```

**Directory Structure:**
```
CI615/
├── apache-tomcat-10.1.50/
│   ├── bin/           # Tomcat executables and startup scripts
│   ├── conf/          # Configuration files (server.xml, web.xml, etc.)
│   ├── lib/           # Tomcat library JARs
│   ├── logs/          # Log files
│   ├── temp/          # Temporary files
│   ├── webapps/       # Web application deployment directory
│   └── work/          # Working directory for compiled JSPs
├── src/
├── pom.xml
└── README.md
```

### Why Local Installation?

Installing Tomcat in the project directory offers several advantages:
- **Project Isolation:** Each project has its own Tomcat instance
- **Version Control:** Tomcat configuration can be versioned with the project
- **Portability:** The entire project (including server) can be moved or shared
- **No Global System Changes:** No need for system-wide Tomcat installation
- **Multiple Versions:** Different projects can use different Tomcat versions

---

## Project Configuration

### pom.xml Modifications

The `pom.xml` file was modified to support Jakarta EE web application development.

#### 1. Packaging Type
Ensure the project is packaged as a WAR (Web Application Archive):

```xml
<packaging>war</packaging>
```

#### 2. Jakarta EE Dependencies Added

**Complete Dependencies Section:**

```xml
<dependencies>
    <!-- Jakarta Servlet API - Provided by Tomcat -->
    <dependency>
        <groupId>jakarta.servlet</groupId>
        <artifactId>jakarta.servlet-api</artifactId>
        <version>6.0.0</version>
        <scope>provided</scope>
    </dependency>

    <!-- Jakarta Server Pages API - Provided by Tomcat -->
    <dependency>
        <groupId>jakarta.servlet.jsp</groupId>
        <artifactId>jakarta.servlet.jsp-api</artifactId>
        <version>3.1.1</version>
        <scope>provided</scope>
    </dependency>

    <!-- JSTL - JSP Standard Tag Library -->
    <dependency>
        <groupId>jakarta.servlet.jsp.jstl</groupId>
        <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
        <version>3.0.0</version>
    </dependency>

    <!-- JUnit 5 - Testing Framework -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>${junit.version}</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

#### 3. Dependency Scope Explanation

- **`provided`:** The dependency is required for compilation but will be provided by the runtime environment (Tomcat). These JARs are not included in the WAR file.
  - Used for: `jakarta.servlet-api`, `jakarta.servlet.jsp-api`
  
- **`compile` (default):** The dependency is included in the WAR file.
  - Used for: `jakarta.servlet.jsp.jstl-api`
  
- **`test`:** Only required for running tests, not included in the WAR.
  - Used for: `junit-jupiter`

#### 4. Why Jakarta EE 10 / Servlet 6.0?

Apache Tomcat 10.x implements:
- **Jakarta EE 10** (previously Java EE)
- **Servlet API 6.0**
- **JSP API 3.1**

**Important:** Tomcat 10+ uses `jakarta.*` namespace, not `javax.*` (used in older versions).

---

## Web Application Structure

### Directory Structure Created

```
src/
└── main/
    ├── java/              # Java source files (Servlets, Beans, etc.)
    ├── resources/         # Configuration files, properties
    └── webapp/            # Web application root
        ├── WEB-INF/       # Protected directory (not directly accessible)
        │   └── web.xml    # Deployment descriptor
        └── index.jsp      # Welcome page
```

### 1. web.xml (Deployment Descriptor)

**Location:** `src/main/webapp/WEB-INF/web.xml`

**Purpose:** 
- Configures the web application
- Defines servlets, filters, listeners
- Sets welcome files
- Configures security constraints

**File Content:**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee
         https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd"
         version="6.0">

    <display-name>CI615 Web Application</display-name>
    <description>CI615 Object Oriented Design and Architecture</description>

    <!-- Welcome File List -->
    <welcome-file-list>
        <welcome-file>index.jsp</welcome-file>
        <welcome-file>index.html</welcome-file>
    </welcome-file-list>

</web-app>
```

**Key Elements:**
- `xmlns="https://jakarta.ee/xml/ns/jakartaee"`: Jakarta EE 10 namespace
- `version="6.0"`: Servlet API version 6.0
- `<welcome-file-list>`: Defines default pages when accessing the root URL

### 2. index.jsp (Welcome Page)

**Location:** `src/main/webapp/index.jsp`

**Purpose:** 
- Entry point for the web application
- Demonstrates JSP functionality
- Displays server-side information

**File Content:**

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>CI615 - Welcome</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
        }
        .info {
            background-color: #e3f2fd;
            padding: 15px;
            border-left: 4px solid #2196F3;
            margin: 20px 0;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Welcome to CI615</h1>
        <h2>Object Oriented Design and Architecture</h2>
        
        <div class="info">
            <p><strong>Server Time:</strong> <%= new java.util.Date() %></p>
            <p><strong>Project Status:</strong> Successfully deployed!</p>
        </div>
        
        <p>This is your Jakarta EE web application scaffold for studying Object Oriented Design and Architecture.</p>
        
        <h3>Getting Started:</h3>
        <ul>
            <li>Create servlets in <code>src/main/java</code></li>
            <li>Create JSP pages in <code>src/main/webapp</code></li>
            <li>Configure web components in <code>WEB-INF/web.xml</code></li>
        </ul>
    </div>
</body>
</html>
```

**JSP Elements Used:**
- `<%@ page ... %>`: Page directive
- `<%= ... %>`: Expression tag (outputs Java expression)

---

## Building the Project

### Maven Build Command

**Command:**
```powershell
mvn clean package
```

**What it does:**
1. **clean:** Deletes the `target/` directory
2. **package:** Compiles code, runs tests, and packages into WAR file

**Build Output:**
```
[INFO] Building CI615 1.0-SNAPSHOT
[INFO] --------------------------------[ war ]---------------------------------
[INFO] 
[INFO] --- clean:3.2.0:clean (default-clean) @ CI615 ---
[INFO] Deleting C:\Users\soboa\IdeaProjects\CI615\target
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ CI615 ---
[INFO] Copying 0 resource from src\main\resources to target\classes
[INFO] 
[INFO] --- compiler:3.12.1:compile (default-compile) @ CI615 ---
[INFO] Compiling 1 source file with javac [debug release 25] to target\classes
[INFO] 
[INFO] --- war:3.4.0:war (default-war) @ CI615 ---
[INFO] Packaging webapp
[INFO] Assembling webapp [CI615] in [C:\Users\soboa\IdeaProjects\CI615\target\CI615-1.0-SNAPSHOT]
[INFO] Processing war project
[INFO] Copying webapp resources [C:\Users\soboa\IdeaProjects\CI615\src\main\webapp]
[INFO] Building war: C:\Users\soboa\IdeaProjects\CI615\target\CI615-1.0-SNAPSHOT.war
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

**Output File:**
```
target/CI615-1.0-SNAPSHOT.war (127 KB)
```

---

## IntelliJ IDEA Run Configuration

### Step-by-Step Configuration

#### 1. Open Run Configurations
- Navigate to: **Run → Edit Configurations...**
- Or click the dropdown next to the Run button → **Edit Configurations...**

#### 2. Add Tomcat Server

1. Click the **"+"** button (top-left)
2. Select **Tomcat Server → Local**
3. If Tomcat Server is not available:
   - Install the **Tomcat and TomEE Integration** plugin
   - Restart IntelliJ IDEA

#### 3. Configure Server Tab

**Name:** `Tomcat 10 - CI615`

**Application server:**
- Click **Configure...** next to the Application server field
- Click **"+"** to add a new server
- Browse to: `C:\Users\soboa\IdeaProjects\CI615\apache-tomcat-10.1.50`
- Click **OK**

**VM options:** (optional, for debugging or memory tuning)
```
-Xms256m -Xmx512m
```

**Browser:** (choose your preferred browser)
- Chrome
- Firefox
- Edge
- etc.

**HTTP port:** `8080` (default)

**JRE:** Select the JDK
- `OpenJDK 25` or the JDK you have installed

#### 4. Configure Deployment Tab

1. Click the **Deployment** tab
2. Click the **"+"** button
3. Select **Artifact...**
4. Choose: `CI615:war exploded` (for hot-swapping during development)
   - Alternatively: `CI615:war` (for production-like deployment)

**Application context:**
- Set to: `/CI615_war`
- This determines the URL path: `http://localhost:8080/CI615_war`

**Note:** The artifact name is based on your `pom.xml`:
```xml
<artifactId>CI615</artifactId>
<version>1.0-SNAPSHOT</version>
```

#### 5. Configure Startup/Connection Tab (Optional)

**Startup:** (default settings usually work)
- **Debug:** 8000 (JPDA port)
- **Run:** (no debugging)

**Before launch:**
- Build
- Build 'CI615:war exploded' artifact

#### 6. Save Configuration

Click **Apply** and then **OK**

---

## Complete Run Configuration Summary

| Setting | Value |
|---------|-------|
| **Name** | Tomcat 10 - CI615 |
| **Server** | Apache Tomcat 10.1.50 |
| **Server Location** | `C:\Users\soboa\IdeaProjects\CI615\apache-tomcat-10.1.50` |
| **HTTP Port** | 8080 |
| **JRE** | OpenJDK 25 |
| **Deployment Artifact** | CI615:war exploded |
| **Application Context** | /CI615_war |
| **URL** | http://localhost:8080/CI615_war |

---

## Verification

### 1. Build the Project

**In IntelliJ:**
- **Build → Build Project** (`Ctrl+F9`)

**Or via Maven:**
```powershell
mvn clean package
```

**Expected Result:**
- No compilation errors
- WAR file created in `target/` directory

### 2. Reload Maven Dependencies (if needed)

If IntelliJ shows "Dependency not found" errors:

1. Open Maven tool window: **View → Tool Windows → Maven**
2. Click the **Reload** button (⟳ circular arrow icon)
3. Wait for IntelliJ to download and index dependencies

### 3. Run the Application

1. Select the Tomcat run configuration from the dropdown
2. Click the **Run** button (▶) or press `Shift+F10`
3. Wait for Tomcat to start (watch the Run console)

**Expected Console Output:**
```
[2025-12-09 20:50:00,123] Artifact CI615:war exploded: Artifact is being deployed, please wait...
Connected to server
[2025-12-09 20:50:05,456] Artifact CI615:war exploded: Artifact is deployed successfully
[2025-12-09 20:50:05,457] Artifact CI615:war exploded: Deploy took 5,334 milliseconds
```

### 4. Access the Application

**URL:** http://localhost:8080/CI615_war

**Expected Result:**
- Browser opens automatically (if configured)
- Welcome page displays:
  - "Welcome to CI615"
  - "Object Oriented Design and Architecture"
  - Current server time
  - "Successfully deployed!" message

### 5. Verify Tomcat is Running

**Check Tomcat Console:**
```
INFO [main] org.apache.catalina.startup.Catalina.start Server startup in [5334] milliseconds
```

**Check Browser:**
- Page loads without errors
- Server time is displayed (confirms JSP is working)

---

## Troubleshooting

### Issue 1: Maven Not Found After Adding to PATH

**Solution:**
Restart PowerShell or refresh the PATH in current session:
```powershell
$env:Path = [System.Environment]::GetEnvironmentVariable("Path","Machine") + ";" + [System.Environment]::GetEnvironmentVariable("Path","User")
mvn -version
```

### Issue 2: JAVA_HOME Not Set Error

**Error Message:**
```
The JAVA_HOME environment variable is not defined correctly
```

**Solution:**
Set JAVA_HOME as shown in [Maven Setup](#3-set-java_home-environment-variable)

### Issue 3: Port 8080 Already in Use

**Error Message:**
```
Address already in use: bind
```

**Solutions:**
1. **Change Port:** In Run Configuration, change HTTP port to `8081` or another free port
2. **Stop Other Service:** Find and stop the service using port 8080:
   ```powershell
   netstat -ano | findstr :8080
   taskkill /PID <PID> /F
   ```

### Issue 4: 404 Error When Accessing Application

**Possible Causes:**
- Wrong URL (check application context)
- Deployment failed
- WAR file not built

**Solutions:**
1. Verify URL: `http://localhost:8080/CI615_war` (note the context path)
2. Check Run console for deployment errors
3. Rebuild: `mvn clean package`
4. Check Deployment tab in Run Configuration

### Issue 5: Changes Not Reflected (Hot Deploy Not Working)

**Solution:**
1. Use **"war exploded"** artifact (not plain "war")
2. In Run Configuration:
   - Server tab → **On 'Update' action:** Select "Update classes and resources"
   - Server tab → **On frame deactivation:** Select "Update classes and resources"
3. Use **"Update Application"** from the Run toolbar

### Issue 6: Jakarta Servlet Dependencies Showing as Errors in IntelliJ

**Error Message:**
```
Dependency 'jakarta.servlet:jakarta.servlet-api:6.0.0' not found
```

**Solution:**
1. Open Maven tool window
2. Click Reload button (⟳)
3. Wait for dependency download and indexing
4. If still showing errors, restart IntelliJ IDEA

### Issue 7: JSP Files Not Being Compiled

**Symptoms:**
- Blank page
- Source code visible in browser
- 500 errors

**Solution:**
Ensure Tomcat's `lib/` directory contains:
- `jasper.jar`
- `jasper-el.jar`
- `ecj-4.27.jar` (Eclipse Java Compiler)

These should be present in Tomcat 10.1.50 by default.

---

## Additional Resources

### Project Structure Reference

```
CI615/
├── apache-tomcat-10.1.50/        # Tomcat server (local installation)
│   ├── bin/
│   ├── conf/
│   ├── lib/
│   ├── logs/
│   ├── temp/
│   ├── webapps/
│   └── work/
├── src/
│   ├── main/
│   │   ├── java/                 # Java source files (Servlets, POJOs)
│   │   ├── resources/            # Configuration files
│   │   └── webapp/               # Web resources
│   │       ├── WEB-INF/
│   │       │   └── web.xml       # Deployment descriptor
│   │       ├── css/              # (create as needed)
│   │       ├── js/               # (create as needed)
│   │       ├── images/           # (create as needed)
│   │       └── index.jsp         # Welcome page
│   └── test/
│       └── java/                 # Test source files
├── target/                       # Build output (auto-generated)
│   └── CI615-1.0-SNAPSHOT.war    # Deployable WAR file
├── pom.xml                       # Maven configuration
└── README.md                     # Project documentation
```

### Maven Commands Reference

| Command | Purpose |
|---------|---------|
| `mvn clean` | Delete target directory |
| `mvn compile` | Compile source code |
| `mvn test` | Run unit tests |
| `mvn package` | Create WAR file |
| `mvn clean package` | Clean + compile + test + package |
| `mvn install` | Install WAR to local Maven repository |
| `mvn dependency:tree` | Show dependency hierarchy |

### Important Files Reference

| File | Purpose | Required? |
|------|---------|-----------|
| `pom.xml` | Maven project configuration | ✅ Yes |
| `web.xml` | Web application configuration | ✅ Yes (for Servlet 6.0) |
| `index.jsp` | Welcome page | Recommended |
| `.gitignore` | Git ignore rules | Recommended |

### Useful IntelliJ Shortcuts

| Shortcut | Action |
|----------|--------|
| `Shift+F10` | Run |
| `Shift+F9` | Debug |
| `Ctrl+F9` | Build Project |
| `Ctrl+Shift+F10` | Run context configuration |
| `Alt+5` | Debug tool window |

---

## Next Steps for Development

### 1. Create a Servlet

**Example:** `src/main/java/org/example/HelloServlet.java`

```java
package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "HelloServlet", urlPatterns = {"/hello"})
public class HelloServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Hello Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Hello from Servlet!</h1>");
            out.println("<p>Request URI: " + request.getRequestURI() + "</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
```

**Access at:** `http://localhost:8080/CI615_war/hello`

### 2. Create More JSP Pages

**Example:** `src/main/webapp/booking.jsp`

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Room Booking</title>
</head>
<body>
    <h1>Music Conservatory - Room Booking</h1>
    <form action="booking" method="post">
        <label for="room">Room:</label>
        <select id="room" name="room">
            <option value="practice1">Practice Room 1</option>
            <option value="practice2">Practice Room 2</option>
            <option value="studio">Recording Studio</option>
        </select>
        <br><br>
        <label for="date">Date:</label>
        <input type="date" id="date" name="date" required>
        <br><br>
        <button type="submit">Book Room</button>
    </form>
</body>
</html>
```

### 3. Add Database Support (Optional)

**Add to `pom.xml`:**
```xml
<!-- MySQL Connector -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.2.0</version>
</dependency>
```

### 4. Implement MVC Pattern

**Recommended Structure:**
```
src/main/java/org/example/
├── controller/        # Servlets (Controllers)
├── model/            # POJOs, Business Logic (Model)
├── dao/              # Data Access Objects
└── util/             # Utility classes
```

---

## Summary

This documentation covered:

✅ **Maven Setup:** Added IntelliJ's bundled Maven to system PATH  
✅ **JAVA_HOME:** Configured environment variable for Maven  
✅ **Tomcat Installation:** Extracted Tomcat 10.1.50 to project directory  
✅ **pom.xml Configuration:** Added Jakarta EE dependencies with correct scopes  
✅ **Web Application Structure:** Created `web.xml` and `index.jsp`  
✅ **IntelliJ Run Configuration:** Configured Tomcat server with deployment artifact  
✅ **Verification:** Successfully built and deployed application  

**Working URL:** http://localhost:8080/CI615_war

---

**Project:** CI615 Music Conservatory Booking System  
**Last Updated:** December 9, 2025

