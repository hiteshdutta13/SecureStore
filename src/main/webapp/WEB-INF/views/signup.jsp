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
                    <a class="nav-link rounded-0 rounded-top text-success fw-bold"  href="${contextPath}/login">Sign In</a>
                  </li>
                  <li class="nav-item" role="presentation">
                    <button class="nav-link rounded-0 rounded-top text-success fw-bold active" id="signup-tab" data-bs-toggle="tab" data-bs-target="#signup-tab-pane" type="button" role="tab" aria-controls="signup-tab-pane" aria-selected="false">Sign Up</button>
                  </li>
                </ul>
                <div class="tab-content" id="myTabContent">
                  <div class="tab-pane border-top border-dark pt-3 container active" id="signup-tab-pane" role="tabpanel" aria-labelledby="signup-tab" tabindex="0">
                        <form action="<c:url value='/register' />" method="post" class="needs-validation">
                              <div class="mb-3 mt-3">
                                <label for="firstName" class="form-label">First Name:</label>
                                <input type="text" class="form-control" name="firstName" placeholder="Enter first name" required>
                              </div>
                              <div class="mb-3 mt-3">
                                  <label for="lastName" class="form-label">Last Name:</label>
                                  <input type="text" class="form-control" name="lastName" placeholder="Enter last name" required>
                              </div>
                              <div class="mb-3 mt-3">
                                <label for="email" class="form-label">Email:</label>
                                <input type="text" class="form-control" name="email" placeholder="Enter email" required>
                              </div>
                              <div class="mb-3 mt-3">
                                <label for="username" class="form-label">Username:</label>
                                <input type="text" class="form-control" name="username" placeholder="Enter username" required>
                              </div>
                              <div class="mb-3">
                                <label for="password" class="form-label">Password:</label>
                                <input type="password" class="form-control" name="password" placeholder="Enter password" required>
                              </div>
                              <div class="mb-3">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                    <button type="submit" class="btn btn-success">Register</button>
                              </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>