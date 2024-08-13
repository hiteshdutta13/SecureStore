<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <c:set var="contextPath" value="${pageContext.request.contextPath}" scope="session"/>
  <title>Secure Store</title>
  <link rel="stylesheet" href="${contextPath}/fontawesome/css/all.css">
  <link rel="stylesheet" href="${contextPath}/css/bootstrap.min.css">
  <link rel="stylesheet" href="${contextPath}/css/thirdparty/sweetalert2.min.css">
  <link rel="stylesheet" href="${contextPath}/css/app.css">
  <link rel="shortcut icon" href="${contextPath}/images/secure-store-icon.webp" />
  <script src="${contextPath}/js/bootstrap.bundle.min.js"></script>
  <script src="${contextPath}/js/jquery.min.js"></script>
  <script src="${contextPath}/js/ajax.handler.min.js"></script>
  <script src="${contextPath}/js/thirdparty/pdf.min.js"></script>
  <script src="${contextPath}/js/thirdparty/mammoth.browser.min.js"></script>
  <script src="${contextPath}/js/thirdparty/xlsx.core.min.js"></script>
</head>
<body class="bg-light bg-gradient">
    <div class="container" id="container" data-context="${contextPath}" data-page="${page}">
        <nav class="navbar navbar-expand-sm">
          <div class="container-fluid">
            <a class="navbar-brand fw-bold" href="${contextPath}/drive">
              <img src="${contextPath}/images/secure-store-icon.webp" alt="Secure Store" style="width:50px;" /> <span class="text-success">Secure</span> <span class="text-dark">Store</span>
            </a>
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0 profile-menu">
                <li class="nav-item dropdown d-block d-sm-none">
                     Hi, ${user.firstName}
                </li>
                <li class="nav-item dropdown d-none d-sm-block">
                  <a class="nav-link text-dark dropdown-toggle" href="javascript:;" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                       Hi, ${user.firstName}
                  </a>
                  <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <li><a class="dropdown-item" href="${contextPath}/drive/settings"><i class="fas fa-cog fa-fw"></i> Settings</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="${contextPath}/logout"><i class="fas fa-sign-out-alt fa-fw"></i> Logout</a></li>
                  </ul>
                </li>
             </ul>
          </div>
        </nav>
        <div class="row">
          <div class="col-sm-2 col-md-2 col-lg-2 col-xl-2 col-xxl-2">
               <c:if test="${page eq 'default'}">
                   <div class="btn-group">
                     <button type="button" class="btn btn-outline-success dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                       <i class="fas fa-plus"></i> New
                     </button>
                     <ul class="dropdown-menu">
                       <li><button type="button" class="dropdown-item btnNewFolder" data-bs-toggle="modal" data-bs-target="#createFolderModal"><i class="fa fa-folder-plus text-success"></i> New Folder</button></li>
                       <li><button type="button" class="dropdown-item btnNewFile" data-bs-toggle="modal" data-bs-target="#uploadFileModal"><i class="fa fa-file-upload text-success"></i> File Upload</button></li>
                     </ul>
                   </div>
                   <hr>
               </c:if>
               <ul class="list-group list-group-flush mb-2">
                 <li class="list-group-item rounded-5 ${page eq 'default' ? 'active':''}"><a class="text-decoration-none text-dark" href="${contextPath}/drive"><i class="fa fa-hard-drive text-success"></i> My Drive</a></li>
                 <li class="list-group-item rounded-5 ${page eq 'settings' ? 'active':''}"><a class="text-decoration-none text-dark" href="${contextPath}/drive/settings"><i class="fa fa-cog fa-fw text-success"></i> Settings</a></li>
                 <li class="list-group-item rounded-5 ${page eq 'bin' ? 'active':''}"><a class="text-decoration-none text-dark" href="${contextPath}/drive/bin"><i class="fa-solid fa-trash-can text-success"></i> Bin</a></li>
                 <li class="list-group-item rounded-5 d-none d-sm-block ${page eq 'upgrade' ? 'active':''}"><a class="text-decoration-none text-dark" href="${contextPath}/drive/upgrade"><i class="fa-solid fa-cloud text-success"></i> Upgrade</a></li>
                 <li class="list-group-item rounded-5 d-block d-sm-none"><a class="text-decoration-none text-dark" href="${contextPath}/logout"><i class="fas fa-sign-out-alt fa-fw text-success"></i> Logout</a></li>
               </ul>
               <div class="d-none d-sm-block">
                   <hr/>
                   Storage
                   <div class="progress" role="progressbar" aria-label="Success example" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">
                       <div class="progress-bar bg-success" style="width: 25%">25%</div>
                   </div>
               </div>
          </div>
          <div class="col-sm-10 col-md-10 col-lg-10 col-xl-10 col-xxl-10">
              <div class="card">
                <div class="card-header fw-bold">
                    <c:choose>
                        <c:when test="${page eq 'upgrade'}">
                            <i class="fa-solid fa-cloud text-success"></i> Upgrade
                        </c:when>
                        <c:when test="${page eq 'bin'}">
                            <i class="fa-solid fa-trash-can text-success"></i> Bin
                        </c:when>
                        <c:when test="${page eq 'settings'}">
                            <i class="fa fa-cog fa-fw text-success"></i> Settings
                        </c:when>
                        <c:otherwise>
                            <i class="fa fa-hard-drive text-success"></i> My Drive
                            <div class="float-end d-none d-sm-block">
                                <button class="btn btnView btn-xs" title="List View" data-value="list"><i class="fa fa-bars"></i></button>
                                <button class="btn btnView btn-xs active" title="Grid View" data-value="grid"><i class="fa fa-th-large"></i></button>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <c:choose>
                   <c:when test="${page eq 'upgrade'}">
                        <div aria-label="breadcrumb" class="row mx-0 px-2">
                          <ol class="breadcrumb border-bottom p-2">Upgrade</ol>
                        </div>
                        <div class="card-body p-3"><jsp:include page="pricing.jsp"/></div>
                   </c:when>
                   <c:when test="${page eq 'bin'}">
                        <div aria-label="breadcrumb" class="row mx-0 px-2">
                          <ol class="breadcrumb border-bottom p-2">Bin</ol>
                        </div>
                        <div class="card-body p-3"><jsp:include page="bin.jsp"/></div>
                   </c:when>
                   <c:when test="${page eq 'settings'}">
                        <div aria-label="breadcrumb" class="row mx-0 px-2">
                          <ol class="breadcrumb border-bottom p-2">Settings</ol>
                        </div>
                        <div class="card-body p-3"><jsp:include page="settings.jsp"/></div>
                   </c:when>
                   <c:otherwise>
                        <div aria-label="breadcrumb" class="row mx-0 px-2">
                          <ol class="breadcrumb border-bottom p-2"><!--Dynamically --></ol>
                        </div>
                        <div class="card-body p-3 driveContent"><!-- Dynamically will be created --> </div>
                    </c:otherwise>
                </c:choose>
              </div>
          </div>
        </div>
        <jsp:include page="footer.jsp"/>
    </div>
    <div class="modal fade" id="createFolderModal" tabindex="-1" aria-labelledby="createFolderModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title text-ellipsis" id="createFolderModalLabel">Create Folder</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
              <div class="mb-3 needs-validation">
                <label for="folder-name" class="col-form-label">Folder Name:</label>
                <input type="text" class="form-control border-dark" id="folder-name" required />
                <div class="invalid-feedback">Please enter folder name.</div>
              </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary btnCancelFolder" data-bs-dismiss="modal">Cancel</button>
            <button type="button" class="btn btn-success btnCreateFolder">Create</button>
          </div>
        </div>
      </div>
    </div>
    <div class="modal fade" id="uploadFileModal" tabindex="-1" aria-labelledby="uploadFileModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title text-ellipsis" id="uploadFileModalLabel">Upload File</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
              <div class="mb-3 needs-validation pt-3">
                <input type="file" class="form-control border-dark" id="fileSelected" accept=".jpg,.jpeg,.png,.gif,.pdf,.doc,.docx,.txt,.xls,.xlsx" required />
                <div class="invalid-feedback">Please choose a file or image.</div>
              </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary btnCancelFile" data-bs-dismiss="modal">Cancel</button>
            <button type="button" class="btn btn-success btnUploadFile">Upload</button>
          </div>
        </div>
      </div>
    </div>
    <div class="modal fade" id="fileViewModal" tabindex="-1" aria-labelledby="fileViewModalLabel" data-bs-backdrop="static" data-bs-keyboard="false" aria-hidden="true">
      <div class="modal-dialog modal-xl modal-dialog-centered modal-dialog-scrollable">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title text-ellipsis" id="fileViewModalLabel"><!-- Dynamically --></h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body"><!-- Dynamically --></div>
        </div>
      </div>
    </div>
    <div class="modal fade" id="fileShareModal" tabindex="-1" aria-labelledby="fileShareModalLabel" data-bs-backdrop="static" data-bs-keyboard="false" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title text-ellipsis" id="fileShareModalLabel"><!-- Dynamically --> </h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body"><!-- Dynamically --></div>
          <div class="modal-footer">
              <button type="button" class="btn btn-secondary btnCancelShare" data-bs-dismiss="modal">Cancel</button>
              <button type="button" class="btn btn-success btnShareFile">Share</button>
          </div>
        </div>
      </div>
    </div>
</body>
<script src="${contextPath}/js/thirdparty/sweetalert2.min.js"></script>
<script src="${contextPath}/js/app.min.js"></script>
</html>