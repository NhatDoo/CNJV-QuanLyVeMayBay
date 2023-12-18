
package DAO;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

public class menu {
    public void htmenu(HttpServletResponse res,PrintWriter out)
    {   
        out.println("<div style='display: flex; justify-content: space-around; background-color: #f1f1f1; padding: 10px;'>");
        out.println("<a href='XemDS' style='text-decoration: none; color: #333;'>Khách Hàng</a>");
        out.println("<a href='XemSoVe' style='text-decoration: none; color: #333;'>Vé</a>");
        out.println("<a href='XemDichVu' style='text-decoration: none; color: #333;'>Dịch Vụ</a>");
        out.println("<a href='XemLoaive' style='text-decoration: none; color: #333;'>loại Vé</a>");
        out.println("<a href='Xemchuyenbay' style='text-decoration: none; color: #333;'>Chuyến bay </a>");
        out.println("<a href='Xemsanbaydi' style='text-decoration: none; color: #333;'>Sân bay đi </a>");
        out.println("<a href='Xemsanbayden' style='text-decoration: none; color: #333;'>Sân bay đến</a>");
        out.println("<a href='index.jsp' style='text-decoration: none; color: #333;'>Đăng xuất </a>");
        out.println("</div>");
    
    }
}
