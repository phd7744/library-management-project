# 📚 Modern Library Manager

> Ứng dụng quản lý thư viện hiện đại xây dựng bằng **JavaFX 17** — hỗ trợ quản lý sách, độc giả, phiếu mượn/trả và báo cáo thống kê theo thời gian thực.

<p align="center">
  <img src="https://img.shields.io/badge/Java-23-orange?logo=java" alt="Java 23"/>
  <img src="https://img.shields.io/badge/JavaFX-17.0.6-blue?logo=openjdk" alt="JavaFX 17"/>
  <img src="https://img.shields.io/badge/Maven-3.8+-red?logo=apachemaven" alt="Maven"/>
  <img src="https://img.shields.io/badge/MariaDB-10.4-teal?logo=mariadb" alt="MariaDB"/>
  <img src="https://img.shields.io/badge/license-MIT-green" alt="License"/>
</p>

---

## 📋 Mục lục

- [Tính năng](#-tính-năng)
- [Yêu cầu hệ thống](#️-yêu-cầu-hệ-thống)
- [Cài đặt & Chạy dự án](#-cài-đặt--chạy-dự-án)
- [Thiết lập cơ sở dữ liệu](#-thiết-lập-cơ-sở-dữ-liệu)
- [Cấu trúc dự án](#-cấu-trúc-dự-án)
- [Sơ đồ CSDL](#-sơ-đồ-cơ-sở-dữ-liệu)
- [Hướng dẫn đóng góp](#-hướng-dẫn-đóng-góp)
- [Quy ước code](#-quy-ước-code)
- [Thành viên nhóm](#-thành-viên-nhóm)

---

## ✨ Tính năng

| Module | Mô tả |
|---|---|
| 🔐 **Đăng nhập** | Xác thực tài khoản theo vai trò (Admin / Nhân viên / Độc giả) |
| 📊 **Dashboard** | Thống kê tổng quan: tổng số sách, độc giả, đang mượn, quá hạn |
| 📖 **Quản lý Sách** | Thêm, sửa, xóa, tìm kiếm sách theo danh mục, tác giả, NXB |
| 👥 **Quản lý Độc giả** | Thêm, sửa, xóa thông tin độc giả; theo dõi công nợ |
| 🔄 **Mượn / Trả Sách** | Lập phiếu mượn, xử lý trả sách, tính phí phạt quá hạn |
| 📈 **Báo cáo** | Thống kê theo thời gian, xuất báo cáo |

---

## ⚙️ Yêu cầu hệ thống

| Thành phần | Phiên bản |
|---|---|
| Java JDK | **23** (khuyến nghị) hoặc 17+ |
| JavaFX | **17.0.6** (tích hợp qua Maven) |
| Apache Maven | **3.8+** |
| MariaDB / MySQL | **10.4+** / 8.0+ |
| XAMPP (tùy chọn) | Cung cấp MySQL + phpMyAdmin |

> **💡 Gợi ý IDE:** IntelliJ IDEA (Community hoặc Ultimate) để được hỗ trợ JavaFX tốt nhất.

---

## 🚀 Cài đặt & Chạy dự án

### Bước 1 — Clone repository

```bash
git clone https://github.com/phd7744/library-management-project.git
cd library-management-project
```

### Bước 2 — Thiết lập cơ sở dữ liệu

> Xem chi tiết tại mục [Thiết lập CSDL](#-thiết-lập-cơ-sở-dữ-liệu) bên dưới.

### Bước 3 — Cấu hình kết nối DB

Tìm file cấu hình kết nối database trong dự án và cập nhật thông tin phù hợp với máy của bạn:

```properties
# Ví dụ: src/main/resources/db.properties (nếu có)
db.url=jdbc:mysql://localhost:3306/librarydb
db.username=root
db.password=YOUR_PASSWORD
```

> ⚠️ **Không commit** thông tin nhạy cảm (password) lên Git. Hãy dùng biến môi trường hoặc file `.env` được thêm vào `.gitignore`.

### Bước 4 — Chạy ứng dụng

```bash
# Sử dụng Maven Wrapper (không cần cài Maven toàn cục)
./mvnw clean javafx:run        # Linux / macOS
mvnw.cmd clean javafx:run      # Windows

# Hoặc nếu đã cài Maven
mvn clean javafx:run
```

Ứng dụng sẽ khởi động và hiển thị màn hình đăng nhập.

---

## 🗄️ Thiết lập cơ sở dữ liệu

### Sử dụng XAMPP (đơn giản nhất cho nhóm)

1. Tải và cài đặt **[XAMPP](https://www.apachefriends.org/)**.
2. Khởi động **Apache** và **MySQL** trong XAMPP Control Panel.
3. Mở trình duyệt, truy cập `http://localhost/phpmyadmin`.
4. Tạo database mới tên **`librarydb`**:
   ```sql
   CREATE DATABASE librarydb CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
   ```
5. Chọn database `librarydb` → Click tab **Import** → Chọn file `database/librarydb.sql` → Click **Go**.

### Sử dụng MySQL CLI

```bash
mysql -u root -p
```
```sql
CREATE DATABASE librarydb CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE librarydb;
SOURCE /đường/dẫn/tới/database/librarydb.sql;
```

### Sơ đồ bảng (tóm tắt)

```
users ──────────┬──── employees
                └──── readers
                
books ──────────┬──── categories
                ├──── authors
                └──── publishers

borrow_receipts ──── borrow_details ──── books
        │
      readers + employees
```

---

## 📁 Cấu trúc dự án

```
library-management-project/
├── database/
│   └── librarydb.sql                   # Script tạo & seed database
├── src/
│   └── main/
│       ├── java/com/example/project_oop/
│       │   ├── MainApp.java            # Entry point ứng dụng
│       │   ├── controller/             # JavaFX Controllers
│       │   │   ├── LoginController.java
│       │   │   ├── MainController.java
│       │   │   ├── DashBoardController.java
│       │   │   ├── BookInventoryController.java
│       │   │   ├── ReaderRecordsController.java
│       │   │   ├── LoanReturnController.java
│       │   │   └── ReportController.java
│       │   ├── model/                  # Entity / POJO classes
│       │   ├── dao/                    # Data Access Objects (DB queries)
│       │   ├── service/                # Business logic layer
│       │   └── utils/                  # Các tiện ích dùng chung
│       └── resources/com/example/project_oop/
│           ├── fxml/                   # Giao diện FXML
│           ├── css/
│           │   └── style.css           # Stylesheet toàn cục
│           └── img/                    # Ảnh, icon tài nguyên
├── .gitignore
├── mvnw / mvnw.cmd                     # Maven Wrapper
├── pom.xml                             # Cấu hình Maven & dependencies
└── README.md
```

---

## 📦 Công nghệ sử dụng

| Công nghệ | Phiên bản | Mục đích |
|---|---|---|
| [JavaFX](https://openjfx.io/) | 17.0.6 | Framework giao diện người dùng |
| [FXML](https://openjfx.io/javadoc/17/javafx.fxml/javafx/fxml/doc-files/introduction_to_fxml.html) | — | Khai báo giao diện dạng XML |
| [Ikonli](https://kordamp.org/ikonli/) | 12.3.1 | Thư viện icon cho JavaFX |
| [MariaDB Connector/J](https://mariadb.com/kb/en/mariadb-connector-j/) | — | Kết nối JDBC tới database |
| [JUnit 5](https://junit.org/junit5/) | 5.10.2 | Unit Testing |
| [Maven](https://maven.apache.org/) | 3.8+ | Build tool & quản lý dependency |

---

## 🤝 Hướng dẫn đóng góp

Dự án được phát triển theo mô hình **Git Flow**. Vui lòng tuân thủ quy trình sau:

### Quy trình làm việc

```
main        ← production-ready (chỉ merge sau khi review)
        ├── feature/ten-tinh-nang   ← mỗi người tự tạo
        ├── fix/ten-loi-can-sua
        └── hotfix/...
```

### Các bước thực hiện

1. **Đồng bộ code mới nhất** trước khi bắt đầu:
   ```bash
   git checkout main
   git pull origin main
   ```

2. **Tạo nhánh mới** từ `main`:
   ```bash
   git checkout -b feature/quan-ly-sach
   ```

3. **Code, commit** với message rõ ràng:
   ```bash
   git add .
   git commit -m "feat: thêm chức năng tìm kiếm sách theo tên tác giả"
   ```

4. **Push** nhánh lên GitHub:
   ```bash
   git push origin feature/quan-ly-sach
   ```

5. Tạo **Pull Request** vào nhánh `main` trên GitHub và yêu cầu ít nhất **1 thành viên** review.

> ❌ **Không được** push thẳng vào nhánh `main`.

---

## 📝 Quy ước code

### Commit Message

Sử dụng format **Conventional Commits**:

```
<type>: <mô tả ngắn gọn bằng tiếng Việt hoặc tiếng Anh>
```

| Type | Ý nghĩa |
|---|---|
| `feat` | Thêm tính năng mới |
| `fix` | Sửa lỗi |
| `refactor` | Cải tổ code, không thay đổi behavior |
| `style` | Chỉnh sửa CSS, format code |
| `docs` | Cập nhật tài liệu, README |
| `test` | Thêm / sửa unit test |
| `chore` | Cập nhật cấu hình, dependency |

**Ví dụ:**
```
feat: thêm màn hình quản lý độc giả
fix: sửa lỗi tính phí phạt âm khi trả sớm
docs: cập nhật hướng dẫn cài đặt database
```

### Đặt tên

| Thành phần | Convention | Ví dụ |
|---|---|---|
| Class | `PascalCase` | `BookInventoryController` |
| Method / Variable | `camelCase` | `handleLogin()`, `bookTitle` |
| Constant | `UPPER_SNAKE_CASE` | `MAX_BORROW_DAYS` |
| Package | `lowercase` | `com.example.project_oop` |
| Nhánh Git | `kebab-case` | `feature/loan-return` |

---

## 👥 Thành viên nhóm

| Họ tên | MSSV | Vai trò | GitHub |
|---|---|---|---|
| *(Thêm tên)* | *(Thêm MSSV)* | *(Vai trò)* | [@username](https://github.com/) |
| *(Thêm tên)* | *(Thêm MSSV)* | *(Vai trò)* | [@username](https://github.com/) |
| *(Thêm tên)* | *(Thêm MSSV)* | *(Vai trò)* | [@username](https://github.com/) |

---

## ❓ Câu hỏi thường gặp (FAQ)

<details>
<summary><b>Lỗi "JavaFX runtime components are missing"?</b></summary>

Đảm bảo bạn chạy ứng dụng thông qua Maven (`mvn javafx:run`) chứ không phải chạy trực tiếp file `.jar` hoặc class `MainApp`. Maven plugin sẽ tự động cấu hình JavaFX module path.

</details>

<details>
<summary><b>Lỗi kết nối database "Communications link failure"?</b></summary>

- Kiểm tra XAMPP đã khởi động MySQL chưa.
- Kiểm tra lại thông tin `url`, `username`, `password` trong file cấu hình.
- Đảm bảo database `librarydb` đã được tạo và import SQL thành công.

</details>

<details>
<summary><b>Thành viên mới clone về không chạy được?</b></summary>

Hãy thực hiện đầy đủ 4 bước trong phần [Cài đặt & Chạy dự án](#-cài-đặt--chạy-dự-án), đặc biệt là **Bước 2 & 3** về cài đặt và cấu hình database.

</details>

---

<p align="center">
  Made with ❤️ by <strong>Nhóm OOP — PTIT</strong>
</p>
