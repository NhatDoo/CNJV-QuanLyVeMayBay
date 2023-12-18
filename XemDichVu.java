

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.DichVuDAO;
import DAO.menu;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@WebServlet("/XemDichVu")
public class XemDichVu extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public XemDichVu() {
        super();
    }
    DichVuDAO in = new DichVuDAO();
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
        out.println("  <label for='searchMaDichVu'>Mã Khách:</label>");
        out.println("  <input type='text' id='searchMaDichVu' name='searchMaDichVu' required>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");
        
        
        out.println("<h2>Form Nhập Dữ Liệu Dịch Vụ</h2>");
        out.println("<form method='get'>"); // Replace 'TenServletXuLyNhapDichVu' with the actual servlet URL for processing the form
        out.println("  <label for='MaDichVu'>Mã Dịch Vụ:</label>");
        out.println("  <input type='text' id='MaDichVu' name='MaDichVu' required><br>");
        out.println("  <label for='TenDichVu'>Tên Dịch Vụ:</label>");
        out.println("  <input type='text' id='TenDichVu' name='TenDichVu' required><br>");
        out.println("  <label for='GiaThucAn'>Giá Thực Ăn:</label>");
        out.println("  <input type='text' id='GiaThucAn' name='GiaThucAn' required><br>");
        out.println("  <label for='GiaLuongThuc'>Giá Lượng Thực:</label>");
        out.println("  <input type='text' id='GiaLuongThuc' name='GiaLuongThuc' required><br>");
        out.println("  <label for='TriGiaDV'>Trị Giá Dịch Vụ:</label>");
        out.println("  <input type='text' id='TriGiaDV' name='TriGiaDV' required><br>");
        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");
        out.println("<style>");
        out.println("body");
        out.println("table {border-collapse: collapse; width: 80%;}");
        out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
        out.println("th {background-color: #f2f2f2;}");
        out.println("</style>");
        out.println("</body>");
        out.println("</html>");

        
        
        String TK = req.getParameter("searchMaDichVu");
        if (TK == null) {
            in.displayData(out);
        }
        else{
            in.searchDichVu(req, res, out);
        }
        in.xoa(req, res, out);
                String mdv = req.getParameter("MaDichVu");
                String tenDichVu = req.getParameter("TenDichVu");
                String giaThucAn = req.getParameter("GiaThucAn");
                String giaLuongThuc = req.getParameter("GiaLuongThuc");
                String triGiaDV = req.getParameter("TriGiaDV");
                if (mdv !=null && tenDichVu != null && giaThucAn != null && giaLuongThuc != null && triGiaDV != null) {
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
                out.println("<title>Form Cập Nhật Dịch Vụ</title>");
                out.println("</head>");
                out.println("<body>");
                String dv = request.getParameter("MaDichVu");
                out.println("<h2>Form Cập Nhật Dịch Vụ</h2>");
                out.println("<form method='post'>");
                out.println("    <input type='hidden' name='MaDichVu' value='" + dv + "'/>");
                out.println("  <label for='TenDichVu'>Tên Dịch Vụ:</label>");
                out.println("  <input type='text' id='TenDichVu' name='TenDichVu' required><br>");
                out.println("  <label for='GiaThucAn'>Giá Thực Ăn:</label>");
                out.println("  <input type='text' id='GiaThucAn' name='GiaThucAn' required><br>");
                out.println("  <label for='GiaLuongThuc'>Giá Lượng Thực:</label>");
                out.println("  <input type='text' id='GiaLuongThuc' name='GiaLuongThuc' required><br>");
                out.println("  <label for='TriGiaDV'>Trị Giá Dịch Vụ:</label>");
                out.println("  <input type='text' id='TriGiaDV' name='TriGiaDV' required><br>");
                out.println("  <input type='submit' value='Cập Nhật'>");
                out.println("</form>");
                out.println("</body>");
                out.println("</html>");
             
                String tenDichVu = request.getParameter("TenDichVu");
                String giaThucAn = request.getParameter("GiaThucAn");
                String giaLuongThuc = request.getParameter("GiaLuongThuc");
                String triGiaDV = request.getParameter("TriGiaDV");

                if (tenDichVu != null && giaThucAn != null && giaLuongThuc != null && triGiaDV != null) {
                    in.suaDichVu(request, response, out);
                }

    }
    
}



