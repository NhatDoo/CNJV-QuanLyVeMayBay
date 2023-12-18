

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.KhachDAO;
import DAO.menu;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@WebServlet("/XemDS")
public class XemDS extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public XemDS() {
        super();
    }
    KhachDAO in = new KhachDAO();
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<body>");
        
        menu menu = new menu();
        menu.htmenu(res, out);
        
        out.println("<h2>Tìm Kiếm Khách Hàng</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='searchMaKhach'>Mã Khách:</label>");
        out.println("  <input type='text' id='searchMaKhach' name='searchMaKhach' required>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");
        
        out.println("<h2>Form Nhập Dữ Liệu Khách Hàng</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='MaKhach'>Mã Khách:</label>");
        out.println("  <input type='text' id='MaKhach' name='MaKhach' required><br>");
        out.println("  <label for='hoKhach'>Họ Khách:</label>");
        out.println("  <input type='text' id='HoKhach' name='HoKhach' required><br>");
        out.println("  <label for='tenKhach'>Tên Khách:</label>");
        out.println("  <input type='text' id='TenKhach' name='TenKhach' required><br>");
        out.println("  <label for='cmnd'>CMND:</label>");
        out.println("  <input type='text' id='CMND' name='CMND' required><br>");
        out.println("  <label for='soDienThoai'>Số Điện Thoại:</label>");
        out.println("  <input type='text' id='SoDienThoai' name='SoDienThoai' required><br>");
        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");
        out.println("</body>");
        out.println("<style>");
        out.println("body");
        out.println("table {border-collapse: collapse; width: 80%;}");
        out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
        out.println("th {background-color: #f2f2f2;}");
        out.println("</style>");
        out.println("</head>");
        out.println("</table>");
        out.println("</body></html>");
        
        
        String searchMaKhach = req.getParameter("searchMaKhach");
        if (searchMaKhach == null) {
            in.displayData(out);
        }
        else{
            in.TK(req, res, out);
        }
        in.xoa(req, res, out);
        in.nhap(req, res); 
               
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setContentType("text/html");
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Form Nhập Dữ Liệu</title>");
        out.println("</head>");
        out.println("<body>");
        String maKhach = request.getParameter("MaKhach");
        out.println("<h2>Form Nhập Dữ Liệu Khách Hàng</h2>");
        out.println("<form method='post'>");
       
        out.println("  <input type='hidden' name='MaKhach' value='" + maKhach + "'/>");
        out.println("  <label for='hoKhach'>Họ Khách:</label>");
        out.println("  <input type='text' id='HoKhach' name='HoKhach' required><br>");

        out.println("  <label for='tenKhach'>Tên Khách:</label>");
        out.println("  <input type='text' id='TenKhach' name='TenKhach' required><br>");

        out.println("  <label for='cmnd'>CMND:</label>");
        out.println("  <input type='text' id='CMND' name='CMND' required><br>");

        out.println("  <label for='soDienThoai'>Số Điện Thoại:</label>");
        out.println("  <input type='text' id='SoDienThoai' name='SoDienThoai' required><br>");

        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");
        
        out.println("</body>");
        out.println("</html>");
        String tenkhach = request.getParameter("TenKhach");
        String hokhach = request.getParameter("HoKhach");
        String CMND = request.getParameter("CMND");
        String std = request.getParameter("SoDienThoai");
        if(tenkhach!=null && hokhach != null && CMND !=null && std !=null)
        {
        in.sua(request, response, out);
        }
        
    }
    
}



