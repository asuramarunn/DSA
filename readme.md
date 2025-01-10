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
