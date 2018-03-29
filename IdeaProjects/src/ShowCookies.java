import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ShowCookies extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html;charset=gb2312");
        PrintWriter out = response.getWriter();
        String title = "Active Cookies";
        out.println("<html><head><title>get client cookies</title></head>"
                    + "<Body bgcoclor=\"#FDF5E6\">\n" + "<h1 align=\"center\">"
                    + title + "<h1>\n" + "<table border=1 align=\"center\">\n"
                    + "<tr bgcolor=\"#FFAD00\">\n" + "<th>Cookie Name\n"
                    + "<th>Cookie Value");
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            Cookie cookie;
            for(int i = 0; i<cookies.length; i++){
                cookie = cookies[i];
                out.println("<tr>\n" + "<td>" + cookie.getName() + "<td>\n"
                            + "<td>" + cookie.getValue() + "</td></tr>\n");
            }
        }
        out.println("</table></body></html>");
    }
}
