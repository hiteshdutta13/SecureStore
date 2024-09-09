<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container">
    <div class="mt-2">
      <c:forEach var="plan" items="${plans}">
          <div class="card p-3 mb-2">
            <div class="row align-items-center">
                <div class="col-12 col-md-4">
                     <c:if test="${user.plan.id eq plan.id}">
                        <span class="badge rounded-pill bg-success">Active</span>
                    </c:if>
                    <h4 class="pt-3 text-170 text-600 text-primary-d1 letter-spacing">
                        ${plan.name}
                    </h4>
                    <div class="text-secondary-d1 text-120">
                      <span class="text-180"><i class="fa-solid fa-indian-rupee-sign"></i> ${plan.price}</span> / ${plan.pricingType}
                    </div>
                </div>
                <ul class="list-unstyled mb-0 col-12 col-md-4 text-dark-l1 text-90 text-left my-4 my-md-0">
                    <li>
                      <i class="fa fa-check text-success text-110 mr-2 mt-1"></i>
                        <span class="text-110">${plan.storage} ${plan.storageType}</span>
                    </li>
                    <c:choose>
                        <c:when test="${plan.share eq 0}">
                            <li class="mt-25">
                              <i class="fa fa-times text-danger text-110 mr-25 mt-1"></i>
                              <span class="text-110">Share file</span>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="mt-25">
                              <i class="fa fa-check text-success text-110 mr-2 mt-1"></i>
                              <span class="text-110">Share file</span>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
                <div class="col-12 col-md-4 text-center">
                    <c:if test="${plan.price gt 0 and user.plan.id ne plan.id}">
                        <a href="${contextPath}/drive/upgrade" class="f-n-hover btn btn-dark btn-raised px-4 py-25 w-75 text-600">Upgrade</a>
                    </c:if>
                </div>
            </div>
          </div>
      </c:forEach>
    </div>
</div>