<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Danh mục"/>
<c:set var="searchLabel" value="Tìm theo tên danh mục"/>
<%@ include file="../shared/header.jspf" %>
<section class="panel">
    <div class="section-heading">
        <h1>Danh mục</h1>
        <a class="button" href="<c:url value='/admin/categories/new'/>">Thêm danh mục</a>
    </div>
    <%@ include file="../shared/search.jspf" %>
    <c:choose>
        <c:when test="${empty results.content}"><p>Không tìm thấy danh mục.</p></c:when>
        <c:otherwise>
            <div class="table-scroll" role="region" aria-label="Bảng danh mục" tabindex="0">
                <table>
                    <thead><tr><th scope="col">ID</th><th scope="col">Tên danh mục</th><th scope="col">URL hình ảnh</th>
                        <th scope="col">Trạng thái</th><th scope="col">Thao tác</th></tr></thead>
                    <tbody>
                    <c:forEach var="category" items="${results.content}">
                        <tr>
                            <td><c:out value="${category.id}"/></td>
                            <th scope="row"><c:out value="${category.name}"/></th>
                            <td class="image-url"><c:out value="${empty category.imageUrl ? '—' : category.imageUrl}"/></td>
                            <td><c:out value="${category.active ? 'Hoạt động' : 'Ẩn'}"/></td>
                            <td>
                                <div class="actions">
                                    <a class="button quiet" href="<c:url value='/admin/categories/${category.id}/edit'/>"
                                       aria-label="Sửa danh mục <c:out value='${category.name}'/>">Sửa</a>
                                    <a class="button remove" href="<c:url value='/admin/categories/${category.id}/delete'/>"
                                       aria-label="Xóa danh mục <c:out value='${category.name}'/>">Xóa</a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
    <%@ include file="../shared/pagination.jspf" %>
</section>
<%@ include file="../shared/footer.jspf" %>
