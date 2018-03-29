import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ThreeParams extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        pw.println(request.getParameter("param1"));
        pw.println("<br/>");
        pw.println(request.getParameter("param2"));
        pw.println("<br/>");
        pw.println(request.getParameter("param3"));
        pw.println("<br/>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        /*PrintWriter pw = response.getWriter();
        pw.println(request.getParameter("param1"));
        pw.println("<br>");
        pw.println(request.getParameter("param2"));
        pw.println("<br>");
        pw.println(request.getParameter("param3"));
        pw.println("<br>");*/
        System.out.println("doPost");
        doGet(request, response);
    }
}
