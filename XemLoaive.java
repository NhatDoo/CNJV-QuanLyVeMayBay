

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.LoaiveDAO;
import DAO.menu;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@WebServlet("/XemLoaive")
public class XemLoaive extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public XemLoaive() {
        super();
    }
    LoaiveDAO in = new LoaiveDAO();
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
        
        menu menu = new menu();
        menu.htmenu(res, out);
        
        out.println("<h2>Tìm Kiếm Khách Hàng</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='searchMaLoaiVe'>Mã Khách:</label>");
        out.println("  <input type='text' id='searchMaLoaiVe' name='searchMaLoaiVe' required>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");
        
        out.println("<h2>Form Nhập Thông Tin Loại Vé</h2>");
        out.println("<form method='get'"); // Replace 'TenServletXuLyNhapLoaiVe' with the actual servlet URL for processing the form
        out.println("  <label for='MaloaiVe'>Mã Loại Vé:</label>");
        out.println("  <input type='text' id='MaloaiVe' name='MaloaiVe' required><br>");
        out.println("  <label for='TenloaiVe'>Tên Loại Vé:</label>");
        out.println("  <input type='text' id='TenloaiVe' name='TenloaiVe' required><br>");
        out.println("  <label for='GiaTien'>Giá Tiền:</label>");
        out.println("  <input type='text' id='GiaTien' name='GiaTien' required><br>");
        out.println("  <label for='Madichvu'>Mã Dịch Vụ:</label>");
        out.println("  <input type='text' id='Madichvu' name='Madichvu' required><br>");
        out.println("  <label for='Ghichu'>Ghi Chú:</label>");
        out.println("  <input type='text' id='Ghichu' name='Ghichu'><br>");
        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");
        out.println("<style>");
        out.println("body");
        out.println("table {border-collapse: collapse; width: 80%;}");
        out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
        out.println("th {background-color: #f2f2f2;}");
        out.println("</style>");
        out.println("</head>");
        out.println("</table>");
        String TK = req.getParameter("searchMaLoaiVe");
        if (TK == null) {
            in.displayData(out);
        }
        else{
           in.TK(req, res, out);
        }
        in.xoa(req, res, out);
                String maloaiVe = req.getParameter("MaloaiVe");
                String tenloaiVe = req.getParameter("TenloaiVe");
                String giaTien = req.getParameter("GiaTien");
                String madichvu = req.getParameter("Madichvu");
                
                 if ( maloaiVe !=null && tenloaiVe != null && giaTien != null && madichvu != null) {
                   in.nhap(req, res);
                  }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                response.setContentType("text/html");
                response.setContentType("text/html;charset=UTF-8");
                request.setCharacterEncoding("UTF-8");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Form Nhập Dữ Liệu Loại Vé</title>");
                out.println("</head>");
                out.println("<body>");
                String mlv = request.getParameter("MaloaiVe");
                out.println("<h2>Form Nhập Dữ Liệu Loại Vé</h2>");
                out.println("<form method='post'>");
                out.println("  <input type='hidden' name='MaloaiVe' value='" + mlv + "'/>");
                
                out.println("  <label for='TenaiVe'>Tên Loại Vé:</label>");
                out.println("  <input type='text' id='TenloaiVe' name='TenloaiVe' required><br>");
                out.println("  <label for='GiaTien'>Giá Tiền:</label>");
                out.println("  <input type='text' id='GiaTien' name='GiaTien' required><br>");
                out.println("  <label for='Madichvu'>Mã Dịch Vụ:</label>");
                out.println("  <input type='text' id='Madichvu' name='Madichvu' required><br>");
                out.println("  <label for='Ghichu'>Ghi Chú:</label>");
                out.println("  <input type='text' id='Ghichu' name='Ghichu'><br>");
                out.println("  <input type='submit' value='Submit'>");
                out.println("</form>");
                out.println("</body>");
                out.println("</html>");
                String maloaiVe = request.getParameter("MaloaiVe");
                String tenloaiVe = request.getParameter("TenloaiVe");
                String giaTien = request.getParameter("GiaTien");
                String madichvu = request.getParameter("Madichvu");
                String ghichu = request.getParameter("Ghichu");

                 if ( tenloaiVe != null && giaTien != null && madichvu != null) {
                   in.sua(request, response, out);  // Replace 'loaiVeServlet' with the actual instance of your servlet
                  }

    }
    
}



