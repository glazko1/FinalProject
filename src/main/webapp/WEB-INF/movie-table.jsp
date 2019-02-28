<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="text" />
<html>
<head>
    <title>All movies</title>
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>
<div class="header">
    <form method="post" action="mainWindow" style="display: inline; margin: 25px;">
        <button class="underlined" name="button" value="mainPage"><fmt:message key="button.main_page" /></button>
    </form>
    <form method="get" action="mainWindow" style="display: inline; margin: 25px;">
        <input type="hidden" name="userId" value="${sessionScope.userId}">
        <button class="underlined" name="button" value="viewUser"><fmt:message key="button.my_profile" /></button>
    </form>
    <form method="get" action="mainWindow" style="display: inline; margin: 25px;">
        <input type="hidden" name="userId" value="${sessionScope.userId}">
        <button class="underlined" name="button" value="viewNotifications"><fmt:message key="button.notifications" /></button>
    </form>
    <form method="post" action="mainWindow" style="display: inline; margin: 25px;">
        <button class="underlined" name="button" value="logout"><fmt:message key="button.logout" /></button>
    </form>
    <form style="display: inline; margin: 25px;">
        <label for="locale"></label><select id="locale" name="locale" onchange="submit()">
        <option value="en_EN" ${locale == 'en_EN' ? 'selected' : ''}>English</option>
        <option value="de_DE" ${locale == 'de_DE' ? 'selected' : ''}>Deutsch</option>
        <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>Русский</option>
    </select>
    </form>
</div>
<div class="center-column">
    <table border="1" cellpadding="4" cellspacing="0" align="center">
        <tr>
            <th align="center">ID</th>
            <th align="center"><fmt:message key="message.movie" /></th>
            <th align="center"><fmt:message key="message.running_time" /></th>
        </tr>
        <c:forEach items="${movies}" var="movie">
            <tr>
                <td align="center">${movie.movieId}</td>
                <td align="center">
                    <form method="get" action="mainWindow" style="display: inline; margin: 0;">
                        <input type="hidden" name="movieId" value="${movie.movieId}">
                        <button class="underlined" type="submit" name="button" value="viewMovie">${movie.title}</button>
                    </form>
                </td>
                <td align="center">${movie.runningTime} <fmt:message key="message.minutes" /></td>
            </tr>
        </c:forEach>
    </table>
    <c:if test="${sessionScope.status == 1 || sessionScope.status == 2}">
        <form method="get" action="mainWindow">
            <br><button type="submit" name="button" value="forwardToNewMovie"><fmt:message key="message.add_movie" /></button>
        </form>
    </c:if>
</div>
</body>
</html>
