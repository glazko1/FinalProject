<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="text" />
<html>
<head>
    <title><fmt:message key="title.all_users" /></title>
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
        <input type="hidden" name="button" value="viewAllUsers">
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
            <th align="center">
                <form method="get" action="mainWindow" style="display: inline; margin: 0;">
                    <input type="hidden" name="sortedBy" value="userId">
                    <c:if test="${sortedBy == 'userId' && sortType == 'ASC'}">
                        <input type="hidden" name="sortType" value="DESC">
                    </c:if>
                    <c:if test="${sortedBy != 'userId' || sortType == 'DESC'}">
                        <input type="hidden" name="sortType" value="ASC">
                    </c:if>
                    <button class="underlined" type="submit" name="button" value="viewAllUsersSorted" style="font-weight: bold">ID</button>
                </form>
            </th>
            <th align="center">
                <form method="get" action="mainWindow" style="display: inline; margin: 0;">
                    <input type="hidden" name="sortedBy" value="username">
                    <c:if test="${sortedBy == 'username' && sortType == 'ASC'}">
                        <input type="hidden" name="sortType" value="DESC">
                    </c:if>
                    <c:if test="${sortedBy != 'username' || sortType == 'DESC'}">
                        <input type="hidden" name="sortType" value="ASC">
                    </c:if>
                    <button class="underlined" type="submit" name="button" value="viewAllUsersSorted" style="font-weight: bold"><fmt:message key="message.username" /></button>
                </form>
            </th>
            <th align="center">
                <form method="get" action="mainWindow" style="display: inline; margin: 0;">
                    <input type="hidden" name="sortedBy" value="email">
                    <c:if test="${sortedBy == 'email' && sortType == 'ASC'}">
                        <input type="hidden" name="sortType" value="DESC">
                    </c:if>
                    <c:if test="${sortedBy != 'email' || sortType == 'DESC'}">
                        <input type="hidden" name="sortType" value="ASC">
                    </c:if>
                    <button class="underlined" type="submit" name="button" value="viewAllUsersSorted" style="font-weight: bold"><fmt:message key="message.email" /></button>
                </form>
            </th>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td align="center">${user.userId}</td>
                <td align="center">
                    <form method="get" action="mainWindow" style="display: inline; margin: 0;">
                        <input type="hidden" name="userId" value="${user.userId}">
                        <button class="underlined" type="submit" name="button" value="viewUser">${user.username}</button>
                    </form>
                </td>
                <td align="center">${user.email}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
