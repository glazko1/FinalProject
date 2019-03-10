<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="text" />
<html>
<head>
    <title><fmt:message key="button.edit_information" /></title>
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
        <input type="hidden" name="button" value="forwardToEditAlien">
        <input type="hidden" name="alienId" value="${alienId}">
        <label for="locale"></label><select id="locale" name="locale" onchange="submit()">
        <option value="en_EN" ${locale == 'en_EN' ? 'selected' : ''}>English</option>
        <option value="de_DE" ${locale == 'de_DE' ? 'selected' : ''}>Deutsch</option>
        <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>Русский</option>
    </select>
    </form>
</div>
<div class="center-column">
    <h2>${alienName}</h2>
    <form method="post" action="mainWindow">
        <table border="1" cellpadding="4" cellspacing="0" align="center">
            <tr>
                <th align="center"><fmt:message key="message.movie" /></th>
                <td align="center">
                    <label>
                        <select name="movie">
                            <c:forEach items="${movies}" var="movie">
                                <option value="${movie.title}">${movie.title}</option>
                            </c:forEach>
                        </select>
                    </label>
                </td>
            </tr>
            <tr>
                <th align="center"><fmt:message key="message.planet" /></th>
                <td align="center">
                    <label>
                        <input type="text" required pattern=".{4,20}" name="planet" title="<fmt:message key="message.planet_format" />" value="${planet}" />
                    </label>
                </td>
            </tr>
            <tr>
                <th align="center"><fmt:message key="message.description" /></th>
                <td align="center">
                    <label>
                        <input type="text" required pattern=".{1,255}" name="description" title="<fmt:message key="message.description_format" />" value="${description}" />
                    </label>
                </td>
            </tr>
        </table><br>
        <input type="hidden" name="alienId" value="${alienId}">
        <button type="submit" name="button" value="editAlien"><fmt:message key="button.submit" /></button>
    </form>
</div>
</body>
</html>
