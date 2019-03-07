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
<div class="header">
    <c:if test="${sessionScope.status != null}">
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
    </c:if>
    <form style="display: inline; margin: 25px;">
        <label for="locale"></label><select id="locale" name="locale" onchange="submit()">
        <option value="en_EN" ${locale == 'en_EN' ? 'selected' : ''}>English</option>
        <option value="de_DE" ${locale == 'de_DE' ? 'selected' : ''}>Deutsch</option>
        <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>Русский</option>
    </select>
    </form>
</div>
<h1 align="center"><fmt:message key="message.welcome" /></h1>
<div class="left-column">
    <form method="post" action="mainWindow">
        <h2><fmt:message key="message.sign_in" /></h2>
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
                <td align="center"><fmt:message key="message.password" /></td>
                <td align="center">
                    <label>
                        <input type="password" required pattern=".{8,30}" name="password" title="<fmt:message key="message.password_format" />">
                    </label>
                </td>
            </tr>
        </table>
        <c:if test="${signInMessage != null}">
            <br><fmt:message key="${signInMessage}" /><br>
        </c:if>
        <br><button type="submit" name="button" value="signIn"><fmt:message key="button.submit" /></button>
    </form>
    <form method="get" action="mainWindow">
        <button class="underlined" type="submit" name="button" value="forwardToRestorePassword"><fmt:message key="message.forgot_password" /></button>
    </form>
</div>
<div class="right-column">
    <form method="post" action="mainWindow">
        <h2><fmt:message key="message.sign_up" /></h2>
        <table align="center">
            <tr>
                <td align="center"><fmt:message key="message.username" /></td>
                <td align="center">
                    <label>
                        <input type="text" required pattern=".{6,15}" name="newUsername" title="<fmt:message key="message.username_format" />" />
                    </label>
                </td>
            </tr>
            <tr>
                <td align="center"><fmt:message key="message.first_name" /></td>
                <td align="center">
                    <label>
                        <input type="text" required pattern=".{2,30}" name="firstName" title="<fmt:message key="message.name_format" />" />
                    </label>
                </td>
            </tr>
            <tr>
                <td align="center"><fmt:message key="message.last_name" /></td>
                <td align="center">
                    <label>
                        <input type="text" required pattern=".{2,30}" name="lastName" title="<fmt:message key="message.name_format" />" />
                    </label>
                </td>
            </tr>
            <tr>
                <td align="center"><fmt:message key="message.email" /></td>
                <td align="center">
                    <label>
                        <input type="email" required name="email">
                    </label>
                </td>
            </tr>
            <tr>
                <td align="center"><fmt:message key="message.birth_date" /></td>
                <td>
                    <label>
                        <input type="date" required name="birthDate" min="1950-01-01" max="2015-12-31">
                    </label>
                </td>
            <tr>
                <td align="center"><fmt:message key="message.password" /></td>
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
        </table>
        <c:if test="${signUpMessage != null}">
            <br><fmt:message key="${signUpMessage}" /><br>
        </c:if>
        <br><button type="submit" name="button" value="signUp"><fmt:message key="button.submit" /></button>
    </form>
</div>
<c:remove var="signInMessage" />
<c:remove var="signUpMessage" />
</body>
</html>