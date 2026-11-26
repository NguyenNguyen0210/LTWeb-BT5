<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Trang chủ"/>
<%@ include file="shared/header.jspf" %>
<section class="panel narrow">
    <h1>Bài tập 5</h1>
    <c:choose>
        <c:when test="${empty pageContext.request.userPrincipal}">
            <p>Đăng nhập hoặc tạo tài khoản để bắt đầu.</p>
            <div class="actions">
                <a class="button" href="<c:url value='/login'/>">Đăng nhập</a>
                <a class="button quiet" href="<c:url value='/register'/>">Đăng ký</a>
            </div>
        </c:when>
        <c:otherwise>
            <p>Bạn đã đăng nhập bằng <strong><c:out value="${pageContext.request.userPrincipal.name}"/></strong>.</p>
            <c:if test="${pageContext.request.isUserInRole('ADMIN')}">
                <div class="actions">
                    <a class="button" href="<c:url value='/admin/categories'/>">Quản lý danh mục</a>
                    <a class="button quiet" href="<c:url value='/admin/users'/>">Quản lý người dùng</a>
                </div>
            </c:if>
        </c:otherwise>
    </c:choose>
</section>
<%@ include file="shared/footer.jspf" %>
