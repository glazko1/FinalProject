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
        <input type="hidden" name="button" value="forwardToEditUser">
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
                <th align="center"><fmt:message key="message.first_name" /></th>
                <td align="center">
                    <label><input type="text" name="firstName" value="${firstName}"></label>
                </td>
            </tr>
            <tr>
                <th align="center"><fmt:message key="message.last_name" /></th>
                <td align="center">
                    <label><input type="text" name="lastName" value="${lastName}"></label>
                </td>
            </tr>
            <tr>
                <th align="center"><fmt:message key="message.email" /></th>
                <td align="center">
                    <label><input type="text" name="email" value="${email}"></label>
                </td>
            </tr>
        </table>
        <input type="hidden" name="userId" value="${userId}">
        <br><button type="submit" name="button" value="editUser"><fmt:message key="button.submit" /></button>
    </form>
</div>
</body>
</html>
