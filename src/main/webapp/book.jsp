<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book a Room</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        form { max-width: 400px; }
        label { display: block; margin-top: 10px; font-weight: bold; }
        input, select { width: 100%; padding: 8px; margin-top: 5px; }
        button { margin-top: 20px; padding: 10px 20px; background: #4CAF50;
            color: white; border: none; border-radius: 5px; cursor: pointer; }
        button:hover { background: #45a049; }
        a { color: #4CAF50; }
        .checkbox-label { display: inline; margin-left: 5px; }
    </style>
</head>
<body>
<h1>Book a Room</h1>
<form action="${pageContext.request.contextPath}/booking" method="post">
    <input type="hidden" name="action" value="create">

    <label for="roomID">Select Room:</label>
    <select name="roomID" id="roomID" required>
        <c:forEach var="room" items="${rooms}">
            <option value="${room.roomID}">
                    ${room.roomType} Room (ID: ${room.roomID}, Capacity: ${room.capacity})
            </option>
        </c:forEach>
    </select>

    <label for="studentID">Student ID:</label>
    <input type="number" name="studentID" id="studentID" required>

    <label for="date">Date:</label>
    <input type="date" name="date" id="date" required>

    <label for="startTime">Start Time:</label>
    <input type="time" name="startTime" id="startTime" required>

    <label for="endTime">End Time:</label>
    <input type="time" name="endTime" id="endTime" required>

    <label for="nrOfParticipants">Number of Participants:</label>
    <input type="number" name="nrOfParticipants" id="nrOfParticipants" min="1" required>

    <label for="isRecurrent">
        <input type="checkbox" name="isRecurrent" id="isRecurrent" value="true">
        <span class="checkbox-label">Recurring (book next week too)</span>
    </label>

    <button type="submit">Book Room</button>
</form>
<p><a href="index.jsp">Back to Home</a> | <a href="booking?action=list">View All Bookings</a></p>
</body>
</html>