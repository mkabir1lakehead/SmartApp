import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class SessionInfoServlet extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        HttpSession mysession = request.getSession(true);//false:get session if unexist ,don't create ;true: get ,if not exist create new
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Session Information Servlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h3>Session Information</h3>");
        out.println("New Session: " + mysession.isNew());
        out.println("<br>Session ID: " + mysession.getId());
        out.println("<br>Session Creation Time: " + new java.util.Date(mysession.getCreationTime()));
        out.println("<br>Session Last Accessed Time: " + new java.util.Date(mysession.getLastAccessedTime()));

        out.println("<h3>Request Information</h3>");
        out.println("Session ID from Request: " + request.getRequestedSessionId());
        out.println("<br>Session ID via Cookie: " + request.isRequestedSessionIdFromCookie());
        out.println("<br>Session ID via rewritten URL: " + request.isRequestedSessionIdFromURL());
        out.println("<br>Valid Session ID: " + request.isRequestedSessionIdValid());
        out.println("<br><a href=" +  response.encodeURL("SessionInfoServlet") + ">refresh</a>");
        out.println("</body></html>");
        out.println("");
        out.println("");
        out.println("");
        out.println("");







    }

}
