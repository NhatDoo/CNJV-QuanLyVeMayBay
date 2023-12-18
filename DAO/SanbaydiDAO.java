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
public class SanbaydiDAO {
  Connection con ;
  Statement stmt ;
  int Masanbaydi;
  String TenSanbay;
  String TinhThanhPho;
  String QuocGia;
        public SanbaydiDAO() {
        }
        public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vemaybay?useUnicode=true&characterEncoding=UTF-8", "root", "Nhat123456789");
        }  
        private SanbaydiDAO Parameter(HttpServletRequest request) {
       SanbaydiDAO sanbaydi = new SanbaydiDAO();
       try {
        sanbaydi.Masanbaydi = Integer.parseInt(request.getParameter("MaSanbaydi"));
        sanbaydi.TenSanbay = request.getParameter("TenSanbaydi");
        sanbaydi.TinhThanhPho = request.getParameter("TinhThanhPho");
        sanbaydi.QuocGia = request.getParameter("QuocGia");
       } catch (NumberFormatException e) {
        e.printStackTrace();
       }
    return sanbaydi;
}

        public void displaySanBayDiData(PrintWriter out) {
        try {
        openConnection();
        stmt = con.createStatement();
        String sql = "SELECT * FROM sanbaydi";
        ResultSet rs = stmt.executeQuery(sql);

        out.println("<table border=1>");
        out.println("<tr><th>Mã Sân Bay Đi</th><th>Tên Sân Bay</th><th>Tỉnh/Thành Phố</th><th>Quốc Gia</th></tr>");

        while (rs.next()) {
            int maSanBayDi = rs.getInt("Masanbaydi");
            String tenSanBay = rs.getString("TenSanbay");
            String tinhThanhPho = rs.getString("TinhThanhPho");
            String quocGia = rs.getString("QuocGia");

            out.println("<tr><td>" + maSanBayDi + "</td><td>" + tenSanBay + "</td><td>" + tinhThanhPho + "</td><td>" + quocGia + "</td>"
                    + "<td><a href='Xemsanbaydi?masanbaydiXoa=" + maSanBayDi + "'>Xóa</a></td>"
                    + "<td><form method='post'>" +
                    "<input type='hidden' name='MaSanbaydi' value='" + maSanBayDi + "'>" +
                    "<input type='submit' value='Sửa'>" +
                    "</form></td></tr>");
        }

        out.println("</table>");
        con.close();
    } catch (Exception e) {
        out.println("Lỗi: " + e.getMessage());
    }
        
}
   public void nhap(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
        openConnection();
        SanbaydiDAO sanbayDi = Parameter(request);

        String sql = "INSERT INTO sanbaydi (Masanbaydi, TenSanbay, TinhThanhPho, QuocGia) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, sanbayDi.Masanbaydi);
            statement.setString(2, sanbayDi.TenSanbay);
            statement.setString(3, sanbayDi.TinhThanhPho);
            statement.setString(4, sanbayDi.QuocGia);

            // Thực hiện chèn dữ liệu
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                PrintWriter out = response.getWriter();
                out.println("Dữ liệu sân bay đi đã được chèn thành công!");
            } else {
                PrintWriter out = response.getWriter();
                out.println("Không thể chèn dữ liệu sân bay đi!");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        PrintWriter out = response.getWriter();
        out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
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

        // Retrieve the masanbayden parameter from the request
        String masanbaydenParam = request.getParameter("masanbaydiXoa");
        if (masanbaydenParam != null && !masanbaydenParam.isEmpty()) {
            int masanbaydi = Integer.parseInt(masanbaydenParam);

            // Start a transaction
            con.setAutoCommit(false);

            try {
                xoatubang("sanbaydi", "Masanbaydi", masanbaydi); // Thay "tenbang" và "tencot" bằng tên bảng và tên cột bạn muốn xóa
                xoatubang("chuyenbay", "Masanbaydi", masanbaydi); 
                con.commit();

                out.println("Xóa sân bay đến thành công!");
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
    public void TK(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    String searchMaSanBayDen = req.getParameter("searchMaSanBayDi");
    try {
        openConnection();
        String sql = "SELECT * FROM sanbaydi WHERE Masanbaydi=?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, searchMaSanBayDen);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                out.println("<style>");
                out.println("body");
                out.println("table {border-collapse: collapse; width: 80%;}");
                out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                out.println("th {background-color: #f2f2f2;}");
                out.println("</style>");
                out.println("<table %>");
                out.println("<tr><th>Mã Sân Bay Đi</th><th>Tên Sân Bay Đi</th><th>Tỉnh/Thành Phố</th><th>Quốc Gia</th></tr>");
                out.println("<tr><td>" + resultSet.getString("Masanbaydi") + "</td><td>" + resultSet.getString("TenSanbay") + "</td><td>" + resultSet.getString("TinhThanhPho") + "</td><td>" + resultSet.getString("QuocGia") + "</td>"
                        + "<td><a href='Xemsanbaydi?masanbaydiXoa=" + resultSet.getString("Masanbaydi") + "'>Xóa</a></td>"
                        + "<td><form method='post'>" +
                        "<input type='hidden' name='MaSanbaydi' value='" + resultSet.getString("Masanbaydi") + "'>" +
                        "<input type='submit' value='Sửa'>" +
                        "</form></td></tr>");
                out.println("</table>");
            } else {
                out.println("Không có sân bay đến nào được tìm thấy.");
            }
        }
    }   catch (Exception e) {
        e.printStackTrace();
        out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
}
     public void sua(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
        res.setContentType("text/html");
        try {
            openConnection();
            SanbaydiDAO sanBayDi = Parameter(req);
            String sql = "UPDATE sanbaydi SET TenSanbay=?, TinhThanhPho=?, QuocGia=? WHERE Masanbaydi=?";

            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, sanBayDi.TenSanbay);
                statement.setString(2, sanBayDi.TinhThanhPho);
                statement.setString(3, sanBayDi.QuocGia);
                statement.setInt(4, sanBayDi.Masanbaydi);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    out.println("Dữ liệu sân bay đến đã được cập nhật thành công!");
                } else {
                    out.println("Không thể cập nhật dữ liệu! Hãy chắc chắn rằng Mã Sân Bay Đến tồn tại.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
}
}
