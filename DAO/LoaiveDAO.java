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

public class LoaiveDAO {
  Connection con ;
  Statement stmt ;
  int MaloaiVe;
  String TenloaiVe;
  double GiaTien ;
  int Madichvu;
  String Ghichu;
 
    public LoaiveDAO() {
    }
      public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vemaybay?useUnicode=true&characterEncoding=UTF-8", "root", "Nhat123456789");
        }  
            public void displayData(PrintWriter out) {
            try {
                openConnection();
                stmt = con.createStatement();
                String sql = "SELECT * FROM loaive";
                ResultSet rs = stmt.executeQuery(sql);

                out.println("<table border=1>");
                out.println("<tr><th>Mã Loại Vé</th><th>Tên Loại Vé</th><th>Giá Tiền</th><th>Mã Dịch Vụ</th><th>Ghi Chú</th></tr>");

                while (rs.next()) {
                    int maLoaiVe = rs.getInt("MaloaiVe");
                    String tenLoaiVe = rs.getString("TenloaiVe");
                    double giaTien = rs.getDouble("GiaTien");
                    int maDichVu = rs.getInt("Madichvu");
                    String ghiChu = rs.getString("Ghichu");

                    out.println("<tr><td>" + maLoaiVe + "</td><td>" + tenLoaiVe + "</td><td>" + giaTien + "</td><td>" + maDichVu + "</td><td>" + ghiChu + "</td></td>"+"<td><a href='XemLoaive?MaloaiveXoa=" + maLoaiVe + "'>Xóa</a></td>"+
                        "<td><form method='post'>" +
                        "<input type='hidden' name='MaloaiVe' value='" + maLoaiVe + "'>" +
                        "<input type='submit' value='Sửa'>" +
                        "</form></td></tr>");
                }
                out.println("</table>");
                con.close();
            } catch (Exception e) {
                out.println("Lỗi: " + e.getMessage());
            }
        }
            private LoaiveDAO Parameter (HttpServletRequest request) {
           LoaiveDAO loaiVe = new LoaiveDAO();
        try {
            String loaiveP = request.getParameter("MaloaiVe");
            loaiVe.MaloaiVe = Integer.parseInt(loaiveP);
            loaiVe.TenloaiVe = request.getParameter("TenloaiVe");
            loaiVe.GiaTien = Double.parseDouble(request.getParameter("GiaTien"));
            loaiVe.Madichvu = Integer.parseInt(request.getParameter("Madichvu"));
            loaiVe.Ghichu = request.getParameter("Ghichu");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return loaiVe;
    }
            public void nhap(HttpServletRequest request, HttpServletResponse response) {
            LoaiveDAO lv = Parameter(request);
                try {
                openConnection();
                
                String sql = "INSERT INTO loaive (MaloaiVe, TenloaiVe, GiaTien, Madichvu, Ghichu) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement statement = con.prepareStatement(sql)) {
                    statement.setInt(1,lv.MaloaiVe);
                    statement.setString(2, lv.TenloaiVe);
                    statement.setDouble(3, lv.GiaTien);
                    statement.setInt(4, lv.Madichvu);
                    statement.setString(5,lv.Ghichu);

                    // Execute the insert statement
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        PrintWriter out = response.getWriter();
                        out.println("Dữ liệu đã được chèn thành công!");
                    } else {
                        PrintWriter out = response.getWriter();
                        out.println("Không thể chèn dữ liệu!");
                    }
                }
            } catch (Exception e) {
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

                // Retrieve the MaloaiVe parameter from the request
                String MaloaiVeParam = request.getParameter("MaloaiveXoa");
                if (MaloaiVeParam != null && !MaloaiVeParam.isEmpty()) {
                    int Maloaive = Integer.parseInt(MaloaiVeParam);

                    con.setAutoCommit(false);

                    try {
                        xoatubang("ve", "Maloaive", Maloaive); // Assuming "ve" table has a column "Maloaive" for loaive
                        xoatubang("loaive", "MaloaiVe", Maloaive);

                        con.commit();

                        out.println("Xóa loại vé thành công!");
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
        String searchMaloaiVe = req.getParameter("searchMaLoaiVe");
        try {
        openConnection();
        String sql = "SELECT * FROM loaive WHERE MaloaiVe=?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, searchMaloaiVe);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                out.println("<style>");
                out.println("body");
                out.println("table {border-collapse: collapse; width: 80%;}");
                out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                out.println("th {background-color: #f2f2f2;}");
                out.println("</style>");
                out.println("<table>");
                out.println("<tr><th>Mã Loại Vé</th><th>Tên Loại Vé</th><th>Giá Tiền</th><th>Mã Dịch Vụ</th><th>Ghi Chú</th></tr>");
                out.println("<tr><td>" + resultSet.getString("MaloaiVe") + "</td><td>" + resultSet.getString("TenloaiVe") + "</td><td>" + resultSet.getDouble("GiaTien") + "</td><td>" + resultSet.getString("Madichvu") + "</td><td>" + resultSet.getString("Ghichu") + "</td></td>"
                     +"<td><a href='XemLoaive?MaloaiveXoa=" + resultSet.getString("MaloaiVe") + "'>Xóa</a></td>"
                     +"<td><form method='post'>" +
                      "<input type='hidden' name='MaloaiVe' value='" + resultSet.getString("MaLoaiVe") + "'>" +
                      "<input type='submit' value='Sửa'>" +
                     "</form></td></tr>");     
                out.println("</table>");
            } else {
                out.println("Không tìm thấy thông tin loại vé.");
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
     }
    public void sua(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    res.setContentType("text/html");


    try {
        openConnection();
        LoaiveDAO lv =Parameter(req);
        String sql = "UPDATE loaive SET TenloaiVe=?, GiaTien=?, Madichvu=?, Ghichu=? WHERE MaloaiVe=?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, lv.TenloaiVe);
            statement.setDouble(2, lv.GiaTien);
            statement.setInt(3, lv.Madichvu);
            statement.setString(4, lv.Ghichu);
            statement.setInt(5, lv.MaloaiVe);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                out = res.getWriter();
                out.println("Dữ liệu đã được cập nhật thành công!");
            } else {
                out = res.getWriter();
                out.println("Không thể cập nhật dữ liệu! Hãy chắc chắn rằng Mã Loại Vé tồn tại.");
            }

        }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
}


  
}
