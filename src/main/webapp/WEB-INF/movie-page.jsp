<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="text" />
<c:set var="movie" value="${sessionScope.movie}" />
<html>
<head>
    <title>${movie.title}</title>
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>
<form>
    <label for="locale"></label><select id="locale" name="locale" onchange="submit()">
    <option value="en_EN" ${locale == 'en_EN' ? 'selected' : ''}>English</option>
    <option value="de_DE" ${locale == 'de_DE' ? 'selected' : ''}>Deutsch</option>
    <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>Русский</option>
</select>
</form>
<h2>${movie.title}</h2>
<table border="1" cellpadding="4" cellspacing="0">
    <tr>
        <th align="center"><fmt:message key="message.running_time" /></th>
        <td align="center">${movie.runningTime} <fmt:message key="message.minutes" /></td>
    </tr>
    <tr>
        <th align="center"><fmt:message key="message.budget" /></th>
        <td align="center">${movie.budget}$</td>
    </tr>
    <tr>
        <th align="center"><fmt:message key="message.release_date" /></th>
        <td align="center">${movie.releaseDate}</td>
    </tr>
</table>
</body>
</html>
