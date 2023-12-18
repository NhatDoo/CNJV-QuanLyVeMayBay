
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.SanbaydenDAO;
import DAO.menu;
@WebServlet("/Xemsanbayden")
public class Xemsanbayden extends HttpServlet {
 private static final long serialVersionUID = 1L;
    public Xemsanbayden() {
        super();
    }
    SanbaydenDAO in = new SanbaydenDAO();
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
        
        // Tạo biểu mẫu nhập liệu
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Form Nhập Thông Tin Sân Bay Đến</title>");
        out.println("</head>");
        out.println("<body>");
          menu menu = new menu();
        menu.htmenu(res, out);
        out.println("<h2>Tìm Kiếm Sân Bây Đến </h2>");
        out.println("<form method='get'>");
        out.println("  Mã Sân Bay Đến : <input type='text' name='searchMaSanBayDen' required>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");

        
        out.println("<h2>Form Nhập Thông Tin Sân Bay Đến</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='MaSanbayden'>Mã Sân Bay Đến:</label>");
        out.println("  <input type='text' id='MaSanbayden' name='MaSanbayden' required><br>");
        out.println("  <label for='TenSanbayden'>Tên Sân Bay Đến:</label>");
        out.println("  <input type='text' id='TenSanbayden' name='TenSanbayden' required><br>");
        out.println("  <label for='TinhThanhPho'>Tỉnh/Thành Phố:</label>");
        out.println("  <input type='text' id='TinhThanhPho' name='TinhThanhPho' required><br>");
        out.println("  <label for='QuocGia'>Quốc Gia:</label>");
        out.println("  <input type='text' id='QuocGia' name='QuocGia' required><br>");
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
        
        String masanbaysearch = req.getParameter("searchMaSanBayDen");
        String masanbaydenStr = req.getParameter("MaSanbayden");
        String tenSanbay = req.getParameter("TenSanbayden");
        String tinhThanhPho = req.getParameter("TinhThanhPho");
        String quocGia = req.getParameter("QuocGia");
        
        if(masanbaysearch == null)
        {
        in.displayData(out);
        }
        else
        {
        in.TK(req, res, out);
        }
        in.xoa(req, res, out);
        if(masanbaydenStr !=null && tenSanbay != null && tinhThanhPho != null && quocGia != null)
        {
        in.nhap(req, res);
        }
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

        String Masanbayden = request.getParameter("MaSanbayden");
        out.println("<h2>Form Nhập Dữ Liệu Sân Bay Đến</h2>");
        out.println("<form method='post'>");
        out.println("  <input type='hidden' name='MaSanbayden' value='" + Masanbayden + "'/>");
        out.println("  <label for='TenSanbayden'>Tên Sân Bay Đến:</label>");
        out.println("  <input type='text' id='TenSanbayden' name='TenSanbayden' required><br>");
        out.println("  <label for='TinhThanhPho'>Tỉnh/Thành Phố:</label>");
        out.println("  <input type='text' id='TinhThanhPho' name='TinhThanhPho' required><br>");
        out.println("  <label for='QuocGia'>Quốc Gia:</label>");
        out.println("  <input type='text' id='QuocGia' name='QuocGia' required><br>");
        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
        in.suaSanBayDen(request, response, out);
    }

}
