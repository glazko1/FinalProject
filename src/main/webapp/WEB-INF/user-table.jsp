<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="text" />
<html>
<head>
    <title>All users</title>
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>
<form>
    <label for="locale"></label><select id="locale" name="locale" onchange="submit()">
    <option value="en_EN" ${locale == 'en_EN' ? 'selected' : ''}>English</option>
    <option value="de_DE" ${locale == 'de_DE' ? 'selected' : ''}>Deutsch</option>
    <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>Русский</option>
</select>
</form>
<form method="get" action="mainWindow">
    <table border="1" cellpadding="4" cellspacing="0">
        <tr>
            <th align="center">ID</th>
            <th align="center">Username</th>
            <th align="center">Email</th>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td align="center">${user.userId}</td>
                <td align="center">
                    <button type="submit" name="button" value="viewUser${user.userId}">${user.username}</button>
                </td>
                <td align="center">${user.email}</td>
            </tr>
        </c:forEach>
    </table>
</form>
</body>
</html>
