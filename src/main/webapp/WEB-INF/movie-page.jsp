<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="text" />
<html>
<head>
    <title>${movie.title}</title>
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
        <input type="hidden" name="button" value="viewMovie">
        <input type="hidden" name="movieId" value="${movie.movieId}">
        <label for="locale"></label><select id="locale" name="locale" onchange="submit()">
        <option value="en_EN" ${locale == 'en_EN' ? 'selected' : ''}>English</option>
        <option value="de_DE" ${locale == 'de_DE' ? 'selected' : ''}>Deutsch</option>
        <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>Русский</option>
    </select>
    </form>
</div>
<div class="center-column">
    <h1>${movie.title}</h1>
    <table border="1" cellpadding="4" cellspacing="0" align="center">
        <tr>
            <th align="center"><fmt:message key="message.running_time" /></th>
            <td align="center">${movie.runningTime} <fmt:message key="message.minutes" /></td>
        </tr>
        <tr>
            <th align="center"><fmt:message key="message.budget" /></th>
            <td align="center">${movie.budget}$</td>
        </tr>
        <tr>
            <th align="center"><fmt:message key="message.release_date" /></th>
            <td align="center">${movie.releaseDate}</td>
        </tr>
    </table>
    <c:if test="${sessionScope.status.statusId == 1 || sessionScope.status.statusId == 2}">
        <br><form method="get" action="mainWindow">
            <input type="hidden" name="movieId" value="${movie.movieId}">
            <button type="submit" name="button" value="forwardToEditMovie"><fmt:message key="button.edit_information" /></button>
        </form>
    </c:if>
    <c:if test="${sessionScope.status.statusId == 1}">
        <form method="get" action="mainWindow">
            <input type="hidden" name="movieId" value="${movie.movieId}">
            <button type="submit" name="button" value="deleteMovie"><fmt:message key="button.delete_movie" /></button>
        </form>
    </c:if>
</div>
</body>
</html>
