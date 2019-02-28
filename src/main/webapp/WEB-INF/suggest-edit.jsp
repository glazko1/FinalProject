<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="text" />
<html>
<head>
    <title><fmt:message key="button.suggest_edit" /></title>
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
        <input type="hidden" name="button" value="forwardToSuggestEdit">
        <input type="hidden" name="alienId" value="${alien.alienId}">
        <label for="locale"></label><select id="locale" name="locale" onchange="submit()">
        <option value="en_EN" ${locale == 'en_EN' ? 'selected' : ''}>English</option>
        <option value="de_DE" ${locale == 'de_DE' ? 'selected' : ''}>Deutsch</option>
        <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>Русский</option>
    </select>
    </form>
</div>
<div class="center-column">
    <h1>${alien.alienName}</h1>
    <table border="1" cellpadding="4" cellspacing="0" align="center">
        <tr>
            <th align="center"><fmt:message key="message.movie" /></th>
            <td align="center">${alien.movie.title}</td>
        </tr>
        <tr>
            <th align="center"><fmt:message key="message.planet" /></th>
            <td align="center">${alien.planet}</td>
        </tr>
        <tr>
            <th align="center"><fmt:message key="message.average_rating" /></th>
            <td align="center">${alien.averageRating}</td>
        </tr>
    </table>
    <p>${alien.description}</p>
    <h2><fmt:message key="button.suggest_description" /></h2>
    <form method="post" action="mainWindow">
        <label><input type="text" name="description"></label><br><br>
        <input type="hidden" name="userId" value="${sessionScope.userId}">
        <input type="hidden" name="alienId" value="${alien.alienId}">
        <button type="submit" name="button" value="suggestEdit"><fmt:message key="button.submit" /></button>
    </form>
</div>
</body>
</html>
