<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="text" />
<html>
<head>
    <title><fmt:message key="title.edit_information" /></title>
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
        <input type="hidden" name="button" value="forwardToEditMovie">
        <input type="hidden" name="movieId" value="${movieId}">
        <label for="locale"></label><select id="locale" name="locale" onchange="submit()">
        <option value="en_EN" ${locale == 'en_EN' ? 'selected' : ''}>English</option>
        <option value="de_DE" ${locale == 'de_DE' ? 'selected' : ''}>Deutsch</option>
        <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>Русский</option>
    </select>
    </form>
</div>
<div class="center-column">
    <h2>${title}</h2>
    <form method="post" action="mainWindow">
        <table border="1" cellpadding="4" cellspacing="0" align="center">
            <tr>
                <th align="center"><fmt:message key="message.running_time" /></th>
                <td align="center">
                    <label>
                        <input type="number" required min="1" max="250" step="1" name="runningTime" title="<fmt:message key="message.running_time_format" />" />
                    </label>
                </td>
            </tr>
            <tr>
                <th align="center"><fmt:message key="message.budget" /></th>
                <td align="center">
                    <label>
                        <input type="number" required min="1000000" max="500000000" step="1000000" name="budget" title="<fmt:message key="message.budget_format" />" />
                    </label>
                </td>
            </tr>
            <tr>
                <td align="center"><fmt:message key="message.release_date" /></td>
                <td align="center">
                    <label>
                        <input type="date" required name="releaseDate" min="1950-01-01">
                    </label>
                </td>
            </tr>
        </table><br>
        <input type="hidden" name="movieId" value="${movieId}">
        <button type="submit" name="button" value="editMovie"><fmt:message key="button.submit" /></button>
    </form>
</div>
</body>
</html>
