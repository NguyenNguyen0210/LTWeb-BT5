<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Người dùng"/>
<c:set var="searchLabel" value="Tìm theo email hoặc họ tên"/>
<%@ include file="../shared/header.jspf" %>
<section class="panel">
    <div class="section-heading">
        <h1>Người dùng</h1>
        <a class="button" href="<c:url value='/admin/users/new'/>">Thêm người dùng</a>
    </div>
    <%@ include file="../shared/search.jspf" %>
    <c:choose>
        <c:when test="${empty results.content}"><p>Không tìm thấy người dùng.</p></c:when>
        <c:otherwise>
            <div class="table-scroll" role="region" aria-label="Bảng người dùng" tabindex="0">
                <table>
                    <thead><tr><th scope="col">ID</th><th scope="col">Họ và tên</th><th scope="col">Email</th>
                        <th scope="col">Vai trò</th><th scope="col">Thao tác</th></tr></thead>
                    <tbody>
                    <c:forEach var="user" items="${results.content}">
                        <tr>
                            <td><c:out value="${user.id}"/></td>
                            <th scope="row"><c:out value="${user.fullName}"/></th>
                            <td><c:out value="${user.email}"/></td>
                            <td><c:out value="${user.role}"/></td>
                            <td>
                                <div class="actions">
                                    <a class="button quiet" href="<c:url value='/admin/users/${user.id}/edit'/>"
                                       aria-label="Sửa người dùng <c:out value='${user.fullName}'/>">Sửa</a>
                                    <c:if test="${user.id ne ownId}">
                                        <a class="button remove" href="<c:url value='/admin/users/${user.id}/delete'/>"
                                           aria-label="Xóa người dùng <c:out value='${user.fullName}'/>">Xóa</a>
                                    </c:if>
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
