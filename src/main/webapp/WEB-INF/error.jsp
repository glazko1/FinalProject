<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="text" />
<html>
<head>
    <title><fmt:message key="title.error" /></title>
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>
<div class="header">
    <c:if test="${sessionScope.status != null}">
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
    </c:if>
    <form style="display: inline; margin: 25px;">
        <label for="locale"></label><select id="locale" name="locale" onchange="submit()">
        <option value="en_EN" ${locale == 'en_EN' ? 'selected' : ''}>English</option>
        <option value="de_DE" ${locale == 'de_DE' ? 'selected' : ''}>Deutsch</option>
        <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>Русский</option>
    </select>
    </form>
</div>
<h1 align="center"><fmt:message key="message.error" /></h1>
<h2 align="center"><fmt:message key="message.try_later" /></h2>
</body>
</html>
