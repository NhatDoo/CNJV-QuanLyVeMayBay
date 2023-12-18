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


public class VeDAO {
        Connection con ;
        Statement stmt ;
        int Sove;
        int Makhach;
        int Maloaive;
        int Machuyenbay;
        String NgayDatve;
        String Ngaynhanve;
    public VeDAO() {
    }
      public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vemaybay?useUnicode=true&characterEncoding=UTF-8", "root", "Nhat123456789");
        }
        private VeDAO Parameter(HttpServletRequest request) {
        VeDAO ve = new VeDAO();

        try {
            ve.Sove = Integer.parseInt(request.getParameter("Sove"));
            ve.Makhach = Integer.parseInt(request.getParameter("Makhach"));
            ve.Maloaive = Integer.parseInt(request.getParameter("Maloaive"));
            ve.Machuyenbay = Integer.parseInt(request.getParameter("Machuyenbay"));
            ve.NgayDatve = request.getParameter("NgayDatve");
            ve.Ngaynhanve = request.getParameter("Ngaynhanve");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return ve;
    }
        public void displayData(PrintWriter out) 
        {
        try {
            openConnection();
            stmt = con.createStatement();
            String sql = "select * from ve ";
            ResultSet rs = stmt.executeQuery(sql);
            out.println("<table border=1>");
            out.println("<tr><th>Số Vé</th><th>Mã Khách</th><th>Mã chuyến bay</th><th>Mã loại vé</th><th>Ngày đặt vé</th><th>Ngày Nhận vé</th></tr>");
            while (rs.next()) {
                int sv = rs.getInt("Sove");
                int mk = rs.getInt("Makhach");
                int mcb = rs.getInt("Machuyenbay");
                int mlv = rs.getInt("Maloaive");
                String nnv = rs.getString("NgayDatve");
                String ndt = rs.getString("Ngaynhanve");
                out.println("<tr><td>" + sv + "</td><td>" + mk + "</td><td>" + mcb + "</td><td>" + mlv + "</td><td>"+ ndt + "</td>"+"</td><td>"+ nnv + "</td>"+"<td><a href='XemSoVe?svx=" + sv + "'>Xóa</a></td>"+
            "<td><form method='post'>" +
            "<input type='hidden' name='Sove' value='" + sv + "'>" +
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
           VeDAO v = Parameter(request);
           String sql = "INSERT INTO ve (Sove,Makhach,Machuyenbay,Maloaive,NgayDatve,Ngaynhanve) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(sql))
            {
                statement.setInt(1, v.Sove);
                statement.setInt(2, v.Makhach);
                statement.setInt(3, v.Machuyenbay);
                statement.setInt(4, v.Maloaive);
                statement.setString(5, v.NgayDatve);
                statement.setString(6, v.Ngaynhanve);

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
        public void xoa(HttpServletRequest request, HttpServletResponse response ,PrintWriter out)
        {
        try {
            openConnection();
            String svx = request.getParameter("svx");

            // Kiểm tra xem mã khách có tồn tại không
            if (svx != null && !svx.isEmpty()) {
                // Tạo câu lệnh SQL để xóa khách hàng
                String sql = "DELETE FROM ve WHERE Sove = ?";
                
                // Tạo đối tượng PreparedStatement để thực hiện câu lệnh SQL
                try (PreparedStatement statement = con.prepareStatement(sql)) {
                    statement.setString(1, svx);
                    
                    // Thực hiện xóa dữ liệu
                    int rowsDeleted = statement.executeUpdate();
                    
                    if (rowsDeleted > 0) {
                        out.println("Xóa khách hàng thành công!");
                    } else {
                        out.println("Không tìm thấy khách hàng có mã " + svx + " để xóa.");
                    }
                }
            } else {
                
            }
            con.close();
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
        }
        }
        public void TK(HttpServletRequest req, HttpServletResponse res ,PrintWriter out)
        {
        String searchSove = req.getParameter("searchSove");
        try {
            openConnection();
            String sql = "SELECT * FROM ve WHERE Sove=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, searchSove);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    out.println("<style>");
                    out.println("body");
                    out.println("table {border-collapse: collapse; width: 80%;}");
                    out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                    out.println("th {background-color: #f2f2f2;}");
                    out.println("</style>");
                    out.println("<table %>");
                    out.println("<tr><th>Số Vé </th><th>Mã Khách </th><th>Mã chuyến bay </th><th>Mã loại vé </th><th>Ngày đặt vé</th></th>Ngày nhận vé  </th><tr>");
                    out.println("<tr><td>" + resultSet.getString("Sove") + "</td><td>" + resultSet.getString("MaKhach") + "</td><td>" + resultSet.getString("Machuyenbay") + "</td><td>" + resultSet.getString("Maloaive") + "</td><td>"+ resultSet.getString("NgayDatve")+ "</td><td>"+ resultSet.getString("Ngaynhanve")
                     + "</td>"+"<td><a href='XemSoVe?svx=" + resultSet.getString("Sove") + "'>Xóa</a></td>"
                     +"<td><form method='post'>" +
                      "<input type='hidden' name='Sove' value='" + resultSet.getString("Sove") + "'>" +
                      "<input type='submit' value='Sửa'>" +
                     "</form></td></tr>");       
                    out.println("</table>");
                } else {
                   out.println("o co so ve");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
        }
        public void sua(HttpServletRequest request, HttpServletResponse response,PrintWriter out)
        {
         response.setContentType("text/html");
         try{
             openConnection();
             VeDAO v = Parameter(request);
             String sql = "UPDATE ve SET Makhach=?, Machuyenbay=?, Maloaive=? , NgayDatve=?, Ngaynhanve=? WHERE Sove=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setInt(1, v.Makhach);
                statement.setInt(2, v.Machuyenbay);
                statement.setInt(3, v.Maloaive);
                statement.setString(4, v.NgayDatve);
                statement.setString(5, v.Ngaynhanve);
                statement.setInt(6, v.Sove);
                
               
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    out = response.getWriter();
                    out.println("Dữ liệu đã được cập nhật thành công!");
                }else {
                    out = response.getWriter();
                    out.println("Không thể cập nhật dữ liệu! Hãy chắc chắn rằng Sove tồn tại.");
                }
            }
         }
          catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
        }
}
