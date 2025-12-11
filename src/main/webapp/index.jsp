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

        <p>This is the Jakarta EE web application for Object Oriented Design and Architecture.</p>

        <h3>Getting Started:</h3>
        <ul>
            <li>Create servlets in <code>src/main/java</code></li>
            <li>Create JSP pages in <code>src/main/webapp</code></li>
            <li>Configure web components in <code>WEB-INF/web.xml</code></li>
        </ul>

        <h3>Room Booking System:</h3>
        <ul>
            <li><a href="${pageContext.request.contextPath}/booking">Book a Room</a></li>
            <li><a href="${pageContext.request.contextPath}/booking?action=list">View All Bookings</a></li>
        </ul>
    </div>
</body>
</html>

