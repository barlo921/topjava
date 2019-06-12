<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Meals Optional</title>
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
        <th colspan=2>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="meal" items="${mealList}">
        <tr bgcolor="${meal.excess ? 'red' : 'green'}">
            <td><javatime:format value="${meal.dateTime}" pattern="dd-MM-yyyy HH:mm"/></td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>${meal.excess}</td>
            <td><a href="meals?action=edit&mealId=<c:out value="${meal.id}"/>">Update</a></td>
            <td><a href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<h4><a href="meals?action=add">Add meal</a></h4>
</body>
</html>
