<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <c:set var="contextPath" value="${pageContext.request.contextPath}" scope="session"/>
    <link rel="stylesheet" href="${contextPath}/css/bootstrap.min.css">
    <link rel="shortcut icon" href="${contextPath}/images/secure-store-icon.webp" />
    <script src="${contextPath}/js/jquery.min.js"></script>
    <script src="${contextPath}/js/bootstrap.bundle.min.js"></script>
    <script src="${contextPath}/js/ajax.handler.min.js"></script>
	<title>Secure Store | Login</title>
	<style>
	.nav-pills .nav-link.active, .nav-pills .show>.nav-link {
        color: var(--bs-nav-pills-link-active-color);
        background-color: #111314;
    }
	</style>
</head>
<body class="bg-light bg-gradient">
    <div class="container">
        <div class="row pt-5">
            <div class="col-sm-8 col-md-8 col-lg-8 col-xl-8 col-xxl-8 fw-bold">
                <img src="${contextPath}/images/secure-store-icon.webp" alt="Secure Store" style="width:50px;" class="rounded-2"/> <span class="text-success">Secure</span> <span class="text-dark">Store</span>
            </div>
            <div class="col-sm-4 col-md-4 col-lg-4 col-xl-4 col-xxl-4"><!-- empty --></div>
        </div>
        <div class="row">
            <div class="col-sm-8 col-md-8 col-lg-8 col-xl-8 col-xxl-8 border-end">
                 <jsp:include page="securestore.jsp"/>
            </div>
            <div class="col-sm-4 col-md-4 col-lg-4 col-xl-4 col-xxl-4">
                <ul class="nav nav-pills nav-justified" id="myTab" role="tablist">
                  <li class="nav-item" role="presentation">
                    <button class="nav-link rounded-0 rounded-top text-success fw-bold active" id="signin-tab" data-bs-toggle="tab" data-bs-target="#signin-tab-pane" type="button" role="tab" aria-controls="signin-tab-pane" aria-selected="true">Sign In</button>
                  </li>
                  <li class="nav-item" role="presentation">
                    <a class="nav-link rounded-0 rounded-top text-success fw-bold" href="${contextPath}/register">Sign Up</a>
                  </li>
                </ul>
                <div class="tab-content" id="myTabContent">
                  <div class="tab-pane pt-3 border-top border-dark container active" id="signin-tab-pane" role="tabpanel" aria-labelledby="signin-tab" tabindex="0">
                        <form action="<c:url value='/login' />" method="post" class="needs-validation">
                              <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />
                              <div class="mb-3 mt-3">
                                <label for="username" class="form-label">Username:</label>
                                <input type="text" class="form-control" id="signInUsername" placeholder="Enter username" name="username">
                              </div>
                              <div class="mb-3">
                                <label for="password" class="form-label">Password:</label>
                                <input type="password" class="form-control" id="signInPassword" placeholder="Enter password" name="password">
                              </div>
                              <div class="mb-3">
                                <a href="${contextPath}" class="text-decoration-none"> Forgot password ?</a>
                              </div>
                              <div class="mb-3">
                                    <button type="submit" class="btn btn-success">Login</button>
                              </div>
                        </form>
                  </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>