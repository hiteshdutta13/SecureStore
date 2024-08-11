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
                <img src="${contextPath}/images/secure-store-icon.webp" alt="Secure Store" style="width:50px;"/> <span class="text-success">Secure</span> <span class="text-dark">Store</span>
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
                                <form action="<c:url value='/reset/password/email' />" method="post" class="needs-validation" autocomplete="off">
                                      <div class="mb-3 mt-3">
                                        <label for="emailAddress" class="form-label">Email:</label>
                                        <input type="email" class="form-control ${not empty message ? 'is-invalid':''}" id="emailAddress" placeholder="Enter registered email" name="email" required value="${requestedEmail}"/>
                                        <div class="invalid-feedback">${message}</div>
                                      </div>
                                      <div class="mb-3">
                                           <button type="submit" class="btn btn-success">Continue</button>
                                           <a href="${contextPath}/" class="btn btn-secondary">Cancel</a>
                                      </div>
                                </form>
                            </c:when>
                            <c:when test="${page eq 'change'}">
                                <form action="<c:url value='/reset/password/change' />" method="post" class="needs-validation" autocomplete="off">
                                      <input type="hidden" name="token" value="${token}" />
                                      <div class="mb-3 mt-3">
                                        <label for="newPassword" class="form-label">New Password:</label>
                                        <input type="text" class="form-control" id="newPassword" placeholder="Enter new password" name="newPassword" autocomplete="off" required>
                                       </div>
                                      <div class="mb-3 mt-3">
                                          <label for="confirmPassword" class="form-label">Confirm New Password:</label>
                                          <input type="password" class="form-control" id="confirmPassword" placeholder="Enter new password" name="confirmPassword" autocomplete="off" required>
                                      </div>
                                      <div class="mb-3">
                                           <button type="submit" class="btn btn-success">Reset Password</button>
                                      </div>
                                      <c:if test="${not empty message}">
                                         <div class="alert alert-danger">${message}</div>
                                      </c:if>
                                </form>
                            </c:when>
                            <c:when test="${page eq 'success'}">
                                <div class="alert alert-success" role="alert">${message}</div>
                                <a href="${contextPath}/" class="btn btn-success">Click here to sign in</a>
                            </c:when>
                            <c:when test="${page eq 'expired'}">
                                <div class="alert alert-danger" role="alert">${message}</div>
                                <a href="${contextPath}/" class="btn btn-success">Go to Home page</a>
                            </c:when>
                            <c:otherwise>
                                <div class="alert alert-success" role="alert">${message}</div>
                                <a href="${contextPath}/" class="btn btn-success">Go to Home page</a>
                            </c:otherwise>
                        </c:choose>
                  </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>