import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TestServletContext extends HttpServlet{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        response.setContentType("text/html;charset=gb2312");
        PrintWriter out = response.getWriter();
        ServletContext application = this.getServletContext();
        Integer accessCount = (Integer)application.getAttribute("accessCount");
        if (accessCount == null){
            accessCount = new Integer(0);

        }else{
            accessCount = new Integer(accessCount.intValue() + 1);
        }

        application.setAttribute("accessCount", accessCount);

        out.println("<html><head><title>Session Track</title></head>"
                    + "<body bgcolor=\"#FDF5E6\">\n" + "<h1 align=\"center\">"
                    + accessCount + "\n" + "</h1>\n" + "</body></html>");
    }

}
