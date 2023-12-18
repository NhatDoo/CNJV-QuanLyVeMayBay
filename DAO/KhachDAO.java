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


public class KhachDAO {
        Connection con ;
        Statement stmt ;
        int maKhach ;
        String hoKhach ;
        String tenKhach ;
        String cmnd ;
        String soDienThoai;

    public KhachDAO() {
    }
      public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vemaybay?useUnicode=true&characterEncoding=UTF-8", "root", "Nhat123456789");
        }
        private KhachDAO Parameter (HttpServletRequest request) {
        KhachDAO khach = new KhachDAO();
            khach.maKhach = Integer.parseInt(request.getParameter("MaKhach"));
            khach.hoKhach = request.getParameter("HoKhach");
            khach.tenKhach = request.getParameter("TenKhach");
            khach.cmnd = request.getParameter("CMND");
            khach.soDienThoai = request.getParameter("SoDienThoai");
        try {
           
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return khach;
    }
        
    public void displayData(PrintWriter out) 
        {
        try {
            openConnection();
            stmt = con.createStatement();
            String sql = "select * from khachhang";
            ResultSet rs = stmt.executeQuery(sql);
            out.println("<table border=1 width=50% height=50%>");
            out.println("<tr><th>Mã Khách</th><th>Họ Khách</th><th>Tên Khách</th><th>CMND</th><th>SDT</th></tr>");
            while (rs.next()) {
                int maKhach = rs.getInt("Makhach");
                String tenKhach = rs.getString("TenKhach");
                String hk = rs.getString("HoKhach");
                String CMND = rs.getString("CMND");
                String sdt = rs.getString("SoDienThoai");
                out.println("<tr><td>" + maKhach + "</td><td>" + hk + "</td><td>" + tenKhach + "</td><td>" + CMND + "</td><td>"+ sdt + "</td>"+"<td><a href='XemDS?maKhachXoa=" + maKhach + "'>Xóa</a></td>"+
                "<td><form method='post'>" +
                "<input type='hidden' name='MaKhach' value='" + maKhach + "'>" +
                "<input type='submit' value='Sửa'>" +
                "</form></td></tr>");
            }
            out.println("</table>");
            con.close();
            } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
            }
    }
    
    
    public void nhap(HttpServletRequest request, HttpServletResponse response)
        {
        try {
           openConnection();
           KhachDAO k = Parameter(request);
           String sql = "INSERT INTO khachhang (Makhach ,HoKhach, TenKhach, CMND, SoDienThoai) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(sql))
            {   
                statement.setInt(1, k.maKhach);
                statement.setString(2, k.hoKhach);
                statement.setString(3, k.tenKhach);
                statement.setString(4, k.cmnd);
                statement.setString(5, k.soDienThoai);

                // Thực hiện chèn dữ liệu
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    PrintWriter out = response.getWriter();
         
                    System.out.println("Dữ liệu đã được chèn thành công!");
                } 
                else 
                {
                    PrintWriter out = response.getWriter();
                  
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
        public void xoa(HttpServletRequest request, HttpServletResponse response ,PrintWriter out)
        {
           try {
            openConnection();

            // Retrieve the maKhach parameter from the request
            String maKhachParam = request.getParameter("maKhachXoa");
            if (maKhachParam != null && !maKhachParam.isEmpty()) {
                int maKhach = Integer.parseInt(maKhachParam);

                // Start a transaction
                con.setAutoCommit(false);

                try {
                    xoatubang("ve", "Makhach", maKhach);
                    xoatubang("khachhang", "Makhach", maKhach);
                    
                    con.commit();

                    out.println("Xóa khách hàng thành công!");
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
        public void TK(HttpServletRequest req, HttpServletResponse res ,PrintWriter out)
        {
        String searchMaKhach = req.getParameter("searchMaKhach");
        try {
            openConnection();
            String sql = "SELECT * FROM khachhang WHERE Makhach=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, searchMaKhach);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    out.println("<style>");
                    out.println("body");
                    out.println("table {border-collapse: collapse; width: 80%;}");
                    out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                    out.println("th {background-color: #f2f2f2;}");
                    out.println("</style>");
                    out.println("<table %>");
                    out.println("<tr><th>Mã Khách</th><th>Họ Khách</th><th>Tên Khách</th><th>CMND</th><th>SDT</th></tr>");
                    out.println("<tr><td>" + resultSet.getString("Makhach") + "</td><td>" + resultSet.getString("HoKhach") + "</td><td>" + resultSet.getString("TenKhach") + "</td><td>" + resultSet.getString("CMND") + "</td><td>"+ resultSet.getString("SoDienThoai") + "</td>"+"<td><a href='XemDS?maKhachXoa=" + resultSet.getString("Makhach") + "'>Xóa</a></td>"
                    +"<td><form method='post'>" +
                    "<input type='hidden' name='MaKhach' value='" + resultSet.getString("Makhach") + "'>" +
                    "<input type='submit' value='Sửa'>" +
                    "</form></td></tr>");
                } else {
                 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
        }
        public void sua(HttpServletRequest req, HttpServletResponse res ,PrintWriter out)
        {
        res.setContentType("text/html");
        try {
            openConnection();
            KhachDAO k = Parameter(req);
            String sql = "UPDATE khachhang SET HoKhach=?, TenKhach=?, CMND=?, SoDienThoai=? WHERE Makhach=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                
                statement.setString(1, k.hoKhach);
                statement.setString(2, k.tenKhach);
                statement.setString(3, k.cmnd);
                statement.setString(4, k.soDienThoai);
                statement.setInt(5, k.maKhach);
                
               
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    out = res.getWriter();
                    out.println("Dữ liệu đã được cập nhật thành công!");
                }else {
                    out = res.getWriter();
                    out.println("Không thể cập nhật dữ liệu! Hãy chắc chắn rằng Mã Khách tồn tại.");
                }
           
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
        }
        
}
