<%--
  Created by IntelliJ IDEA.
  User: danil
  Date: 11.02.2017
  Time: 23:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Admin Page</title>
    <script src = "/resources/jquery/jquery-3.1.1.js"></script>
</head>
<body>
<strong>${user}</strong>, Welcome to Admin Page.
<a href="<c:url value="/logout" />">Logout</a>
<div>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Username</th>
            <th>Status</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${userlist}" var="user">
            <tr>
                <td>${user.username}</td>
                <td>${user.enabled}</td>
                <td><a id="delete" href="<c:url value='/delete-user-${user.id}'/>">Удалить!</a></td>
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
    });
</script>
</html>