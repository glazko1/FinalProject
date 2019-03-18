<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${not empty param.locale ? param.locale : not empty locale ? locale : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="text" />
<html>
<head>
    <title><fmt:message key="message.restore_password" /></title>
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>
<div class="header">
    <form style="display: inline; margin: 25px;">
        <input type="hidden" name="button" value="forwardToRestorePassword">
        <label for="locale"></label><select id="locale" name="locale" onchange="submit()">
        <option value="en_EN" ${locale == 'en_EN' ? 'selected' : ''}>English</option>
        <option value="de_DE" ${locale == 'de_DE' ? 'selected' : ''}>Deutsch</option>
        <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>Русский</option>
    </select>
    </form>
</div>
<div class="center-column">
    <h1><fmt:message key="message.restore_password" /></h1>
    <form method="post" action="mainWindow">
        <table align="center">
            <tr>
                <td align="center"><fmt:message key="message.username" /></td>
                <td align="center">
                    <label>
                        <input type="text" required pattern=".{6,15}" name="username" title="<fmt:message key="message.username_format" />">
                    </label>
                </td>
            </tr>
            <tr>
                <td align="center"><fmt:message key="message.first_name" /></td>
                <td align="center">
                    <label>
                        <input type="text" required pattern=".{2,30}" name="firstName" title="<fmt:message key="message.name_format" />">
                    </label>
                </td>
            </tr>
            <tr>
                <td align="center"><fmt:message key="message.last_name" /></td>
                <td align="center">
                    <label>
                        <input type="text" required pattern=".{2,30}" name="lastName" title="<fmt:message key="message.name_format" />">
                    </label>
                </td>
            </tr>
            <tr>
                <td align="center"><fmt:message key="message.email" /></td>
                <td align="center">
                    <label>
                        <input type="email" name="email">
                    </label>
                </td>
            </tr>
            <tr>
                <td align="center"><fmt:message key="message.new_password" /></td>
                <td align="center">
                    <label>
                        <input type="password" required pattern=".{8,30}" name="newPassword" title="<fmt:message key="message.password_format" />">
                    </label>
                </td>
            </tr>
            <tr>
                <td align="center"><fmt:message key="message.confirm_password" /></td>
                <td align="center">
                    <label>
                        <input type="password" required pattern=".{8,30}" name="confirmedPassword" title="<fmt:message key="message.password_format" />">
                    </label>
                </td>
            </tr>
        </table><br>
        <button type="submit" name="button" value="restorePassword"><fmt:message key="button.submit" /></button>
    </form>
</div>
</body>
</html>
