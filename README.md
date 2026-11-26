# BT5 · Lập trình Web

Ứng dụng Spring Boot 4.1.3 dùng Spring MVC, JSP/JSTL, SiteMesh 3, Spring Security và Spring Data JPA. Dữ liệu được lưu trên đĩa bằng H2. Tomcat nhúng chạy từ file WAR. SiteMesh áp dụng hai layout cho trang người dùng và trang quản trị.

- Khách có thể đăng ký, đăng nhập và đăng xuất. Đăng ký công khai luôn tạo tài khoản `USER`.
- Quản trị viên có thể xem, thêm, sửa và xóa danh mục tại `/admin/categories`. Trang `/admin/categories/searchpaginated` nhận `name`, `page` và `size`. Kết quả xếp theo tên và mặc định hiển thị 3 dòng mỗi trang. Có thể chọn 3, 5, 10, 15 hoặc 20 dòng.
- Quản trị viên có thể xem, thêm, sửa và xóa người dùng tại `/admin/users`. Tìm theo email hoặc họ tên. Khi sửa người dùng, có thể để trống mật khẩu để giữ mật khẩu hiện tại.
- Trang người dùng hiển thị 10 dòng mỗi trang. Cả hai trang giữ từ khóa khi chuyển trang. Các thao tác xóa yêu cầu xác nhận.

## Chạy trên Windows

Cài JDK 21 và Maven 3.9+. Mở PowerShell trong thư mục có `pom.xml`.

```powershell
$env:BT5_ADMIN_EMAIL = "admin@example.com"
$env:BT5_ADMIN_PASSWORD = "ThayBangMatKhauCuaBan"
mvn spring-boot:run
```

Đổi giá trị mật khẩu ví dụ trước khi chạy. Mở [http://localhost:8085](http://localhost:8085) và đăng nhập bằng tài khoản admin vừa đặt. Mật khẩu dài từ 8 đến 128 ký tự.

Lần đầu chạy, ứng dụng tạo bảng `users` và `categories` trong `data/bt5.mv.db`. Biến môi trường chỉ dùng để tạo admin khi cơ sở dữ liệu chưa có tài khoản admin. Lần sau hãy dùng lại tài khoản đã tạo. Nếu cổng 8085 đang được dùng, đặt `$env:SERVER_PORT = "8086"` trước khi chạy.

Để đóng gói, chạy `mvn clean verify`. File `target/bt5.war` chạy bằng `java -jar target/bt5.war`. Trên Windows, đường dẫn WAR có dấu cách có thể khiến Tomcat ghi cảnh báo khi quét các thư viện nhúng. Chạy qua Maven tránh các cảnh báo này.

## Kiểm tra nhanh

1. Đăng ký một tài khoản mới và đăng nhập. Thử mở `/admin/users` để kiểm tra quyền truy cập, rồi đăng xuất.
2. Đăng nhập bằng admin. Thêm, sửa và xóa một danh mục và một người dùng.
3. Tạo ít nhất 4 danh mục cùng từ khóa. Tìm kiếm, chuyển sang trang 2 bằng liên kết số trang rồi đổi số dòng mỗi trang. Kiểm tra từ khóa vẫn được giữ.
4. Tìm ít nhất 11 người dùng trùng từ khóa trong email hoặc họ tên và chuyển sang trang 2.
