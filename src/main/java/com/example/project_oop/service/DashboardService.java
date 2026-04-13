package com.example.project_oop.service;

import com.example.project_oop.config.DatabaseConnection;
import com.example.project_oop.models.view.RecentTransactionDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DashboardService {

    /**
     * Tổng số lượng sách trong thư viện (SUM quantity)
     */
    public int getTotalBooks() {
        String sql = "SELECT COALESCE(SUM(quantity), 0) AS total FROM books";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Tổng số bạn đọc đang hoạt động
     */
    public int getTotalReaders() {
        String sql = "SELECT COUNT(*) AS total FROM readers WHERE status = 'ACTIVE'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Số sách đang được mượn (chưa trả)
     */
    public int getBooksOnLoan() {
        String sql = "SELECT COUNT(*) AS total FROM borrow_details WHERE return_date IS NULL";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Số sách quá hạn (chưa trả và đã qua hạn trả)
     */
    public int getOverdueItems() {
        String sql = "SELECT COUNT(*) AS total FROM borrow_details WHERE return_date IS NULL AND due_date < CURDATE()";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Lấy 10 giao dịch mượn gần nhất (mỗi chi tiết sách là 1 dòng)
     */
    public List<RecentTransactionDTO> getRecentTransactions() {
        List<RecentTransactionDTO> list = new ArrayList<>();
        String sql = """
                SELECT br.receipt_id, r.full_name AS reader_name, b.title AS book_title,
                       br.borrow_date, br.status
                FROM borrow_details bd
                JOIN borrow_receipts br ON bd.receipt_id = br.receipt_id
                JOIN readers r ON br.reader_id = r.reader_id
                JOIN books b ON bd.book_id = b.book_id
                ORDER BY br.borrow_date DESC, br.receipt_id DESC
                LIMIT 10
                """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RecentTransactionDTO dto = new RecentTransactionDTO();
                dto.setReceiptId(rs.getInt("receipt_id"));
                dto.setReaderName(rs.getString("reader_name"));
                dto.setBookTitle(rs.getString("book_title"));
                dto.setBorrowDate(rs.getDate("borrow_date"));
                dto.setStatus(rs.getString("status"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Thống kê trạng thái sách cho PieChart:
     * - Available: tổng số lượng sách - số sách đang mượn
     * - On Loan: số sách đang mượn (chưa trả, chưa quá hạn)
     * - Overdue: số sách quá hạn (chưa trả, đã qua due_date)
     */
    public Map<String, Integer> getBookStatusCounts() {
        Map<String, Integer> counts = new LinkedHashMap<>();

        int totalBooks = getTotalBooks();
        int onLoan = 0;
        int overdue = 0;

        String sqlOnLoan = "SELECT COUNT(*) AS total FROM borrow_details WHERE return_date IS NULL AND due_date >= CURDATE()";
        String sqlOverdue = "SELECT COUNT(*) AS total FROM borrow_details WHERE return_date IS NULL AND due_date < CURDATE()";

        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sqlOnLoan);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    onLoan = rs.getInt("total");
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(sqlOverdue);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    overdue = rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int available = Math.max(0, totalBooks - onLoan - overdue);
        counts.put("Available", available);
        counts.put("On Loan", onLoan);
        counts.put("Overdue", overdue);

        return counts;
    }

    /**
     * Số sách quá hạn trong tuần này
     */
    public int getOverdueThisWeek() {
        String sql = """
                SELECT COUNT(*) AS total FROM borrow_details
                WHERE return_date IS NULL
                  AND due_date < CURDATE()
                  AND due_date >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)
                """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Tổng tiền phạt chưa thu (fine_amount > 0 nhưng chưa trả sách hoặc đã trả nhưng có fine)
     */
    public double getTotalUnpaidFines() {
        String sql = "SELECT COALESCE(SUM(fine_amount), 0) AS total FROM borrow_details WHERE fine_amount > 0";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
