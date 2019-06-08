<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MealServlet</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border="1px">
    <thead>
    <tr>
        <td><b>Date</b></td>
        <td><b>Description</b></td>
        <td><b>Calories</b></td>
        <td><b>Excess</b></td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="meal" items="${mealList}">
        <c:choose>
            <c:when test="${meal.isExcess()}">
                <c:set var="color" value="red"/>
            </c:when>
            <c:otherwise>
                <c:set var="color" value="green"/>
            </c:otherwise>
        </c:choose>
        <tr bgcolor="${color}">
            <td><javatime:format value="${meal.getDateTime()}" style="MS"/></td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>
            <td>${meal.isExcess()}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
