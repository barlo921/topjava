<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="POST" action='meals' name="updateMeal">
    <input type="hidden" readonly="readonly" name="id"
           value="${meal.id}"/>
    Date Time : <input
        type="text" name="dateTime"
        placeholder="01-01-2000 10:00"
        value="<javatime:format value="${meal.dateTime}" pattern="dd-MM-yyyy HH:mm" />"/> <br/>
    Description : <input
        type="text" name="description"
        placeholder="Breakfast"
        value="<c:out value="${meal.description}" />"/> <br/>
    Calories : <input
        type="text" name="calories"
        placeholder="1000"
        value="<c:out value="${meal.calories}" />"/> <br/>
    <br/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
