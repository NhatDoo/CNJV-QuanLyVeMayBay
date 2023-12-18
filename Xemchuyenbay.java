
import DAO.menu;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.chuyenbayDAO;
/**
 *
 * @author nhat
 */
@WebServlet("/Xemchuyenbay")
public class Xemchuyenbay extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public Xemchuyenbay() {
        super();
    }
    chuyenbayDAO in = new chuyenbayDAO();
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
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
        
    out.println("<h2>Tìm Kiếm Chuyến Bay</h2>");
    out.println("<form method='get'>");
    out.println("  <label for='searchMaChuyenBay'>Mã Chuyến Bay:</label>");
    out.println("  <input type='text' id='searchMaChuyenBay' name='searchMaChuyenBay' required>");
    out.println("  <input type='submit' value='Tìm Kiếm'>");
    out.println("</form>");
        
        out.println("<h2>Form Nhập Thông Tin Chuyến Bay</h2>");
        out.println("<form method='get'>"); 
        out.println("  <label for='Machuyenbay'>Mã Chuyến Bay:</label>");
        out.println("  <input type='text' id='Machuyenbay' name='Machuyenbay' required><br>");
        out.println("  <label for='Loaimaybay'>Loại Máy Bay:</label>");
        out.println("  <input type='text' id='Loaimaybay' name='Loaimaybay' required><br>");
        out.println("  <label for='Soghe'>Số Ghế:</label>");
        out.println("  <input type='text' id='Soghe' name='Soghe' required><br>");
        out.println("  <label for='MaSanbaydi'>Mã Sân Bay Đi:</label>");
        out.println("  <input type='text' id='MaSanbaydi' name='MaSanbaydi' required><br>");
        out.println("  <label for='MaSanbayden'>Mã Sân Bay Đến:</label>");
        out.println("  <input type='text' id='MaSanbayden' name='MaSanbayden' required><br>");
        out.println("  <label for='Giokhoihanh'>Giờ Khởi Hành:</label>");
        out.println("  <input type='text' id='Giokhoihanh' name='Giokhoihanh' required><br>");
        out.println("  <label for='Ngaykhoihanh'>Ngày Khởi Hành:</label>");
        out.println("  <input type='text' id='Ngaykhoihanh' name='Ngaykhoihanh' required><br>");
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
        String searchsv = req.getParameter("searchMaChuyenBay");
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Form Nhập Dữ Liệu</title>");
        out.println("</head>");
        out.println("<body>");

        String maChuyenBay = request.getParameter("Machuyenbay");

        out.println("<h2>Form Nhập Dữ Liệu Chuyến Bay</h2>");
        out.println("<form method='post'>");
        out.println("  <input type='hidden' name='Machuyenbay' value='" + maChuyenBay + "'/>");
        out.println("  <label for='Loaimaybay'>Loại Máy Bay:</label>");
        out.println("  <input type='text' id='Loaimaybay' name='Loaimaybay' required><br>");
        out.println("  <label for='Soghe'>Số Ghế:</label>");
        out.println("  <input type='text' id='Soghe' name='Soghe' required><br>");
        out.println("  <label for='MaSanbaydi'>Mã Sân Bay Đi:</label>");
        out.println("  <input type='text' id='MaSanbaydi' name='MaSanbaydi' required><br>");
        out.println("  <label for='MaSanbayden'>Mã Sân Bay Đến:</label>");
        out.println("  <input type='text' id='MaSanbayden' name='MaSanbayden' required><br>");
        out.println("  <label for='Giokhoihanh'>Giờ Khởi Hành:</label>");
        out.println("  <input type='text' id='Giokhoihanh' name='Giokhoihanh' required><br>");
        out.println("  <label for='Ngaykhoihanh'>Ngày Khởi Hành:</label>");
        out.println("  <input type='text' id='Ngaykhoihanh' name='Ngaykhoihanh' required><br>");
        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");
        in.sua(request, response, out);
    }
}
