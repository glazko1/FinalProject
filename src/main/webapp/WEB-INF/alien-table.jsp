<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="text" />
<html>
<head>
    <title>All aliens</title>
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
        <input type="hidden" name="button" value="viewAllAliens">
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
            <c:if test="${sessionScope.status == 1}">
                <th align="center">
                    <form method="get" action="mainWindow" style="display: inline; margin: 0;">
                        <input type="hidden" name="sortedBy" value="alienId">
                        <c:if test="${sortedBy == 'alienId' && sortType == 'ASC'}">
                            <input type="hidden" name="sortType" value="DESC">
                        </c:if>
                        <c:if test="${sortedBy != 'alienId' || sortType == 'DESC'}">
                            <input type="hidden" name="sortType" value="ASC">
                        </c:if>
                        <button class="underlined" type="submit" name="button" value="viewAllAliensSorted" style="font-weight: bold">ID</button>
                    </form>
                </th>
            </c:if>
            <th align="center">
                <form method="get" action="mainWindow" style="display: inline; margin: 0;">
                    <input type="hidden" name="sortedBy" value="alienName">
                    <c:if test="${sortedBy == 'alienName' && sortType == 'ASC'}">
                        <input type="hidden" name="sortType" value="DESC">
                    </c:if>
                    <c:if test="${sortedBy != 'alienName' || sortType == 'DESC'}">
                        <input type="hidden" name="sortType" value="ASC">
                    </c:if>
                    <button class="underlined" type="submit" name="button" value="viewAllAliensSorted" style="font-weight: bold"><fmt:message key="message.name" /></button>
                </form>
            </th>
            <th align="center">
                <form method="get" action="mainWindow" style="display: inline; margin: 0;">
                    <input type="hidden" name="sortedBy" value="movieTitle">
                    <c:if test="${sortedBy == 'movieTitle' && sortType == 'ASC'}">
                        <input type="hidden" name="sortType" value="DESC">
                    </c:if>
                    <c:if test="${sortedBy != 'movieTitle' || sortType == 'DESC'}">
                        <input type="hidden" name="sortType" value="ASC">
                    </c:if>
                    <button class="underlined" type="submit" name="button" value="viewAllAliensSorted" style="font-weight: bold"><fmt:message key="message.movie" /></button>
                </form>
            </th>
            <th align="center">
                <form method="get" action="mainWindow" style="display: inline; margin: 0;">
                    <input type="hidden" name="sortedBy" value="planet">
                    <c:if test="${sortedBy == 'planet' && sortType == 'ASC'}">
                        <input type="hidden" name="sortType" value="DESC">
                    </c:if>
                    <c:if test="${sortedBy != 'planet' || sortType == 'DESC'}">
                        <input type="hidden" name="sortType" value="ASC">
                    </c:if>
                    <button class="underlined" type="submit" name="button" value="viewAllAliensSorted" style="font-weight: bold"><fmt:message key="message.planet" /></button>
                </form>
            </th>
        </tr>
        <c:forEach items="${aliens}" var="alien">
            <tr>
                <c:if test="${sessionScope.status == 1}">
                    <td align="center">${alien.alienId}</td>
                </c:if>
                <td align="center">
                    <form method="get" action="mainWindow" style="display: inline; margin: 0;">
                        <input type="hidden" name="alienId" value="${alien.alienId}">
                        <button class="underlined" type="submit" name="button" value="viewAlien">${alien.alienName}</button>
                    </form>
                </td>
                <td align="center">
                    <form method="get" action="mainWindow" style="display: inline; margin: 0;">
                        <input type="hidden" name="movieId" value="${alien.movie.movieId}">
                        <button class="underlined" type="submit" name="button" value="viewMovie">${alien.movie.title}</button>
                    </form>
                </td>
                <td align="center">${alien.planet}</td>
            </tr>
        </c:forEach>
    </table>
    <c:if test="${sessionScope.status == 1 || sessionScope.status == 3}">
        <form method="get" action="mainWindow">
            <br><button type="submit" name="button" value="forwardToNewAlien"><fmt:message key="message.add_alien" /></button>
        </form>
    </c:if>
</div>
</body>
</html>
