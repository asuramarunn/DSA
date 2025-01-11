# Minh họa cấu trúc dữ liệu cuối kỳ

## 1. Minh họa bài toán cây ATM

### Bài toán:

Mỗi cây ATM được cung cấp một số lượng nhất định các loại mệnh giá tiền giấy. Khi khách hàng yêu cầu rút tiền, máy cần xử lý sao cho:

- Tổng số tiền đúng với số tiền khách hàng yêu cầu.
- Số lượng tờ tiền được sử dụng là ít nhất.
- Phân bổ tiền giấy trong phạm vi giới hạn số lượng hiện có tại cây ATM.

### Thuật toán tham lam:

Để giải bài toán này, ta có thể sử dụng thuật toán tham lam (Greedy). Các bước thực hiện:

1. Sắp xếp các mệnh giá tiền theo thứ tự giảm dần.
2. Duyệt qua từng mệnh giá:

- Tính số lượng tờ tiền cần lấy ở mệnh giá đó: số tờ = S / mệnh giá.
- Trừ số tiền tương ứng với mệnh giá đã sử dụng khỏi S.

3. Tiếp tục cho đến khi S=0.

### Yêu cầu cài đặt:

- Hãy đảm bảo rằng máy tính đã cài đặt JDK và Java trong máy. Nếu chưa cài đặt, hãy tải về ở trang chủ [JDK](https://www.oracle.com/java/technologies/downloads/) và [Java](https://www.java.com/download/ie_manual.jsp).

- Cài đặt [Netbean](https://netbeans.apache.org/front/main/download/).

- Cài đặt một SQL server. Ở đây, tôi sử dụng [Xampp](https://www.apachefriends.org/download.html). Các bạn có thể sử dụng các SQL server khác nhưng cấu hình sẽ bị thay đổi.

### Các bước cài đặt

Bước 1: Tạo Database

Trước tiên ta cần tạo một Database tên là Bank. Trong database tạo 3 bảng: bank_transaction, currency, open_account.

Bảng bank_transaction lưu thông tin của các giao dịch. Cấu trúc của bảng như sau:

| bank_transaction              |                |
| ----------------------------- | -------------- |
| bank_transaction_id           | INT(11) PK AI  |
| bank_transactiondate          | Date NN        |
| bank_transactionaccountnumber | INT(11) NN     |
| savings_account_amount        | INT(15) NN     |
| checking_account_amount       | INT(15) NN     |
| bank_transaction              | VARCHAR(10) NN |

Bảng currency lưu trữ thông tin của số lượng các đồng mệnh giá trong cây ATM.

| currency     |            |
| ------------ | ---------- |
| denomination | INT(6) PK  |
| quantity     | INT(11) NN |

Bảng open_account lưu trữ thông tin tài khoản người sử dụng.
| open_account | |
| ----------------------------- | -------------- |
| id_number | INT(11) PK AI |
| account_number | INT(16) UNIQUE NN |
| date_opened | Date NN |
| username | VARCHAR(45) NN |
| userpassword | VARCHAR(45) NN |
| user_pin | INT(11) NN |
| fullname | VARCHAR(45) PK AI |
| identification_number | VARCHAR(12) NN |
| birthdate | Date NN |
| gender | VARCHAR(6) NN |
| sercurity_question | VARCHAR(45) NN |
| answer | VARCHAR(45) NN |
| sa_balance | INT(15) NN |
| ca_balance | INT(15) NN |

Bước 2: Git clone repository

```
git clone https://github.com/asuramarunn/DSA.git
```

Bước 3: Vào Netbean và truy cập project BankingSystem. Kết nối với database và chỉnh sửa phần cấu hình trong các đoạn code:
Ví dụ

```
Class.forName("com.mysql.cj.jdbc.Driver");
String url = "jdbc:mysql://localhost:3306/bank?zeroDateTimeBehavior=CONVERT_TO_NULL";
String sqlUsername = "root";
String sqlPassword = "";
```

Bước 4: Chạy chương trình. Vào phần rút tiền và test.

## Bài toán tìm đường:
### Mô tả bài toán:
Bài toán tìm đường đi ngắn nhất là một trong những bài toán quan trọng trong lý thuyết đồ thị và được ứng dụng rộng rãi trong thực tế. Mục tiêu là tìm đường đi ngắn nhất từ một nút bắt đầu (source) đến một nút kết thúc (destination) trong đồ thị có trọng số. Trọng số của cạnh biểu thị chi phí hoặc độ dài của đoạn đường giữa hai nút.

Ứng dụng cung cấp các thuật toán phổ biến để giải quyết bài toán này:

- Thuật toán Dijkstra: Tìm đường đi ngắn nhất từ một nút đến tất cả các nút khác trong đồ thị không chứa cạnh trọng số âm.
- Thuật toán A*: Kết hợp giữa Dijkstra và tìm kiếm theo heuristic, thích hợp cho các bài toán có không gian lớn và cần tìm kiếm hiệu quả.
- Greedy BFS (Tìm kiếm tham lam): Sử dụng heuristic để tìm kiếm nhanh chóng, nhưng không đảm bảo tìm được đường đi ngắn nhất trong mọi trường hợp.

Kết quả của thuật toán sẽ bao gồm:
- Đường đi ngắn nhất từ nút bắt đầu đến nút kết thúc (nếu tồn tại).
- Đường đi được hiển thị trực quan trên bảng đồ thị.

### Chức năng:
- Thêm nút: Nhấp chuột trái vào bảng để thêm một nút mới.
- Tạo cạnh: Kết nối hai nút bằng một cạnh và thiết lập trọng số cho cạnh.
- Chọn nút bắt đầu và kết thúc: Đặt nút bắt đầu và nút kết thúc cho các thuật toán tìm đường đi.
- Xóa nút: Xóa một nút bất kỳ khỏi đồ thị.
- Chạy thuật toán: Thực thi các thuật toán tìm đường đi ngắn nhất (Dijkstra, A*, Greedy BFS).
Xóa đồ thị: Xóa toàn bộ các nút và cạnh trên đồ thị.

### Hướng dẫn sử dụng:
Bước 1: Thêm nút

- Mở ứng dụng.
- Nhấp chuột trái vào bất kỳ vị trí nào trên bảng đồ thị.
- Một nút mới sẽ được thêm tại vị trí bạn nhấp chuột.

Bước 2:Tạo cạnh
- Nhấp chuột trái vào một nút để chọn làm nút bắt đầu.
- Kéo tới một nút khác để tạo cạnh giữa hai nút.
- Một hộp thoại sẽ xuất hiện, cho phép bạn nhập trọng số cho cạnh.
- Nhập trọng số và nhấn "OK" để hoàn tất.

Bước 3: Chọn nút bắt đầu và kết thúc
- Nhấp chuột trái vào một nút để chọn làm nút bắt đầu. Thông báo sẽ hiển thị trong khu vực trạng thái.
- Nhấp chuột phải vào một nút để chọn làm nút kết thúc. Thông báo cũng sẽ hiển thị trong khu vực trạng thái.

Bước 4: Xóa nút
- Nhấp đúp chuột trái vào một nút để xóa nó khỏi đồ thị.
- Thông báo xác nhận sẽ hiển thị trong khu vực trạng thái.

Bước 5. Chạy thuật toán
- Chọn một thuật toán trong danh sách thả xuống, gồm Dijkstra, A*, hoặc Greedy BFS.
- Nhấn nút "Run" để thực thi thuật toán tìm đường đi ngắn nhất.
- Kết quả sẽ được hiển thị trong khu vực trạng thái và đường đi ngắn nhất sẽ được vẽ trên bảng đồ thị.

Bước 6: Xóa đồ thị
- Nhấn nút "Clear Graph" để xóa toàn bộ các nút và cạnh trên đồ thị.
- Một thông báo xác nhận sẽ hiển thị trong khu vực trạng thái.
