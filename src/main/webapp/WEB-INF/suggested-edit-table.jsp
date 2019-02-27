<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="text" />
<html>
<head>
    <title><fmt:message key="title.suggested_edits" /></title>
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
    <form method="post" action="mainWindow" style="display: inline; margin: 25px;">
        <button class="underlined" name="button" value="logout"><fmt:message key="button.logout" /></button>
    </form>
    <form style="display: inline; margin: 25px;">
        <input type="hidden" name="button" value="viewAllSuggestedEdits">
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
            <th align="center"><fmt:message key="message.username" /></th>
            <th align="center"><fmt:message key="message.alien_name" /></th>
            <th align="center"><fmt:message key="message.date_time" /></th>
        </tr>
        <c:forEach items="${edits}" var="edit">
            <tr>
                <td>
                    <form method="get" action="mainWindow" style="display: inline; margin: 0;">
                        <input type="hidden" name="editId" value="${edit.editId}">
                        <button class="underlined" type="submit" name="button" value="viewSuggestedEdit">${edit.editId}</button>
                    </form>
                </td>
                <td align="center">${edit.user.username}</td>
                <td align="center">${edit.alien.alienName}</td>
                <td align="center">${edit.editDateTime}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
