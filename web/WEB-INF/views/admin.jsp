<%--
  Created by IntelliJ IDEA.
  User: danil
  Date: 11.02.2017
  Time: 23:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Admin Page</title>
    <spring:url value="/resources/css/bootstrap.css" var="bootstrap"/>
    <link href="${bootstrap}" rel="stylesheet" />
    <script src = "/resources/jquery/jquery-3.1.1.js"></script>
</head>
<body>
<strong>${user}</strong>, Welcome to Admin Page.
<a href="<c:url value="/logout" />">Logout</a>
<div>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Username</th>
            <th>Status</th>
            <th>Role</th>
            <th>Delete</th>
            <th>Enable/Disable</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${userlist}" var="user">
            <tr>
                <td>${user.username}</td>
                <td>${user.enabled}</td>
                <td>${user.roles.role}</td>
                <td><a id="delete" href="<c:url value='/delete-user-${user.id}'/>">Удалить!</a></td>
                <td><c:choose>
                        <c:when test="${user.enabled=='true'}">
                            <a id="disable" href="<c:url value='/disable-user-${user.id}'/>">Disable</a>
                        </c:when>
                         <c:when test="${user.enabled=='false'}">
                            <a id="enable" href="<c:url value='/enable-user-${user.id}'/>">Enable</a>
                        </c:when>
                </c:choose></td>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function () {
        $('a#delete').click(function (event) {
            event.preventDefault();
            $.ajax({
                url:$(this).attr('href'),
                data:{id:$(this).attr('value')}
            })
        })
        $('a#disable').click(function (event) {
            event.preventDefault();
            $.ajax({
                url:$(this).attr('href'),
                data:{id:$(this).attr('value')}
            })
        })
        $('a#enable').click(function (event) {
            event.preventDefault();
            $.ajax({
                url:$(this).attr('href'),
                data:{id:$(this).attr('value')}
            })
        })
    });
</script>
</html>