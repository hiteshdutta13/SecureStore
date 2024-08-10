<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
    <div class="col-md-3 border-right">
        <div class="d-flex flex-column align-items-center text-center p-3 py-5">
            <i class="fa-regular fa-circle-user text-success fs-75"></i>
            <span class="font-weight-bold mt-3">${user.firstName}</span><span class="text-black-50">${user.email}</span><span> </span>
        </div>
    </div>
    <div class="col-md-9 border-right">
        <div class="">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h4 class="text-right">Profile Settings</h4>
            </div>
            <div class="row mt-2">
                <div class="col-md-6">
                    <label class="userFirstName">First Name</label>
                    <input type="text" class="form-control" id="userFirstName" placeholder="first name" value="${user.firstName}" disabled />
                </div>
                <div class="col-md-6">
                    <label class="userLastName">Last Name</label>
                    <input type="text" class="form-control" id="userLastName" value="${user.lastName}" placeholder="last name" disabled />
                </div>
            </div>
            <div class="row mt-2">
                <div class="col-md-6">
                    <label class="userUsername">Username</label>
                    <input type="text" class="form-control" id="userUsername" placeholder="username" value="${user.username}" disabled />
                </div>
                <div class="col-md-6">
                    <label class="userEmail">Email</label>
                    <input type="text" class="form-control" id="userEmail" value="${user.email}" placeholder="email" disabled/>
                </div>
            </div>
            <div class="row mt-2">
                <div class="col-md-3">
                    <label class="userGender">Gender</label>
                    <select class="form-select" id="userGender">
                        <c:choose>
                            <c:when test="${user.gender eq 'Male'}">
                                <option value="Male">Male</option>
                                <option value="Female">Female</option>
                                <option value="Other">Other</option>
                            </c:when>
                            <c:when test="${user.gender eq 'Female'}">
                                <option value="Female">Female</option>
                                <option value="Male">Male</option>
                                <option value="Other">Other</option>
                            </c:when>
                            <c:when test="${user.gender eq 'Other'}">
                                <option value="Other">Other</option>
                                <option value="Male">Male</option>
                                <option value="Female">Female</option>
                            </c:when>
                            <c:otherwise>
                                <option value="">Choose</option>
                                <option value="Male">Male</option>
                                <option value="Female">Female</option>
                                <option value="Other">Other</option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </div>
                <div class="col-md-3">
                    <label class="userDateOfBirth">Date of birth</label>
                    <input type="date" class="form-control" id="userDateOfBirth" value="${user.dateOfBirth}" placeholder="date of birth" />
                </div>
                <div class="col-md-4">
                    <label class="userMobileNo">Mobile No</label>
                    <input type="text" class="form-control" id="userMobileNo" placeholder="mobile no" value="${user.mobileNo}"/>
                </div>
            </div>
            <div class="mt-3">
                <button class="btn btn-success btnSaveProfile" data-id="${user.id}" type="button">Save Profile</button>
                <button class="btn btn-secondary btnChangePassword" type="button">Change Password</button>
            </div>
        </div>
    </div>
</div>
<script src="${contextPath}/js/settings.min.js"></script>