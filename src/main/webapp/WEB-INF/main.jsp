<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="text" />
<html>
<head>
    <title>Title</title>
</head>
<body>
<form>
    <label for="locale"></label><select id="locale" name="locale" onchange="submit()">
    <option value="en_EN" ${locale == 'en_EN' ? 'selected' : ''}>English</option>
    <option value="de_DE" ${locale == 'de_DE' ? 'selected' : ''}>Deutsch</option>
    <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>Русский</option>
</select>
</form>
<h2><fmt:message key="title.welcome" />, ${sessionScope.firstName}!</h2>
<form method="get" action="mainWindow">
    <button type="submit" name="button" value="viewAliens"><fmt:message key="button.view_aliens" /></button>
    <button type="submit" name="button" value="viewMovies"><fmt:message key="button.view_movies" /></button>
</form>
</body>
</html>
