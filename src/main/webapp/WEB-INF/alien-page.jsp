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
    <h2><fmt:message key="message.feedbacks" /></h2>
    <c:forEach var="feedback" items="${feedbacks}">
        <form method="get" action="mainWindow" style="display: inline; margin: 0;">
            <input type="hidden" name="userId" value="${feedback.user.userId}">
            <button class="underlined" type="submit" name="button" value="viewUser" style="font-size: 14px;">${feedback.user.username}</button>
        </form> ${feedback.feedbackDateTime}
        <p>${feedback.feedbackText}</p>
    </c:forEach>
    <h3><fmt:message key="message.your_feedback" /></h3>
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
</div>
<div class="right-column" style="text-align: left">
    <p>${alien.description}</p>
</div>
<div class="center-column">



</div>
</body>
</html>
