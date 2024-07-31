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
  <link rel="stylesheet" href="${contextPath}/css/app.css">
  <link rel="shortcut icon" href="${contextPath}/images/secure-store-icon.webp" />
  <script src="${contextPath}/js/bootstrap.bundle.min.js"></script>
  <script src="${contextPath}/js/jquery.min.js"></script>
  <script src="${contextPath}/js/ajax.handler.min.js"></script>
</head>
<body class="bg-light bg-gradient">
    <div class="container" id="container" data-context="${contextPath}">
        <nav class="navbar navbar-expand-sm">
          <div class="container-fluid">
            <a class="navbar-brand fw-bold" href="${contextPath}">
              <img src="${contextPath}/images/secure-store-icon.webp" alt="Secure Store" style="width:50px;" class="rounded-2"/> <span class="text-success">Secure</span> <span class="text-dark">Store</span>
            </a>
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0 profile-menu">
                <li class="nav-item dropdown">
                  <a class="nav-link text-dark dropdown-toggle" href="javascript:;" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                       Hello, ${user.firstName}
                  </a>
                  <ul class="dropdown-menu " aria-labelledby="navbarDropdown">
                    <li><a class="dropdown-item" href="${contextPath}/"><i class="fas fa-cog fa-fw"></i> Settings</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="${contextPath}/logout"><i class="fas fa-sign-out-alt fa-fw"></i> Log Out</a></li>
                  </ul>
                </li>
             </ul>
          </div>
        </nav>
        <div class="row">
          <div class="col-sm-2 col-md-2 col-lg-2 col-xl-2 col-xxl-2">
               <div class="btn-group">
                 <button type="button" class="btn btn-outline-success dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                   <i class="fas fa-plus"></i> New
                 </button>
                 <ul class="dropdown-menu">
                   <li><button type="button" class="dropdown-item" data-bs-toggle="modal" data-bs-target="#createFolderModal"><i class="fa fa-folder-plus text-success"></i> New Folder</button></li>
                   <li><button type="button" class="dropdown-item" data-bs-toggle="modal" data-bs-target="#uploadFileModal"><i class="fa fa-file-upload text-success"></i> File Upload</button></li>
                 </ul>
               </div>
               <hr>
               <ul class="list-group list-group-flush">
                 <li class="list-group-item rounded-5 active"><a class="text-decoration-none text-dark" href="${contextPath}/"><i class="fa fa-hard-drive text-success"></i> My Drive</a></li>
               </ul>
          </div>
          <div class="col-sm-10 col-md-10 col-lg-10 col-xl-10 col-xxl-10">
              <div class="card">
                <div class="card-header fw-bold"><i class="fa fa-hard-drive text-success"></i> My Drive
                    <div class="float-end">
                        <button class="btn btn-sm btnView" title="List View" data-value="list"><i class="fa fa-bars"></i></button>
                        <button class="btn btn-sm btnView active" title="Grid View" data-value="grid"><i class="fa fa-th-large"></i></button>
                    </div>
                </div>
                <div aria-label="breadcrumb" class="row mx-0 px-2">
                  <ol class="breadcrumb border-bottom p-2"><!--Dynamically --></ol>
                </div>
                <div class="card-body p-3 driveContent"><!-- Dynamically will be created --> </div>
              </div>
          </div>
        </div>
        <div class="toast align-items-center float-end" id="toastMessage" role="alert" aria-live="assertive" aria-atomic="true">
          <div class="d-flex">
            <div class="toast-body">
                <i class="fa-regular fa-circle-check text-success"></i> <span class="toastMessage">Hello, world! This is a toast message.</spa>
           </div>
            <button type="button" class="btn-close me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
          </div>
        </div>
        <jsp:include page="footer.jsp"/>
    </div>
    <div class="modal fade" id="createFolderModal" tabindex="-1" aria-labelledby="createFolderModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="createFolderModalLabel">New Folder</h5>
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
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="uploadFileModalLabel">Upload File</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
              <div class="mb-3 needs-validation pt-3">
                <input type="file" class="form-control border-dark" id="fileSelected" required />
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
    <div class="modal fade" id="imageViewModal" tabindex="-1" aria-labelledby="imageViewModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="imageViewModalLabel">Upload File</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
              <img src="..." class="img-fluid" alt="..."/>
          </div>
        </div>
      </div>
    </div>
</body>
<script src="${contextPath}/js/app.min.js"></script>
</html>