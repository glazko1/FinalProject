<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="text" />
<html>
<head>
    <title>${alien.alienName}</title>
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
        <input type="hidden" name="button" value="viewAlien">
        <input type="hidden" name="alienId" value="${alien.alienId}">
        <label for="locale"></label><select id="locale" name="locale" onchange="submit()">
        <option value="en_EN" ${locale == 'en_EN' ? 'selected' : ''}>English</option>
        <option value="de_DE" ${locale == 'de_DE' ? 'selected' : ''}>Deutsch</option>
        <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>Русский</option>
    </select>
    </form>
</div>
<div class="left-column" style="text-align: left; width: 25%; margin-left: 25%;">
    <h1>${alien.alienName}</h1>
    <table border="1" cellpadding="4" cellspacing="0">
        <tr>
            <th align="center"><fmt:message key="message.movie" /></th>
            <td align="center">
                <form method="get" action="mainWindow" style="display: inline; margin: 0;">
                    <input type="hidden" name="movieId" value="${alien.movie.movieId}">
                    <button class="underlined" type="submit" name="button" value="viewMovie">${alien.movie.title}</button>
                </form>
            </td>
        </tr>
        <tr>
            <th align="center"><fmt:message key="message.planet" /></th>
            <td align="center">${alien.planet}</td>
        </tr>
        <tr>
            <th align="center"><fmt:message key="message.average_rating" /></th>
            <td align="center">${alien.averageRating}</td>
        </tr>
    </table>
    <c:if test="${sessionScope.status.statusId == 1 || sessionScope.status.statusId == 3}">
        <br><form method="get" action="mainWindow">
            <input type="hidden" name="alienId" value="${alien.alienId}">
            <button type="submit" name="button" value="forwardToEditAlien"><fmt:message key="button.edit_information" /></button><br><br>
            <button type="submit" name="button" value="deleteAlien"><fmt:message key="button.delete_alien" /></button>
        </form>
    </c:if>
    <c:if test="${sessionScope.status.statusId == 2 || sessionScope.status.statusId == 4}">
        <br><form method="get" action="mainWindow">
            <input type="hidden" name="alienId" value="${alien.alienId}">
            <button type="submit" name="button" value="forwardToSuggestEdit"><fmt:message key="button.suggest_edit" /></button>
        </form>
    </c:if>
    <p>${alien.description}</p>
    <h2><fmt:message key="message.your_feedback" /></h2>
    <form method="post" action="mainWindow">
        <input type="hidden" name="alienId" value="${alien.alienId}">
        <input type="hidden" name="username" value="${sessionScope.username}">
        <table>
            <tr>
                <td>Rating</td>
                <td>
                    <label>
                        <select name="rating">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                        </select>
                    </label>
                </td>
            </tr>
            <tr>
                <td><fmt:message key="message.feedback" /></td>
                <td>
                    <label><input type="text" name="feedbackText"></label>
                </td>
            </tr>
        </table><br>
        <button type="submit" name="button" value="addFeedback"><fmt:message key="button.submit" /></button>
    </form>
    <h2><fmt:message key="message.feedbacks" /></h2>
    <c:forEach var="feedback" items="${feedbacks}">
        <form method="get" action="mainWindow" style="display: inline; margin: 0;">
            <input type="hidden" name="userId" value="${feedback.user.userId}">
            <button class="underlined" type="submit" name="button" value="viewUser" style="font-size: 14px;">${feedback.user.username}</button>
        </form> ${feedback.feedbackDateTime}
        <c:if test="${feedback.user.userId == sessionScope.userId || sessionScope.status.statusId == 1}">
            <form method="post" action="mainWindow" style="display: inline; margin: 0;">
                <input type="hidden" name="feedbackId" value="${feedback.feedbackId}">
                <input type="hidden" name="alienId" value="${alien.alienId}">
                <button type="submit" name="button" value="deleteFeedback" style="font-size: 14px;"><fmt:message key="message.delete" /></button>
            </form>
        </c:if>
        <br>
        <c:if test="${feedback.rating == 1}">&#9733; 1/5</c:if>
        <c:if test="${feedback.rating == 2}">&#9733;&#9733; 2/5</c:if>
        <c:if test="${feedback.rating == 3}">&#9733;&#9733;&#9733; 3/5</c:if>
        <c:if test="${feedback.rating == 4}">&#9733;&#9733;&#9733;&#9733; 4/5</c:if>
        <c:if test="${feedback.rating == 5}">&#9733;&#9733;&#9733;&#9733;&#9733; 5/5</c:if>
        <br>${feedback.feedbackText}<br><br>
    </c:forEach>
</div>
<div class="right-column">
    <br><br>
    <img src="${alien.imagePath}" align="center" alt="${alien.alienName}" height="300" style="border: 2px solid white;"><br><br>
    ${alien.alienName}
</div>
</body>
</html>
