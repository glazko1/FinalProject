<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="text" />
<html>
<head>
    <title><fmt:message key="title.main_page" /></title>
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
    <h2><fmt:message key="title.welcome" />, ${sessionScope.firstName}!</h2>
    <c:if test="${message != null}">
        <fmt:message key="${message}" /><br><br>
    </c:if>
    <form method="get" action="mainWindow">
        <button type="submit" name="button" value="viewAllAliens" style="height: 200px; width: 200px; margin-right: 25px">
            <img src="img/spock.png" align="center" alt="" height="150px"><br>
            <fmt:message key="button.view_aliens" />
        </button>
        <button type="submit" name="button" value="viewAllMovies" style="height: 200px; width: 200px">
            <img src="img/transformers.png" align="center" alt="" height="150px"><br>
            <fmt:message key="button.view_movies" />
        </button>
        <br><br>
        <c:if test="${sessionScope.status.statusId == 1}">
            <button type="submit" name="button" value="viewAllUsers" style="height: 200px; width: 200px; margin-right: 25px">
                <img src="img/user.png" align="center" alt="" height="150px"><br>
                <fmt:message key="button.view_users" />
            </button>
        </c:if>
        <c:if test="${sessionScope.status.statusId == 1 || sessionScope.status.statusId == 3}">
            <button type="submit" name="button" value="viewAllSuggestedEdits" style="height: 200px; width: 200px;">
                <img src="img/edit.png" align="center" alt="" height="150px"><br>
                <fmt:message key="button.view_suggested_edits" />
            </button>
        </c:if>
    </form>
</div>
<c:remove var="message" />
</body>
</html>