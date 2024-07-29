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
	<title>Secure Store | Reset Password</title>
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
                    <button class="nav-link rounded-0 rounded-top text-success fw-bold active" id="signin-tab" data-bs-toggle="tab" data-bs-target="#signin-tab-pane" type="button" role="tab" aria-controls="signin-tab-pane" aria-selected="true">Reset Password</button>
                  </li>
                </ul>
                <div class="tab-content" id="myTabContent">
                  <div class="tab-pane pt-3 border-top border-dark container active" id="signin-tab-pane" role="tabpanel" aria-labelledby="signin-tab" tabindex="0">
                        <c:choose>
                            <c:when test="${page eq 'email'}">
                                <form action="<c:url value='/reset/password/email' />" method="post" class="needs-validation">
                                      <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />
                                      <div class="mb-3 mt-3">
                                        <label for="emailAddress" class="form-label">Email:</label>
                                        <input type="email" class="form-control ${message ? 'is-invalid':''}" id="emailAddress" placeholder="Enter registered email" name="email" required>
                                        <div class="invalid-feedback">${message}</div>
                                      </div>
                                      <div class="mb-3">
                                           <button type="submit" class="btn btn-success">Continue</button>
                                      </div>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <div class="alert alert-success" role="alert">${message}</div>
                            </c:otherwise>
                        </c:choose>
                  </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>