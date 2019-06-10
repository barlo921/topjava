<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="POST" action='mealsOptional' name="updateMeal">
    Meal ID : <input type="text" readonly="readonly" name="id"
                     value="<c:out value="${meal.id}" />"/> <br/>
    Date Time : <input
        type="text" name="dateTime"
        value="<javatime:format value="${meal.dateTime}" style="MS" />"/> <br/>
    Description : <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />"/> <br/>
    Calories : <input
        type="text" name="calories"
        value="<c:out value="${meal.calories}" />"/> <br/>
    <br/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
