<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="text" />
<html>
<head>
    <title><fmt:message key="title.change_password" /></title>
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
        <input type="hidden" name="button" value="forwardToChangePassword">
        <input type="hidden" name="userId" value="${userId}">
        <label for="locale"></label><select id="locale" name="locale" onchange="submit()">
        <option value="en_EN" ${locale == 'en_EN' ? 'selected' : ''}>English</option>
        <option value="de_DE" ${locale == 'de_DE' ? 'selected' : ''}>Deutsch</option>
        <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>Русский</option>
    </select>
    </form>
</div>
<div class="center-column">
    <h2>${username}</h2>
    <form method="post" action="mainWindow">
        <table border="1" cellpadding="4" cellspacing="0" align="center">
            <tr>
                <th align="center"><fmt:message key="message.current_password" /></th>
                <td align="center">
                    <label><input type="password" required pattern=".{8,30}" name="currentPassword" title="<fmt:message key="message.password_format" />"></label>
                </td>
            </tr>
            <tr>
                <th align="center"><fmt:message key="message.new_password" /></th>
                <td align="center">
                    <label><input type="password" required pattern=".{8,30}" name="newPassword" title="<fmt:message key="message.password_format" />"></label>
                </td>
            </tr>
            <tr>
                <th align="center"><fmt:message key="message.confirm_password" /></th>
                <td align="center">
                    <label><input type="password" required pattern=".{8,30}" name="confirmedPassword" title="<fmt:message key="message.password_format" />"></label>
                </td>
            </tr>
        </table><br>
        <input type="hidden" name="userId" value="${userId}">
        <button type="submit" name="button" value="changePassword"><fmt:message key="button.submit" /></button>
    </form>
</div>
</body>
</html>
