-- =========================
-- DATABASE
-- =========================
DROP DATABASE IF EXISTS librarydb;
CREATE DATABASE librarydb;
USE librarydb;

-- =========================
-- ACCOUNTS (LOGIN SYSTEM)
-- =========================
CREATE TABLE accounts (
  account_id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,

  role ENUM('ADMIN','EMPLOYEE','READER') NOT NULL,
  status ENUM('ACTIVE','INACTIVE','BANNED') DEFAULT 'ACTIVE',

  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- EMPLOYEES
-- =========================
CREATE TABLE employees (
  emp_id INT AUTO_INCREMENT PRIMARY KEY,
  account_id INT UNIQUE,
  full_name VARCHAR(100) NOT NULL,
  phone_number VARCHAR(15),
  shift ENUM('Sáng','Chiều','Tối'),

  CONSTRAINT fk_employee_account
    FOREIGN KEY (account_id)
    REFERENCES accounts(account_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- =========================
-- READERS
-- =========================
CREATE TABLE readers (
  reader_id INT AUTO_INCREMENT PRIMARY KEY,
  account_id INT UNIQUE,
  full_name VARCHAR(100) NOT NULL,
  phone_number VARCHAR(15),
  reader_type VARCHAR(50),
  debt DOUBLE DEFAULT 0,

  CONSTRAINT fk_reader_account
    FOREIGN KEY (account_id)
    REFERENCES accounts(account_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- =========================
-- AUTHORS
-- =========================
CREATE TABLE authors (
  author_id INT AUTO_INCREMENT PRIMARY KEY,
  author_name VARCHAR(100) NOT NULL
);

-- =========================
-- CATEGORIES
-- =========================
CREATE TABLE categories (
  category_id INT AUTO_INCREMENT PRIMARY KEY,
  category_name VARCHAR(100) NOT NULL
);

-- =========================
-- PUBLISHERS
-- =========================
CREATE TABLE publishers (
  pub_id INT AUTO_INCREMENT PRIMARY KEY,
  pub_name VARCHAR(100) NOT NULL
);

-- =========================
-- BOOKS
-- =========================
CREATE TABLE books (
  book_id INT AUTO_INCREMENT PRIMARY KEY,
  isbn VARCHAR(20) NOT NULL,
  title VARCHAR(200) NOT NULL,
  category_id INT,
  author_id INT,
  pub_id INT,
  publish_year INT,
  quantity INT DEFAULT 0,

  status ENUM('ACTIVE','INACTIVE') DEFAULT 'ACTIVE',

  CONSTRAINT fk_book_category
    FOREIGN KEY (category_id)
    REFERENCES categories(category_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE,

  CONSTRAINT fk_book_author
    FOREIGN KEY (author_id)
    REFERENCES authors(author_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE,

  CONSTRAINT fk_book_publisher
    FOREIGN KEY (pub_id)
    REFERENCES publishers(pub_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);

-- =========================
-- BORROW RECEIPTS
-- =========================
CREATE TABLE borrow_receipts (
  receipt_id INT AUTO_INCREMENT PRIMARY KEY,
  reader_id INT,
  emp_id INT,
  borrow_date DATE DEFAULT CURRENT_DATE,

  status ENUM('BORROWING','RETURNED','OVERDUE') DEFAULT 'BORROWING',

  CONSTRAINT fk_receipt_reader
    FOREIGN KEY (reader_id)
    REFERENCES readers(reader_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE,

  CONSTRAINT fk_receipt_employee
    FOREIGN KEY (emp_id)
    REFERENCES employees(emp_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);

-- =========================
-- BORROW DETAILS
-- =========================
CREATE TABLE borrow_details (
  receipt_id INT,
  book_id INT,
  due_date DATE,
  return_date DATE,
  fine_amount DOUBLE DEFAULT 0,

  PRIMARY KEY (receipt_id, book_id),

  CONSTRAINT fk_detail_receipt
    FOREIGN KEY (receipt_id)
    REFERENCES borrow_receipts(receipt_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,

  CONSTRAINT fk_detail_book
    FOREIGN KEY (book_id)
    REFERENCES books(book_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- =========================
-- INDEXES (OPTIMIZE)
-- =========================
CREATE INDEX idx_books_category ON books(category_id);
CREATE INDEX idx_books_author ON books(author_id);
CREATE INDEX idx_receipts_reader ON borrow_receipts(reader_id);
CREATE INDEX idx_receipts_emp ON borrow_receipts(emp_id);