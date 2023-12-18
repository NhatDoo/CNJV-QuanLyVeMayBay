

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.VeDAO;
import DAO.menu;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@WebServlet("/XemSoVe")
public class XemSoVe extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public XemSoVe() {
        super();
    }
    VeDAO in = new VeDAO();
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
        out.println("  <label for='searchSove'>Mã Khách:</label>");
        out.println("  <input type='text' id='searchSove' name='searchSove' required>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");
        
        out.println("<h2>Form Nhập Dữ Liệu Khách Hàng</h2>");
        out.println("<form method='get'>");
        out.println("   <label for='Sove'>Số vé:</label>");
        out.println("  <input type='text' id='Sove' name='Sove' required><br>");
        
        out.println("  <label for='Makhach'>Mã Khách:</label>");
        out.println("  <input type='text' id='Makhach' name='Makhach' required><br>");
        
        out.println("  <label for='Machuyenbay'> Mã chuyến bay:</label>");
        out.println("  <input type='text' id='Machuyenbay' name='Machuyenbay' required><br>");
        
        out.println("  <label for='Maloaive'>Mã loại vé :</label>");
        out.println("  <input type='text' id='Maloaive' name='Maloaive' required><br>");
     
        out.println("  <label for='NgayDatve'>Ngày Đặt vé :</label>");
        out.println("  <input type='text' id='NgayDatve' name='NgayDatve' required><br>");
        
         out.println("  <label for='Ngaynhanve'>Ngày nhận vé :</label>");
        out.println("  <input type='text' id='Ngaynhanve' name='Ngaynhanve' required><br>");
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
        String searchsv = req.getParameter("searchSove");
        if (searchsv == null) {
            in.displayData(out);
        }
        else{
            in.TK(req, res, out);
        }
        in.xoa(req, res, out);
        in.nhap(req, res); 
               
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
        out.println("<title>Form Nhập Dữ Liệu</title>");
        out.println("</head>");
        out.println("<body>");
        String sv = request.getParameter("Sove");
        out.println("<h2>Form Nhập Dữ Liệu Khách Hàng</h2>");
        out.println("<form method='post'>");
        out.println("  <input type='hidden' name='Sove' value='" + sv + "'/>");
        out.println("  <label for='Makhach'>Mã Khách:</label>");
        out.println("  <input type='text' id='Makhach' name='Makhach' required><br>"); 
        out.println("  <label for='Machuyenbay'> Mã chuyến bay:</label>");
        out.println("  <input type='text' id='Machuyenbay' name='Machuyenbay' required><br>");
        out.println("  <label for='Maloaive'>Mã loại vé :</label>");
        out.println("  <input type='text' id='Maloaive' name='Maloaive' required><br>");
        out.println("  <label for='NgayDatve'>Ngày Đặt vé :</label>");
        out.println("  <input type='text' id='NgayDatve' name='NgayDatve' required><br>");
        out.println("  <label for='Ngaynhanve'>Ngày nhận vé :</label>");
        out.println("  <input type='text' id='Ngaynhanve' name='Ngaynhanve' required><br>");
        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
        String mk = request.getParameter("Makhach");
        String mcb = request.getParameter("Machuyenbay");
        String mlv = request.getParameter("Maloaive");
        String ndv = request.getParameter("NgayDatve");
        String nnv = request.getParameter("Ngaynhanve");
        if(mk != null && mcb !=null && mlv !=null && ndv!=null && nnv!=null)
        {
        in.sua(request, response, out);
        }
    }
    
}



