<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Danh mục"/>
<%@ include file="../../shared/view-start.jspf" %>
<c:url var="searchUrl" value="${searchPath}"/>
<section class="panel">
    <div class="section-heading">
        <h1>Danh mục</h1>
        <a class="button" href="<c:url value='/admin/categories/add'/>">Thêm danh mục</a>
    </div>
    <form method="get" action="<c:out value='${searchUrl}'/>" class="search" role="search">
        <div class="field">
            <label for="name">Tìm theo tên danh mục</label>
            <input type="search" id="name" name="name" maxlength="100"
                   placeholder="Nhập từ khóa để tìm" value="<c:out value='${name}'/>">
        </div>
        <input type="hidden" name="size" value="<c:out value='${categoryPage.size}'/>">
        <button type="submit">Tìm kiếm</button>
        <c:if test="${not empty name}">
            <c:url var="resetUrl" value="${searchPath}">
                <c:param name="size" value="${categoryPage.size}"/>
            </c:url>
            <a class="button quiet" href="<c:out value='${resetUrl}'/>">Xóa bộ lọc</a>
        </c:if>
    </form>
    <c:choose>
        <c:when test="${empty categoryPage.content}"><p>Không tìm thấy danh mục.</p></c:when>
        <c:otherwise>
            <div class="table-scroll" role="region" aria-label="Bảng danh mục" tabindex="0">
                <table>
                    <thead><tr><th scope="col">STT</th><th scope="col">URL hình ảnh</th><th scope="col">Tên danh mục</th>
                        <th scope="col">Trạng thái</th><th scope="col">Thao tác</th></tr></thead>
                    <tbody>
                    <c:forEach var="category" items="${categoryPage.content}" varStatus="row">
                        <tr>
                            <td><c:out value="${categoryPage.pageable.offset + row.count}"/></td>
                            <td class="image-url"><c:out value="${empty category.imageUrl ? '—' : category.imageUrl}"/></td>
                            <th scope="row"><c:out value="${category.name}"/></th>
                            <td><c:out value="${category.active ? 'Hoạt động' : 'Ẩn'}"/></td>
                            <td>
                                <div class="actions">
                                    <a class="button quiet" href="<c:url value='/admin/categories/edit/${category.id}'/>"
                                       aria-label="Sửa danh mục <c:out value='${category.name}'/>">Sửa</a>
                                    <a class="button remove" href="<c:url value='/admin/categories/delete/${category.id}'/>"
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
    <form method="get" action="<c:out value='${searchUrl}'/>" class="actions page-size">
        <input type="hidden" name="name" value="<c:out value='${name}'/>">
        <label for="size">Số dòng mỗi trang</label>
        <select id="size" name="size">
            <c:forEach var="option" items="${pageSizes}">
                <option value="${option}" ${categoryPage.size eq option ? 'selected' : ''}><c:out value="${option}"/></option>
            </c:forEach>
        </select>
        <button class="quiet" type="submit">Áp dụng</button>
    </form>
    <nav class="pagination" aria-label="Phân trang kết quả">
        <span><c:out value="${categoryPage.totalElements}"/> kết quả</span>
        <c:if test="${categoryPage.totalPages gt 1}">
            <div class="actions">
                <c:forEach var="pageNumber" items="${pageNumbers}">
                    <c:choose>
                        <c:when test="${pageNumber eq categoryPage.number + 1}">
                            <span class="button current-page" aria-current="page"><c:out value="${pageNumber}"/></span>
                        </c:when>
                        <c:otherwise>
                            <c:url var="pageUrl" value="${searchPath}">
                                <c:param name="name" value="${name}"/>
                                <c:param name="size" value="${categoryPage.size}"/>
                                <c:param name="page" value="${pageNumber}"/>
                            </c:url>
                            <a class="button quiet" href="<c:out value='${pageUrl}'/>"
                               aria-label="Trang ${pageNumber}"><c:out value="${pageNumber}"/></a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
        </c:if>
    </nav>
</section>
<%@ include file="../../shared/view-end.jspf" %>
