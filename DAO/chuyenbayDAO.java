
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
public class chuyenbayDAO {
     Connection con ;
     Statement stmt ;
     int maChuyenBay;
     String loaiMayBay;
     int soGhe;
     int maSanBayDi ;
     int maSanBayDen;
     String gioKhoiHanh ;
     String ngayKhoiHanh ;
    public chuyenbayDAO() {
    }
      public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vemaybay?useUnicode=true&characterEncoding=UTF-8", "root", "Nhat123456789");
        }
        private chuyenbayDAO Parametter(HttpServletRequest request) {
        chuyenbayDAO chuyenBay = new chuyenbayDAO();

        try {
            chuyenBay.maChuyenBay = Integer.parseInt(request.getParameter("Machuyenbay"));
            chuyenBay.loaiMayBay = request.getParameter("Loaimaybay");
            chuyenBay.soGhe =  Integer.parseInt(request.getParameter("Soghe"));
            chuyenBay.maSanBayDi = Integer.parseInt(request.getParameter("MaSanbaydi"));
            chuyenBay.maSanBayDen= Integer.parseInt(request.getParameter("MaSanbayden"));
            chuyenBay.gioKhoiHanh = request.getParameter("Giokhoihanh");
            chuyenBay.ngayKhoiHanh= request.getParameter("Ngaykhoihanh");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return chuyenBay;
    }
        public void displayData(PrintWriter out) 
        {
        try {
            openConnection();
            stmt = con.createStatement();
            String sql = "SELECT * FROM chuyenbay";
            ResultSet rs = stmt.executeQuery(sql);

            out.println("<table border=1>");
            out.println("<tr><th>Mã Chuyến Bay</th><th>Loại Máy Bay</th><th>Số Ghế</th><th>Mã Sân Bay Đi</th><th>Mã Sân Bay Đến</th><th>Giờ Khởi Hành</th><th>Ngày Khởi Hành</th><th>Thao Tác</th></tr>");

            while (rs.next()) {
                String maChuyenBay = rs.getString("Machuyenbay");
                String loaiMayBay = rs.getString("Loaimaybay");
                int soGhe = rs.getInt("Soghe");
                String maSanBayDi = rs.getString("MaSanbaydi");
                String maSanBayDen = rs.getString("MaSanbayden");
                String gioKhoiHanh = rs.getString("Giokhoihanh");
                String ngayKhoiHanh = rs.getString("Ngaykhoihanh");

                out.println("<tr><td>" + maChuyenBay + "</td><td>" + loaiMayBay + "</td><td>" 
                        + soGhe + "</td><td>" + maSanBayDi + "</td><td>" + maSanBayDen + "</td><td>" 
                        + gioKhoiHanh + "</td><td>" + ngayKhoiHanh + "</td><td><a href='Xemchuyenbay?maChuyenBayXoa=" + maChuyenBay + "'>Xóa</a></td>"
                        + "<td><form method='post'>" +
                        "<input type='hidden' name='Machuyenbay' value='" + maChuyenBay + "'>" +
                        "<input type='submit' value='Sửa'>" +
                        "</form></td>");
                out.println("</tr>");
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
            chuyenbayDAO chuyenBay = Parametter(request);

            String sql = "INSERT INTO chuyenbay (Machuyenbay, Loaimaybay, Soghe, MaSanbaydi, MaSanbayden, Giokhoihanh, Ngaykhoihanh) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setInt(1, chuyenBay.maChuyenBay);
                statement.setString(2, chuyenBay.loaiMayBay);
                statement.setInt(3, chuyenBay.soGhe);
                statement.setInt(4, chuyenBay.maSanBayDi);
                statement.setInt(5, chuyenBay.maSanBayDen);
                statement.setString(6, chuyenBay.gioKhoiHanh);
                statement.setString(7, chuyenBay.ngayKhoiHanh);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    PrintWriter out = response.getWriter();
                    out.println("Dữ liệu chuyến bay đã được chèn thành công!");
                } else {
                    PrintWriter out = response.getWriter();
                    out.println("Không thể chèn dữ liệu chuyến bay!");
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

        // Retrieve the maChuyenBay parameter from the request
        String maChuyenBayParam = request.getParameter("maChuyenBayXoa");
        if (maChuyenBayParam != null && !maChuyenBayParam.isEmpty()) {
            int maChuyenBay = Integer.parseInt(maChuyenBayParam);

            // Start a transaction
            con.setAutoCommit(false);

            try {
                xoatubang("chuyenbay", "Machuyenbay", maChuyenBay);

                con.commit();

                out.println("Xóa chuyến bay thành công!");
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
    String searchMaChuyenBay = req.getParameter("searchMaChuyenBay");
    try {
        openConnection();
        String sql = "SELECT * FROM chuyenbay WHERE Machuyenbay=?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, searchMaChuyenBay);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                out.println("<style>");
                out.println("body");
                out.println("table {border-collapse: collapse; width: 80%;}");
                out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                out.println("th {background-color: #f2f2f2;}");
                out.println("</style>");
                out.println("<table %>");
                out.println("<tr><th>Mã Chuyến Bay</th><th>Loại Máy Bay</th><th>Số Ghế</th><th>Mã Sân Bay Đi</th><th>Mã Sân Bay Đến</th><th>Giờ Khởi Hành</th><th>Ngày Khởi Hành</th></tr>");
                out.println("<tr><td>" + resultSet.getString("Machuyenbay") + "</td><td>" + resultSet.getString("Loaimaybay") + "</td><td>" + resultSet.getInt("Soghe") + "</td><td>" + resultSet.getString("MaSanbaydi") + "</td><td>"+ resultSet.getString("MaSanbayden") + "</td><td>"+ resultSet.getString("Giokhoihanh") + "</td><td>"+ resultSet.getString("Ngaykhoihanh") + "</td>"+"<td><a href='XoaChuyenBay?maChuyenBayXoa=" + resultSet.getString("Machuyenbay") + "'>Xóa</a></td>"
                +"<td><form method='post'>" +
                "<input type='hidden' name='MaChuyenBay' value='" + resultSet.getString("Machuyenbay") + "'>" +
                "<input type='submit' value='Sửa'>" +
                "</form></td></tr>");
            } else {
                // Chuyến bay không tồn tại
                out.println("Không tìm thấy chuyến bay với mã: " + searchMaChuyenBay);
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
        chuyenbayDAO cb = Parametter(req);
        String sql = "UPDATE chuyenbay SET Loaimaybay=?, Soghe=?, MaSanbaydi=?, MaSanbayden=?, Giokhoihanh=?, Ngaykhoihanh=? WHERE Machuyenbay=?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, cb.loaiMayBay);
            statement.setInt(2, cb.soGhe);
            statement.setInt(3, cb.maSanBayDi);
            statement.setInt(4, cb.maSanBayDen);
            statement.setString(5, cb.gioKhoiHanh);
            statement.setString(6, cb.ngayKhoiHanh);
            statement.setInt(7, cb.maChuyenBay);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                out = res.getWriter();
                out.println("Dữ liệu chuyến bay đã được cập nhật thành công!");
            } else {
                out = res.getWriter();
                out.println("Không thể cập nhật dữ liệu chuyến bay! Hãy chắc chắn rằng Mã Chuyến Bay tồn tại.");
            }

        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
}

}
