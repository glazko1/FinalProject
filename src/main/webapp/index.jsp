<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>
<h2 align="center">Welcome</h2>
<form method="post" action="mainWindow">
    <div class="left-column">
        <h2>Sign In</h2>
        <table align="center">
            <tr>
                <td align="center">Username</td>
                <td align="center">
                    <label><input type="text" name="username"></label>
                </td>
            </tr>
            <tr>
                <td align="center">Password</td>
                <td align="center">
                    <label><input type="password" name="password"></label>
                </td>
            </tr>
        </table><br/>
        <button type="submit" name="button" value="signIn">Submit</button>
    </div>
    <div class="right-column">
        <h2>Sign up</h2>
        <table align="center">
            <tr>
                <td align="center">Username</td>
                <td align="center">
                    <label><input type="text" name="newUsername"></label>
                </td>
            </tr>
            <tr>
                <td align="center">First Name</td>
                <td align="center">
                    <label><input type="text" name="firstName"></label>
                </td>
            </tr>
            <tr>
                <td align="center">Last Name</td>
                <td align="center">
                    <label><input type="text" name="lastName"></label>
                </td>
            </tr>
            <tr>
                <td align="center">E-mail</td>
                <td align="center">
                    <label><input type="email" name="email"></label>
                </td>
            </tr>
            <tr>
                <td align="center">Password</td>
                <td align="center">
                    <label><input type="password" name="newPassword"></label>
                </td>
            </tr>
            <tr>
                <td align="center">Confirm password</td>
                <td align="center">
                    <label><input type="password" name="confirmedPassword"></label>
                </td>
            </tr>
        </table><br/>
        <button type="submit" name="button" value="signUp">Submit</button>
    </div>
</form>
</body>
</html>
