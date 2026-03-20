# 📚 Modern Library Manager

Ứng dụng quản lý thư viện hiện đại được xây dựng bằng **JavaFX**, hỗ trợ theo dõi sách, độc giả, phiếu mượn/trả và báo cáo thống kê.

---

## 🖥️ Giao diện

Ứng dụng có giao diện trực quan, bao gồm các thành phần:

- **Sidebar** – Điều hướng giữa các màn hình chính
- **Dashboard** – Thống kê tổng quan: tổng số sách, độc giả, đang mượn, quá hạn
- **Book Inventory** – Quản lý danh mục sách
- **Reader Records** – Quản lý thông tin độc giả
- **Loan/Return** – Xử lý phiếu mượn và trả sách
- **Reports** – Báo cáo thống kê

---

## ⚙️ Yêu cầu hệ thống

| Thành phần | Phiên bản tối thiểu |
|---|---|
| Java JDK | 17 trở lên |
| JavaFX | 17.0.6 |
| Maven | 3.8+ |

---

## 🚀 Hướng dẫn chạy

### 1. Clone dự án

```bash
git clone https://github.com/phd7744/library-management-project.git
cd library-management-project
```

### 2. Chạy bằng Maven

```bash
mvn clean javafx:run
```

> Lệnh này sẽ tự động tải các dependency và khởi chạy ứng dụng.

---

## 📁 Cấu trúc dự án

```
library-management-project/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/project_oop/
│       │       ├── MainApp.java              # Điểm khởi chạy ứng dụng
│       │       └── controller/
│       │           └── MainController.java   # Controller chính
│       └── resources/
│           └── com/example/project_oop/
│               ├── css/
│               │   └── style.css             # File CSS toàn cục
│               ├── fxml/
│               │   └── main-view.fxml        # Giao diện chính
│               └── img/
│                   └── avatar.jpg            # Ảnh đại diện mặc định
├── pom.xml                                   # Cấu hình Maven
└── README.md
```

---

## 📦 Công nghệ sử dụng

- **JavaFX 17** – Framework giao diện người dùng
- **FXML** – Định nghĩa giao diện dạng XML
- **CSS (JavaFX)** – Tuỳ chỉnh giao diện
- **Ikonli** – Thư viện icon cho JavaFX
- **JUnit 5** – Unit testing
- **Maven** – Quản lý build và dependency

