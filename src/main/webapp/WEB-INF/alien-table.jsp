<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="text" />
<html>
<head>
    <title></title>
</head>
<body>
<form>
    <label for="locale"></label><select id="locale" name="locale" onchange="submit()">
    <option value="en_EN" ${locale == 'en_EN' ? 'selected' : ''}>English</option>
    <option value="de_DE" ${locale == 'de_DE' ? 'selected' : ''}>Deutsch</option>
    <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>Русский</option>
</select>
</form>
<table border="1" cellpadding="4" cellspacing="0">
    <tr>
        <c:if test="${sessionScope.status == 1}">
            <th align="center">ID</th>
        </c:if>
        <th align="center">Name</th>
        <th align="center">Movie</th>
        <th align="center">Planet</th>
    </tr>
    <c:forEach items="${aliens}" var="aliens">
        <tr>
            <c:if test="${sessionScope.status == 1}">
                <td align="center">${aliens.alienId}</td>
            </c:if>
            <td align="center">${aliens.alienName}</td>
            <td align="center">${aliens.movie.title}</td>
            <td align="center">${aliens.planet}</td>
        </tr>
    </c:forEach>
</table>
<c:if test="${sessionScope.status == 1 || sessionScope.status == 3}">
    <form method="get" action="mainWindow">
        <button type="submit" name="button" value="newAlien"><fmt:message key="message.add_alien" /></button>
    </form>
</c:if>
</body>
</html>
