<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>View Bookings</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 40px; }
    table { border-collapse: collapse; width: 100%; margin-top: 20px; }
    th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
    th { background-color: #4CAF50; color: white; }
    tr:nth-child(even) { background-color: #f2f2f2; }
    button { padding: 5px 10px; background: #f44336; color: white;
      border: none; border-radius: 3px; cursor: pointer; }
    button:hover { background: #da190b; }
    .success { color: green; font-weight: bold; }
    a { color: #4CAF50; }
  </style>
</head>
<body>
<h1>All Bookings</h1>

<c:if test="${param.success == 'booked'}">
  <p class="success">Booking created successfully!</p>
</c:if>
<c:if test="${param.success == 'cancelled'}">
  <p class="success">Booking cancelled successfully!</p>
</c:if>

<c:choose>
  <c:when test="${empty bookings}">
    <p>No bookings found.</p>
  </c:when>
  <c:otherwise>
    <table>
      <thead>
      <tr>
        <th>Booking ID</th>
        <th>Room</th>
        <th>Student ID</th>
        <th>Date</th>
        <th>Time</th>
        <th>Participants</th>
        <th>Recurring</th>
        <th>Action</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="booking" items="${bookings}">
        <tr>
          <td>${booking.bookingID}</td>
          <td>
            <c:forEach var="room" items="${rooms}">
              <c:if test="${room.roomID == booking.roomID}">
                ${room.roomType} Room ${room.roomID}
              </c:if>
            </c:forEach>
          </td>
          <td>${booking.studentID}</td>
          <td>${booking.date}</td>
          <td>${booking.startTime} - ${booking.endTime}</td>
          <td>${booking.nrOfParticipants}</td>
          <td>${booking.recurrent ? 'Yes' : 'No'}</td>
          <td>
            <form action="booking"
                  method="post" style="display: inline;">
              <input type="hidden" name="action" value="cancel">
              <input type="hidden" name="bookingID" value="${booking.bookingID}">
              <button type="submit"
                      onclick="return confirm('Cancel this booking?')">
                Cancel
              </button>
            </form>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </c:otherwise>
</c:choose>

<p>
  <a href="index.jsp">Back to Home</a> |
  <a href="booking">Book a Room</a>
</p>
</body>
</html>
