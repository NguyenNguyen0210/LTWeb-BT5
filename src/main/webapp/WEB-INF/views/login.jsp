<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Đăng nhập"/>
<%@ include file="shared/header.jspf" %>
<section class="panel narrow">
    <h1>Đăng nhập</h1>
    <c:if test="${paramValues.error ne null}"><p class="error" role="alert">Email hoặc mật khẩu không đúng.</p></c:if>
    <c:if test="${paramValues.registered ne null}"><p class="notice" role="status">Bạn đã đăng ký thành công.</p></c:if>
    <c:if test="${paramValues.logout ne null}"><p class="notice" role="status">Bạn đã đăng xuất.</p></c:if>
    <form method="post" action="<c:url value='/login'/>" class="stack">
        <%@ include file="shared/csrf.jspf" %>
        <div class="field">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" required maxlength="100" autocomplete="username">
        </div>
        <div class="field">
            <label for="password">Mật khẩu</label>
            <input type="password" id="password" name="password" required maxlength="128" autocomplete="current-password">
        </div>
        <div class="actions"><button type="submit">Đăng nhập</button></div>
    </form>
    <p>Chưa có tài khoản? <a href="<c:url value='/register'/>">Đăng ký</a></p>
</section>
<%@ include file="shared/footer.jspf" %>
