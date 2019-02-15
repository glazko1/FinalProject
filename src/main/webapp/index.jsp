<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="text" />
<html>
<head>
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
<h2 align="center"><fmt:message key="message.welcome" /></h2>
<form method="post" action="mainWindow">
    <div class="left-column">
        <h2><fmt:message key="message.sign_in" /></h2>
        <table align="center">
            <tr>
                <td align="center"><fmt:message key="message.username" /></td>
                <td align="center">
                    <label><input type="text" name="username"></label>
                </td>
            </tr>
            <tr>
                <td align="center"><fmt:message key="message.password" /></td>
                <td align="center">
                    <label><input type="password" name="password"></label>
                </td>
            </tr>
        </table><br/>
        <button type="submit" name="button" value="signIn"><fmt:message key="button.submit" /></button>
    </div>
    <div class="right-column">
        <h2><fmt:message key="message.sign_up" /></h2>
        <table align="center">
            <tr>
                <td align="center"><fmt:message key="message.username" /></td>
                <td align="center">
                    <label><input type="text" name="newUsername"></label>
                </td>
            </tr>
            <tr>
                <td align="center"><fmt:message key="message.first_name" /></td>
                <td align="center">
                    <label><input type="text" name="firstName"></label>
                </td>
            </tr>
            <tr>
                <td align="center"><fmt:message key="message.last_name" /></td>
                <td align="center">
                    <label><input type="text" name="lastName"></label>
                </td>
            </tr>
            <tr>
                <td align="center"><fmt:message key="message.email" /></td>
                <td align="center">
                    <label><input type="email" name="email"></label>
                </td>
            </tr>
            <tr>
                <td align="center"><fmt:message key="message.password" /></td>
                <td align="center">
                    <label><input type="password" name="newPassword"></label>
                </td>
            </tr>
            <tr>
                <td align="center"><fmt:message key="message.confirm_password" /></td>
                <td align="center">
                    <label><input type="password" name="confirmedPassword"></label>
                </td>
            </tr>
        </table><br/>
        <button type="submit" name="button" value="signUp"><fmt:message key="button.submit" /></button>
    </div>
</form>
</body>
</html>
