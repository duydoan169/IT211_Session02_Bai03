

Phần 1 – Phân tích logic
Vai trò của HTTP Status Code:
HTTP Status Code giống như tín hiệu đèn giao thông — client 
không cần đọc nội dung response mới biết kết quả, chỉ cần nhìn 
vào status code là hiểu ngay. 200 nghĩa là thành công, 404 nghĩa 
là không tìm thấy, 500 nghĩa là server đang có vấn đề. Nếu API không 
trả về status code đúng, client phải tự đoán kết quả dựa vào nội dung 
response — rất dễ xử lý sai và tốn công viết thêm logic không cần thiết.


Tại sao trả về null thay vì 404 gây vấn đề:
Khi server trả về null kèm status 200 OK, client nghĩ request thành 
công nhưng không có dữ liệu — hoàn toàn mơ hồ, không biết đây là lỗi 
hay dữ liệu thật sự rỗng. Ứng dụng có thể bị crash vì cố gắng đọc dữ 
liệu từ null. Ngược lại nếu trả về 404 Not Found rõ ràng, client biết 
ngay tài nguyên không tồn tại và xử lý đúng — ví dụ hiển thị thông báo 
"Không tìm thấy sản phẩm" thay vì crash.


Tại sao cần Jackson Dataformat XML:
Spring Boot mặc định chỉ cài Jackson để xử lý JSON. Muốn hỗ trợ thêm XML, 
cần thêm dependency jackson-dataformat-xml vào build.gradle. Khi có thư 
viện này, Spring tự động đọc header Accept trong request — nếu client gửi 
Accept: application/json thì trả về JSON, nếu gửi Accept: application/xml 
thì trả về XML, hoàn toàn tự động mà không cần viết thêm code xử lý.