<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Xác nhận xóa"/>
<%@ include file="../shared/view-start.jspf" %>
<section class="panel narrow">
    <h1>Xác nhận xóa</h1>
    <p>Bạn có muốn xóa <strong><c:out value="${itemName}"/></strong>?</p>
    <form method="post" action="<c:url value='${deletePath}'/>" class="actions">
        <%@ include file="../shared/csrf.jspf" %>
        <button class="danger" type="submit">Xác nhận xóa</button>
        <a class="button quiet" href="<c:url value='${listPath}'/>">Hủy</a>
    </form>
</section>
<%@ include file="../shared/view-end.jspf" %>
