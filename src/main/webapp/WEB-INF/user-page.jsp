<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="text" />
<c:set var="user" value="${user}" />
<html>
<head>
    <title>${user.firstName} ${user.lastName}</title>
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>
<form>
    <input type="hidden" name="button" value="viewUser">
    <input type="hidden" name="userId" value="${user.userId}">
    <label for="locale"></label><select id="locale" name="locale" onchange="submit()">
    <option value="en_EN" ${locale == 'en_EN' ? 'selected' : ''}>English</option>
    <option value="de_DE" ${locale == 'de_DE' ? 'selected' : ''}>Deutsch</option>
    <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>Русский</option>
</select>
</form>
<h2>${user.firstName} ${user.lastName}</h2>
<table border="1" cellpadding="4" cellspacing="0">
    <tr>
        <th align="center"><fmt:message key="message.username" /></th>
        <td align="center">${user.username}</td>
    </tr>
    <tr>
        <th align="center"><fmt:message key="message.email" /></th>
        <td align="center">${user.email}</td>
    </tr>
    <tr>
        <th align="center"><fmt:message key="message.birth_date" /></th>
        <td align="center">${user.birthDate}</td>
    </tr>
</table>
<form method="get" action="mainWindow">
    <c:if test="${user.userId == sessionScope.userId}">
        <button type="submit" name="button" value="editProfile"><fmt:message key="button.edit_profile" /></button><br>
    </c:if>
</form>
<form method="post" action="mainWindow">
    <input type="hidden" name="userId" value=${user.userId}>
    <c:if test="${sessionScope.status == 1 && user.userId != sessionScope.userId && user.statusId != 1}">
        <button type="submit" name="button" value="changeBanStatus">
            <c:if test="${user.banned == false}"><fmt:message key="button.ban_user" /></c:if>
            <c:if test="${user.banned == true}"><fmt:message key="button.unban_user" /></c:if>
        </button><br><br>
        <fmt:message key="message.change_status" />
        <label>
            <select name="status">
                <c:if test="${user.statusId != 1}">
                    <option value="1">Admin</option>
                </c:if>
                <c:if test="${user.statusId != 2}">
                    <option value="2">Movie Fan</option>
                </c:if>
                <c:if test="${user.statusId != 3}">
                    <option value="3">Alien Specialist</option>
                </c:if>
                <c:if test="${user.statusId != 4}">
                    <option value="4">User</option>
                </c:if>
            </select>
        </label>
        <button type="submit" name="button" value="changeUserStatus"><fmt:message key="button.submit" /></button>
    </c:if>
</form>
</body>
</html>
