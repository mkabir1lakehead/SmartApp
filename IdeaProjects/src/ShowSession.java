import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class ShowSession extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String title = "Session Tracking Example";
        HttpSession session = request.getSession(true);
        String heading;
        Integer accessCount = (Integer) session.getAttribute("accessCount");
        if(accessCount == null){
            accessCount = new Integer(0);
            heading = "Welcome, Newcomer";

        }else{
            heading = "Welcome Back";
            accessCount = new Integer(accessCount.intValue() + 1);

        }

        session.setAttribute("accessCount",accessCount);

        out.print("<html><head><title>Session Track</title></head>"
                + "<body bgcolor=\"#FDF5E6\"\n>" + "<h1 align = \"center\">"
                + heading + "</h1>\n"
                + "<h2>Ingormation on Your Session:</h2>\n"
                + "<table boder=1 align=\"center\">\n"
                + "<tr bgcolor=\"#FFAD00\">\n" + "<th>Info Type<th>Value\n"
                + "<tr>\n" + "<td>ID\n" + "<td>" + session.getId() + "\n"
                + "<tr>\n" + "<td>Creation Time\n" + "<td>" + new Date(session.getCreationTime()) + "\n"
                + "<tr>\n" + "<td>Time of Last Access\n" + "<td>" + new Date(session.getLastAccessedTime()) + "\n"
                + "<tr>\n" + "<td>Number of Previous Accesses\n" + "<td>" + accessCount + "\n" + "</table>\n" + "</body></html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        doGet(request,response);
    }
}
