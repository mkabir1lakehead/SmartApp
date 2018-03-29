import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SetCookies extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        for(int i = 0; i<3; i ++){
            Cookie cookie = new Cookie("Session-Cookie-"+i,"Cookie-Value0-S");
            response.addCookie(cookie);
            cookie = new Cookie("Persistent-Cookie-"+i,"Cookie-Value-p"+i);
            cookie.setMaxAge(3600);
            response.addCookie(cookie);
        }
        response.setContentType("text/html;charset=gb2312");
        PrintWriter pw = response.getWriter();
        String title = "Setting Cookie";
        pw.println("<html><head><title>设置Cookie</title></head>"
                +"<body bgcolor='#FDF5E6'>\n" + "<h1 align=center>" + title+ "<h1>\n"
                + "There are six cookies associated with this page.\n"
                +"To see them,visit the\n" + "<a href=\"ShowCookies\">\n"
                + "<code>ShowCookies</code> servlet</a>.\n"+"<p>\n"
                + "Three of the cookies are associated only with the\n"
                + "current session, while three are persistent.\n"
                + "Quit the browser, restart, and return to the \n"
                + "<code>ShowCookies</code> servlet to verify thar\n"
                + "the three long-lived ones persist across session.\n"
                + "</body></html>");
    }
}
