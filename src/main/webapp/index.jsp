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
                <td align="center"><fmt:message key="message.birth_date" /></td>
                <td align="center">
                    <label for="day"></label><select id="day" name="day">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                    <option value="13">13</option>
                    <option value="14">14</option>
                    <option value="15">15</option>
                    <option value="16">16</option>
                    <option value="17">17</option>
                    <option value="18">18</option>
                    <option value="19">19</option>
                    <option value="20">20</option>
                    <option value="21">21</option>
                    <option value="22">22</option>
                    <option value="23">23</option>
                    <option value="24">24</option>
                    <option value="25">25</option>
                    <option value="26">26</option>
                    <option value="27">27</option>
                    <option value="28">28</option>
                    <option value="29">29</option>
                    <option value="30">30</option>
                    <option value="31">31</option>
                </select>
                    <label for="month"></label><select id="month" name="month">
                    <option value="1"><fmt:message key="month.january" /></option>
                    <option value="2"><fmt:message key="month.february" /></option>
                    <option value="3"><fmt:message key="month.march" /></option>
                    <option value="4"><fmt:message key="month.april" /></option>
                    <option value="5"><fmt:message key="month.may" /></option>
                    <option value="6"><fmt:message key="month.june" /></option>
                    <option value="7"><fmt:message key="month.july" /></option>
                    <option value="8"><fmt:message key="month.august" /></option>
                    <option value="9"><fmt:message key="month.september" /></option>
                    <option value="10"><fmt:message key="month.october" /></option>
                    <option value="11"><fmt:message key="month.november" /></option>
                    <option value="12"><fmt:message key="month.december" /></option>
                </select>
                    <label for="year"></label><select id="year" name="year">
                    <option value="1980">1980</option>
                    <option value="1981">1981</option>
                    <option value="1982">1982</option>
                    <option value="1983">1983</option>
                    <option value="1984">1984</option>
                    <option value="1985">1985</option>
                    <option value="1986">1986</option>
                    <option value="1987">1987</option>
                    <option value="1988">1988</option>
                    <option value="1989">1989</option>
                    <option value="1990">1990</option>
                    <option value="1991">1991</option>
                    <option value="1992">1992</option>
                    <option value="1993">1993</option>
                    <option value="1994">1994</option>
                    <option value="1995">1995</option>
                    <option value="1996">1996</option>
                    <option value="1997">1997</option>
                    <option value="1998">1998</option>
                    <option value="1999">1999</option>
                    <option value="2000">2000</option>
                    <option value="2001">2001</option>
                    <option value="2002">2002</option>
                    <option value="2003">2003</option>
                    <option value="2004">2004</option>
                    <option value="2005">2005</option>
                    <option value="2006">2006</option>
                    <option value="2007">2007</option>
                    <option value="2008">2008</option>
                    <option value="2009">2009</option>
                    <option value="2010">2010</option>
                </select>
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
    </form>
</div>
</body>
</html>