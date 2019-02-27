<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="text" />
<html>
<head>
    <title><fmt:message key="title.suggested_edit" /></title>
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
        <input type="hidden" name="editId" value="${edit.editId}">
        <input type="hidden" name="button" value="viewSuggestedEdit">
        <label for="locale"></label><select id="locale" name="locale" onchange="submit()">
        <option value="en_EN" ${locale == 'en_EN' ? 'selected' : ''}>English</option>
        <option value="de_DE" ${locale == 'de_DE' ? 'selected' : ''}>Deutsch</option>
        <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>Русский</option>
    </select>
    </form>
</div>
<div class="center-column">
    <h1>${edit.alien.alienName}</h1>
    <table border="1" cellpadding="4" cellspacing="0" align="center">
        <tr>
            <th align="center"><fmt:message key="message.movie" /></th>
            <td align="center">${edit.alien.movie.title}</td>
        </tr>
        <tr>
            <th align="center"><fmt:message key="message.planet" /></th>
            <td align="center">${edit.alien.planet}</td>
        </tr>
        <tr>
            <th align="center"><fmt:message key="message.average_rating" /></th>
            <td align="center">${edit.alien.averageRating}</td>
        </tr>
    </table>
    <h2><fmt:message key="message.current_description" /></h2>
    <p>${edit.alien.description}</p>
    <h2><fmt:message key="message.suggested_description" /></h2>
    <p>${edit.editText}</p>
    <form method="post" action="mainWindow">
        <input type="hidden" name="editId" value="${edit.editId}">
        <input type="hidden" name="userId" value="${edit.user.userId}">
        <button type="submit" name="button" value="acceptEdit"><fmt:message key="button.accept_edit" /></button><br><br>
        <button type="submit" name="button" value="rejectEdit"><fmt:message key="button.reject_edit" /></button>
    </form>
</div>
</body>
</html>
