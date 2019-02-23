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
<form>
    <input type="hidden" name="button" value="viewAlien">
    <input type="hidden" name="alienId" value="${alien.alienId}">
    <label for="locale"></label><select id="locale" name="locale" onchange="submit()">
    <option value="en_EN" ${locale == 'en_EN' ? 'selected' : ''}>English</option>
    <option value="de_DE" ${locale == 'de_DE' ? 'selected' : ''}>Deutsch</option>
    <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>Русский</option>
</select>
</form>
<h1>${alien.alienName}</h1>
<table border="1" cellpadding="4" cellspacing="0">
    <tr>
        <th align="center"><fmt:message key="message.movie" /></th>
        <td align="center">${alien.movie.title}</td>
    </tr>
    <tr>
        <th align="center"><fmt:message key="message.planet" /></th>
        <td align="center">${alien.planet}</td>
    </tr>
</table>
<p>${alien.description}</p>
<h2>Feedbacks</h2>
<c:forEach var="feedback" items="${feedbacks}">
    ${feedback.user.username} ${feedback.feedbackDateTime} <br>
    ${feedback.feedbackText} <br>
</c:forEach>
<h3>Your feedback</h3>
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
            <td>Feedback</td>
            <td>
                <label><input type="text" name="feedbackText"></label>
            </td>
        </tr>
    </table>
    <button type="submit" name="button" value="addFeedback"><fmt:message key="button.submit" /></button>
</form>
</body>
</html>
