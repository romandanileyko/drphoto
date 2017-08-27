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
    <spring:url value="/resources/css/bootstrap.css" var="bootstrap"/>
    <link href="${bootstrap}" rel="stylesheet" />
</head>
<body>
<div>
    <p>Upload your file please:</p>
    <form:form action="/load?${_csrf.parameterName}=${_csrf.token}" method="POST" modelAttribute="fileUpload" enctype="multipart/form-data" cssClass="form-inline">
            <div class="form-group col-xs-3">
                <input class="form-control col-xs-3" type="text" name="name" id="name"/>
            </div>

            <div class="form-group col-xs-2">
                <form:select path="category_id.id" cssClass="form-control">
                    <form:options items="${category}" itemValue="id" itemLabel="name"/>
                </form:select>
            </div>
            <div class="form-group">
                <input type="file" name="file" id="file" class="btn btn-primary btn-file"/>
            </div>
            <div class="form-group">
                <input class=" btn btn-primary" type="submit" value="upload" />
            </div>

        <form:errors path="photo" cssStyle="color: #ff0000;" />
        <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
    </form:form>
</div>
<div class="thumbnail">
    <c:forEach var="listPhoto" items="${img}" varStatus="obj" >
        <div class="col-md-3 col-sm-4 col-xs-6">
            <figure style="float: left">
                <img alt="${photo[obj.index].name}"  src="data:image/jpg;base64,<c:out value='${listPhoto}'/>" width="300" height="200" class="img-rounded" />
                <figcaption><a href=<spring:url value="/delete"/> id="${photo[obj.index].id}">Удалить</a> </figcaption>
            </figure>
        </div>
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
