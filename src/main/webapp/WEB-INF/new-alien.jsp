<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="text" />
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="mainWindow">
    <table align="center">
        <tr>
            <td align="center"><fmt:message key="message.alien_name" /></td>
            <td align="center">
                <label><input type="text" name="alienName"></label>
            </td>
        </tr>
        <tr>
            <td align="center"><fmt:message key="message.movie" /></td>
            <td align="center">
                <label>
                    <select name="movie">
                        <c:forEach items="${movies}" var="movie">
                            <option value="${movie.title}">${movie.title}</option>
                        </c:forEach>
                    </select>
                </label>
            </td>
        </tr>
        <tr>
            <td align="center"><fmt:message key="message.planet" /></td>
            <td align="center">
                <label><input type="text" name="planet"></label>
            </td>
        </tr>
        <tr>
            <td align="center"><fmt:message key="message.description" /></td>
            <td align="center">
                <label><input type="text" name="description" height="30px"></label>
            </td>
        </tr>
    </table>
    <button type="submit" name="button" value="addAlien"><fmt:message key="button.submit" /></button>
</form>
</body>
</html>
