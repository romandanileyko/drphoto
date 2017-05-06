<%--
  Created by IntelliJ IDEA.
  User: danil
  Date: 17.04.2017
  Time: 20:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Photo</title>
    <spring:url value="/resources/css/bootstrap.css" var="bootstrap"/>
    <link href="${bootstrap}" rel="stylesheet" />
    <script src = "/resources/jquery/jquery-3.1.1.js"></script>
</head>
<body>
<div id="owner">${user}</div>
    <div id="photo" align="center">
        <figure>
            <img class="img-rounded" alt="${photoObject.name}"  src="data:image/jpg;base64,<c:out value='${onePhoto}'/>" width="600" height="400"/>
            <figcaption><c:out value="${photoObject.name}"/></figcaption>
        </figure>
    </div>

    <div id="commentInput" class="col-xs-4">
        <c:if test="${user!='anonymousUser'}">
            <textarea class="form-control col-xs-4" id="commenText"></textarea>
            <br>
            <input class="btn btn-primary col-xs-4" id = "send" type="submit" value="Отправить"/>
            <span id="result"></span>
        </c:if>
    </div>

    <div id="comment">
        <c:forEach var="commentItem" items="${commentList}">
            <span>${commentItem.getId_user().getUsername()}</span>
            <div>${commentItem.getComment()}</div>
        </c:forEach>
    </div>
</body>
<script type="text/javascript">
    $(document).ready(function() {
        $('#send').click(function() {
            $.ajax({
                type:'POST',
                url:'/addComment?${_csrf.parameterName}=${_csrf.token}',
                data:{comment:$('#commenText').val(),
                        photoId:${photoObject.id}},
                dataType:'text',
                success: function (response) {
                    if(response=='comment was saved.')
                            $('#result').html('success saved.');
                }
            })
        });
    });
</script>
</html>
