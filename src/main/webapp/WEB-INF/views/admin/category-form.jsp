<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${editing ? 'Sửa danh mục' : 'Thêm danh mục'}"/>
<%@ include file="../shared/view-start.jspf" %>
<section class="panel narrow">
    <h1><c:out value="${pageTitle}"/></h1>
    <c:url var="actionUrl" value="${formPath}"/>
    <form:form method="post" action="${actionUrl}" modelAttribute="categoryForm" cssClass="stack">
        <div class="field">
            <label for="name">Tên danh mục</label>
            <form:input path="name" id="name" maxlength="50" required="required" aria-describedby="name-error"/>
            <form:errors path="name" id="name-error" cssClass="field-error"/>
        </div>
        <div class="field">
            <label for="imageUrl">URL hình ảnh</label>
            <form:input path="imageUrl" id="imageUrl" type="url" maxlength="500" aria-describedby="image-error"/>
            <form:errors path="imageUrl" id="image-error" cssClass="field-error"/>
        </div>
        <div class="field">
            <label for="active">Trạng thái</label>
            <form:select path="active" id="active" aria-describedby="active-error">
                <form:option value="true" label="Hoạt động"/>
                <form:option value="false" label="Ẩn"/>
            </form:select>
            <form:errors path="active" id="active-error" cssClass="field-error"/>
        </div>
        <div class="actions">
            <button type="submit">Lưu danh mục</button>
            <a class="button quiet" href="<c:url value='/admin/categories'/>">Hủy</a>
        </div>
    </form:form>
</section>
<%@ include file="../shared/view-end.jspf" %>
