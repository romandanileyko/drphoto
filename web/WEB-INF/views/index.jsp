<%--
  Created by IntelliJ IDEA.
  User: danil
  Date: 11.02.2017
  Time: 22:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>$Title$</title>
  <spring:url value="/resources/css/bootstrap.css" var="bootstrap"/>
  <spring:url value="/resources/css/style.css" var="core_css"/>
  <link href="${bootstrap}" rel="stylesheet" />
  <link href="${core_css}" rel="stylesheet" />
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <%--<spring:url value="/resources/jquery/jquery-3.1.1.js" var="jquery" />--%>
  <%--<script src = "${jquery}"></script>--%>
  <script type="text/javascript" language="javascript">
    $(document).ready(function () {
        var flag = ${user.startsWith("anonymous")};
        if(flag)
        {
            $('#logout').hide()
            $('#login').show();
        }else if (!flag){
            $('#logout').show();
            $('#login').hide();
        }
    })
      function doAjaxPost() {
          var msg   = $('#loginForm').serialize();
          $.ajax({
              type: 'POST',
              url: '/',
              data: msg,
              success: function(data) {
                  $('#info').html(data);
              },
              error:  function(xhr, str){
                  alert('Возникла ошибка: ' + xhr.responseCode);
              }
          });
      }
  </script>
</head>
<body>
<div id="loggedUser">${user}</div>
<div class="login-pannel">
  <form id="loginForm" role="form" name = "f" class="form-inline" action=<c:url value="/"/> method="post">

    <div class="form-group">
      <label for="email">Email</label>
      <input type="text" class="form-control" id="email" placeholder="Введите email" name="username" value="">
    </div>

    <div class="form-group">
      <label for="pass">Пароль</label>
      <input type="password" name="password" class="form-control" id="pass" placeholder="Пароль">
    </div>

    <button id="login" type="submit" name="submit" value="login" class="btn btn-primary">Login</button>
      <a id="logout" href="<c:url value="/logout" />">LogOut</a>
    <a href= "<spring:url value="/register"/>">Регистрация</a>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <c:if test="${param.error != null}">
      <p>
        Invalid username and password.
      </p>
    </c:if>
  </form>
</div>
<div>
  <a href="load">Загрузка</a>
</div>
<div class="thumbnail">
  <c:forEach var="listPhotoAll" items="${allPhoto}" varStatus="status" >
    <div class="col-md-3 col-sm-4 col-xs-6">
      <figure style="float: left"><a href=<c:url value='/one-photo-${allPhotos[status.index].id}'/>>
        <img class="img-rounded" alt="${allPhotos[status.index].name}"  src="data:image/jpg;base64,<c:out value='${listPhotoAll}'/>" width="300" height="200"/></a>
        <figcaption><c:out value="${allPhotos[status.index].name}"/></figcaption>
      </figure>
    </div>
  </c:forEach>
</div>
</body>
</html>