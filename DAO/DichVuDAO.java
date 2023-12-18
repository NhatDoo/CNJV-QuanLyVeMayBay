package DAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DichVuDAO {
    Connection con ;
    Statement stmt ;
    int maDichVu ;
    String tenDichVu;
    double giaThucAn;
    double giaLuongThuc;
    double triGiaDV;
    
    public DichVuDAO() {
    }
      public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vemaybay?useUnicode=true&characterEncoding=UTF-8", "root", "Nhat123456789");
        }
        private DichVuDAO Parameter (HttpServletRequest request) {
        DichVuDAO dichVu = new DichVuDAO();

        try {
            dichVu.maDichVu = Integer.parseInt(request.getParameter("MaDichVu"));
            dichVu.tenDichVu = request.getParameter("TenDichVu");
            dichVu.giaThucAn = Double.parseDouble(request.getParameter("GiaThucAn"));
            dichVu.giaLuongThuc = Double.parseDouble(request.getParameter("GiaLuongThuc"));
            dichVu.triGiaDV = Double.parseDouble(request.getParameter("TriGiaDV"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return dichVu;
    }
        public void displayData(PrintWriter out) 
        {
        try {
            openConnection();
            stmt = con.createStatement();
        String sql = "select * from dichvu";
        ResultSet rs = stmt.executeQuery(sql);
        out.println("<table border=1 width=50% height=50%>");
        out.println("<tr><th>Mã Dịch Vụ</th><th>Tên Dịch Vụ</th><th>Giá Thực Ăn</th><th>Giá Lượng Thực</th><th>Trị Giá Dịch Vụ</th></tr>");
        while (rs.next()) {
            int maDichVu = rs.getInt("MaDichVu");
            String tenDichVu = rs.getString("TenDichVu");
            double giaThucAn = rs.getDouble("GiaThucAn");
            double giaLuongThuc = rs.getDouble("GiaLuongThuc");
            double triGiaDV = rs.getDouble("TriGiaDV");

            out.println("<tr><td>" + maDichVu + "</td><td>" + tenDichVu + "</td><td>" + giaThucAn + "</td><td>" + giaLuongThuc + "</td><td>"+ triGiaDV + "</td>"+"<td><a href='XemDichVu?maDichVuXoa=" + maDichVu + "'>Xóa</a></td>"+"<td><form method='post'>" +
                "<input type='hidden' name='MaDichVu' value='" + maDichVu + "'>" +
                "<input type='submit' value='Sửa'>" +
                "</form></td></tr>");
        }
        out.println("</table>");
        con.close();
    } catch (Exception e) {
        out.println("Lỗi: " + e.getMessage());
    }
    }
    public void nhap(HttpServletRequest request, HttpServletResponse response){
    
        try {
           openConnection();
           DichVuDAO d = Parameter(request);
           String sql = "INSERT INTO dichvu (MaDichVu, TenDichVu, GiaThucAn, GiaLuongThuc, TriGiaDV) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, d.maDichVu);
            statement.setString(2, d.tenDichVu);
            statement.setDouble(3, d.giaThucAn);
            statement.setDouble(4, d.giaLuongThuc);
            statement.setDouble(5, d.triGiaDV);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                PrintWriter out = response.getWriter();
                out.println("Dữ liệu dịch vụ đã được chèn thành công!");
            } else {
                PrintWriter out = response.getWriter();
                out.println("Không thể chèn dữ liệu dịch vụ!");
            }
        }
            } catch (Exception e) 
            {
                   e.printStackTrace();
                    
            }
        } 
       public void xoatubang(String tableName, String columnName, int value) throws Exception 
       {
        String sql = "DELETE FROM " + tableName + " WHERE " + columnName + " = ?";
        try (PreparedStatement statement = con.prepareStatement(sql)) 
            {
            statement.setInt(1, value);
            statement.executeUpdate();
            }
       }
        public void xoa(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
             try {
                    openConnection();

        // Retrieve the maDichVu parameter from the request
        String maDichVuParam = request.getParameter("maDichVuXoa");
        if (maDichVuParam != null && !maDichVuParam.isEmpty()) {
            int maDichVu = Integer.parseInt(maDichVuParam);

                // Start a transaction
                con.setAutoCommit(false);

                try {
                    xoatubang("loaive", "MaDichVu", maDichVu);  
                    xoatubang("dichvu", "MaDichVu", maDichVu);

                    con.commit();

                    out.println("Xóa dịch vụ thành công!");
                } catch (Exception e) {
                    // Rollback the transaction if an error occurs
                    con.rollback();
                    out.println("Lỗi: " + e.getMessage());
                } finally {
                    // Reset auto-commit to true
                    con.setAutoCommit(true);
                }
            }
            } catch (Exception e) {
                out.println("Lỗi: " + e.getMessage());
            } finally {
                out.close();
            }
        }
        public void searchDichVu(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
            String searchMaDichVu = req.getParameter("searchMaDichVu");

            try {
                openConnection();
                String sql = "SELECT * FROM dichvu WHERE MaDichVu=?";
                try (PreparedStatement statement = con.prepareStatement(sql)) {
                    statement.setString(1, searchMaDichVu);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        out.println("<style>");
                        out.println("table {border-collapse: collapse; width: 80%;}");
                        out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                        out.println("th {background-color: #f2f2f2;}");
                        out.println("</style>");
                        out.println("<table %>");
                        out.println("<tr><th>Mã Dịch Vụ</th><th>Tên Dịch Vụ</th><th>Giá Thực Ăn</th><th>Giá Lượng Thực</th><th>Trị Giá Dịch Vụ</th></tr>");
                        out.println("<tr><td>" + resultSet.getString("MaDichVu") + "</td><td>" + resultSet.getString("TenDichVu") + "</td><td>" + resultSet.getString("GiaThucAn") + "</td><td>" + resultSet.getString("GiaLuongThuc") + "</td><td>" + resultSet.getString("TriGiaDV") + "</td>" + "<td><a href='XemDichVu?maDichVuXoa=" + resultSet.getString("MaDichVu") + "'>Xóa</a></td>"
                        + "<input type='hidden' name='MaDichVu' value='" +resultSet.getString("MaDichVu") + "'>" +
                          "<input type='submit' value='Sửa'>" +
                          "</form></td></tr>");
                        out.println("</table>");
                    } else {
                        out.println("Không tìm thấy dữ liệu cho Mã Dịch Vụ: " + searchMaDichVu);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
            }
        }
        public void suaDichVu(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    res.setContentType("text/html");
    DichVuDAO d = Parameter(req);
    try {
        openConnection();
        String sql = "UPDATE dichvu SET TenDichVu=?, GiaThucAn=?, GiaLuongThuc=?, TriGiaDV=? WHERE MaDichVu=?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, d.tenDichVu);
            statement.setDouble(2, d.giaThucAn);
            statement.setDouble(3, d.giaLuongThuc);
            statement.setDouble(4, d.triGiaDV);
            statement.setInt(5, d.maDichVu);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                out.println("Dữ liệu dịch vụ đã được cập nhật thành công!");
            } else {
                out.println("Không thể cập nhật dữ liệu! Hãy chắc chắn rằng Mã Dịch Vụ tồn tại.");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
}
    
}
