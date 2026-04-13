USE librarydb;

-- =========================
-- EMPLOYEES (password SHA-256 cua "123456")
-- =========================
INSERT INTO employees (full_name, phone_number, shift, role, username, password, first_login, status) VALUES
('Nguyễn Văn An',   '0901111111', 'Sáng',  'ADMIN',     'admin',    '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 0, 'ACTIVE'),
('Trần Thị Bích',   '0902222222', 'Chiều', 'LIBRARIAN', 'bich.tran','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, 'ACTIVE'),
('Lê Minh Cường',   '0903333333', 'Tối',   'LIBRARIAN', 'cuong.le', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, 'ACTIVE'),
('Phạm Thị Dung',   '0904444444', 'Sáng',  'LIBRARIAN', 'dung.pham','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, 'INACTIVE');

-- =========================
-- READERS
-- =========================
INSERT INTO readers (full_name, phone_number, reader_type, debt, username, password, first_login, status) VALUES
('Nguyễn Thị Hoa',   '0911111111', 'Sinh viên',   0,      'hoa.nt',    '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, 'ACTIVE'),
('Trần Văn Bảo',     '0912222222', 'Sinh viên',   15000,  'bao.tv',    '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, 'ACTIVE'),
('Lê Thị Cẩm',       '0913333333', 'Giảng viên',  0,      'cam.lt',    '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, 'ACTIVE'),
('Phạm Quốc Dũng',   '0914444444', 'Sinh viên',   30000,  'dung.pq',   '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, 'ACTIVE'),
('Hoàng Minh Đức',   '0915555555', 'Học sinh',    0,      'duc.hm',    '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, 'ACTIVE'),
('Vũ Thị Lan',       '0916666666', 'Giảng viên',  0,      'lan.vt',    '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, 'ACTIVE'),
('Đặng Văn Giang',   '0917777777', 'Sinh viên',   50000,  'giang.dv',  '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, 'BANNED'),
('Bùi Thị Hương',    '0918888888', 'Học sinh',    0,      'huong.bt',  '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, 'ACTIVE'),
('Đỗ Minh Khoa',     '0919999999', 'Sinh viên',   0,      'khoa.dm',   '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, 'ACTIVE'),
('Ngô Thị Linh',     '0920000000', 'Giảng viên',  0,      'linh.nt',   '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, 'ACTIVE');

-- =========================
-- CATEGORIES
-- =========================
INSERT INTO categories (category_name) VALUES
('Văn học'),
('Khoa học - Công nghệ'),
('Lịch sử - Địa lý'),
('Kinh tế - Quản trị'),
('Tâm lý - Kỹ năng sống'),
('Thiếu nhi'),
('Triết học'),
('Ngoại ngữ'),
('Y học - Sức khỏe'),
('Pháp luật');

-- =========================
-- AUTHORS
-- =========================
INSERT INTO authors (author_name) VALUES
('Nguyễn Nhật Ánh'),
('Nam Cao'),
('Tô Hoài'),
('Ngô Tất Tố'),
('Vũ Trọng Phụng'),
('Dale Carnegie'),
('Napoleon Hill'),
('Robert T. Kiyosaki'),
('Stephen Hawking'),
('Yuval Noah Harari'),
('Mark Manson'),
('James Clear'),
('Sun Tzu'),
('Dostoevsky'),
('George Orwell');

-- =========================
-- PUBLISHERS
-- =========================
INSERT INTO publishers (pub_name, pub_address) VALUES
('NXB Kim Đồng', '41 Trần Hưng Đạo, Hoàn Kiếm, Hà Nội'),
('NXB Trẻ', '101 Trần Hưng Đạo, Quận 1, TP.HCM'),
('NXB Văn Học', '54 Giảng Võ, Ba Đình, Hà Nội'),
('NXB Tổng Hợp TP.HCM', '62 Ngô Thời Nhiệm, Quận 3, TP.HCM'),
('NXB Hội Nhà Văn', '90 Hàng Đậu, Hoàn Kiếm, Hà Nội'),
('NXB Lao Động', '69 Trần Hưng Đạo, Hoàn Kiếm, Hà Nội'),
('NXB Dân Trí', '146 Quán Thánh, Ba Đình, Hà Nội'),
('NXB Thế Giới', '61 Nơi Mạnh, Quận Hai Bà Trưng, Hà Nội'),
('NXB Chính Trị Quốc Gia', '49 Đinh Lễ, Hoàn Kiếm, Hà Nội'),
('Alphabooks', '76 Hải Bà Trưng, Hoàn Kiếm, Hà Nội');

-- =========================
-- BOOKS
-- =========================
INSERT INTO books (isbn, title, category_id, author_id, pub_id, publish_year, quantity, status) VALUES
-- Văn học (category_id=1)
('978-604-2-10001', 'Cho tôi xin một vé đi tuổi thơ',        1,  1,  1, 2008, 5, 'ACTIVE'),
('978-604-2-10002', 'Mắt biếc',                               1,  1,  2, 1990, 4, 'ACTIVE'),
('978-604-2-10003', 'Chí Phèo',                               1,  2,  3, 1941, 3, 'ACTIVE'),
('978-604-2-10004', 'Dế Mèn Phiêu Lưu Ký',                   1,  3,  1, 1941, 6, 'ACTIVE'),
('978-604-2-10005', 'Tắt Đèn',                                1,  4,  3, 1939, 3, 'ACTIVE'),
('978-604-2-10006', 'Số Đỏ',                                  1,  5,  3, 1936, 4, 'ACTIVE'),
('978-604-2-10007', '1984',                                   1, 15,  8, 1949, 3, 'ACTIVE'),
('978-604-2-10008', 'Tội Ác Và Hình Phạt',                   1, 14,  8, 1866, 2, 'ACTIVE'),

-- Khoa học - Công nghệ (category_id=2)
('978-604-2-20001', 'Lược Sử Thời Gian',                     2,  9,  8, 1988, 4, 'ACTIVE'),
('978-604-2-20002', 'Sapiens: Lược Sử Loài Người',           2, 10,  4, 2011, 5, 'ACTIVE'),

-- Kinh tế - Quản trị (category_id=4)
('978-604-2-40001', 'Cha Giàu Cha Nghèo',                    4,  8,  7, 1997, 6, 'ACTIVE'),
('978-604-2-40002', 'Nghĩ Giàu Làm Giàu',                    4,  7,  6, 1937, 4, 'ACTIVE'),
('978-604-2-40003', 'Đắc Nhân Tâm',                          4,  6,  4, 1936, 8, 'ACTIVE'),

-- Tâm lý - Kỹ năng sống (category_id=5)
('978-604-2-50001', 'Nghệ Thuật Tinh Tế Của Việc Không Quan Tâm', 5, 11, 7, 2016, 5, 'ACTIVE'),
('978-604-2-50002', 'Atomic Habits',                         5, 12, 10, 2018, 4, 'ACTIVE'),

-- Lịch sử - Địa lý (category_id=3)
('978-604-2-30001', 'Binh Pháp Tôn Tử',                      3, 13,  8, 2500, 3, 'ACTIVE'),

-- Thiếu nhi (category_id=6)
('978-604-2-60001', 'Totto-chan: Cô Bé Bên Cửa Sổ',          6,  3,  1, 1981, 5, 'ACTIVE'),

-- INACTIVE
('978-604-2-99001', 'Sách Cũ Không Còn Lưu Hành',            1,  1,  2, 2000, 0, 'INACTIVE');

-- =========================
-- BORROW RECEIPTS & DETAILS
-- =========================

-- Receipt 1: reader 1 mượn 2 cuốn, đã trả
INSERT INTO borrow_receipts (reader_id, emp_id, borrow_date, status) VALUES (1, 2, '2026-03-01', 'RETURNED');
INSERT INTO borrow_details (receipt_id, book_id, due_date, return_date, fine_amount) VALUES
(1, 1, '2026-03-15', '2026-03-14', 0),
(1, 9, '2026-03-15', '2026-03-15', 0);

-- Receipt 2: reader 2 mượn 2 cuốn, trả trễ -> có phạt
INSERT INTO borrow_receipts (reader_id, emp_id, borrow_date, status) VALUES (2, 2, '2026-03-05', 'RETURNED');
INSERT INTO borrow_details (receipt_id, book_id, due_date, return_date, fine_amount) VALUES
(2, 11, '2026-03-19', '2026-03-25', 15000),
(2, 13, '2026-03-19', '2026-03-19', 0);

-- Receipt 3: reader 3 mượn 1 cuốn, đang mượn
INSERT INTO borrow_receipts (reader_id, emp_id, borrow_date, status) VALUES (3, 3, '2026-03-20', 'BORROWING');
INSERT INTO borrow_details (receipt_id, book_id, due_date, return_date, fine_amount) VALUES
(3, 10, '2026-04-03', NULL, 0);

-- Receipt 4: reader 4 — overdue, có phạt
INSERT INTO borrow_receipts (reader_id, emp_id, borrow_date, status) VALUES (4, 2, '2026-03-01', 'OVERDUE');
INSERT INTO borrow_details (receipt_id, book_id, due_date, return_date, fine_amount) VALUES
(4, 14, '2026-03-15', NULL, 30000),
(4, 15, '2026-03-15', NULL, 0);

-- Receipt 5: reader 5 mượn 3 cuốn, đang mượn
INSERT INTO borrow_receipts (reader_id, emp_id, borrow_date, status) VALUES (5, 3, '2026-03-25', 'BORROWING');
INSERT INTO borrow_details (receipt_id, book_id, due_date, return_date, fine_amount) VALUES
(5, 3,  '2026-04-08', NULL, 0),
(5, 4,  '2026-04-08', NULL, 0),
(5, 17, '2026-04-08', NULL, 0);

-- Receipt 6: reader 6 mượn 1 cuốn, đã trả
INSERT INTO borrow_receipts (reader_id, emp_id, borrow_date, status) VALUES (6, 2, '2026-02-10', 'RETURNED');
INSERT INTO borrow_details (receipt_id, book_id, due_date, return_date, fine_amount) VALUES
(6, 12, '2026-02-24', '2026-02-22', 0);

-- Receipt 7: reader 8 đang mượn
INSERT INTO borrow_receipts (reader_id, emp_id, borrow_date, status) VALUES (8, 3, '2026-03-28', 'BORROWING');
INSERT INTO borrow_details (receipt_id, book_id, due_date, return_date, fine_amount) VALUES
(7, 7,  '2026-04-11', NULL, 0),
(7, 8,  '2026-04-11', NULL, 0);
