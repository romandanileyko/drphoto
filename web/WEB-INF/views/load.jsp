<%--
  Created by IntelliJ IDEA.
  User: danil
  Date: 10.03.2017
  Time: 19:54
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Load Your Photo</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script src = "/resources/jquery/jquery-3.1.1.js"></script>
</head>
<body>
<div class="container">
    <form:form action="/load?${_csrf.parameterName}=${_csrf.token}" method="POST" modelAttribute="fileUpload" enctype="multipart/form-data">
    Upload your file please:

            <input type="text" name="name" id="name"></input>


            <input type="file" name="file" id="file"></input>


        <input type="submit" value="upload" />
        <form:errors path="photo" cssStyle="color: #ff0000;" />
        <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
    </form:form>

</div>
<div>
    <c:forEach var="listPhoto" items="${img}" varStatus="obj" >
        <figure style="float: left">
            <img alt="${photo[obj.index].name}"  src="data:image/jpg;base64,<c:out value='${listPhoto}'/>" width="300" height="200"/>
            <figcaption><a href="/delete" id="${photo[obj.index].id}">Удалить</a> </figcaption>
        </figure>
    </c:forEach>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $('a').click(function (event) {
            event.preventDefault();
            $.ajax({
                url:$(this).attr('href'),
                data:{photoId:$(this).attr('id')}
            })
        })
    });
</script>
</body>
</html>
