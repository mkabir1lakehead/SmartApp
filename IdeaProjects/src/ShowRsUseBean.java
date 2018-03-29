import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ShowRsUseBean extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{

        response.setContentType("text/html");
        response.setCharacterEncoding("gb2312");
        PrintWriter out = response.getWriter();

        out.println("<table border=1>");
        out.println("<tr><td>UserName</td><td>UserPassword</td></tr>");

        Connection conn = DB.getConn();
        Statement stmt = DB.getStatement(conn);
        String sql = "select * from UserInfo";
        ResultSet rs = DB.getResultSet(stmt,sql);

        try {
            while (rs.next()){
                out.println("<tr>");
                out.println("<td>" + rs.getString("userName") + "</td>"
                        + "<td>" + rs.getString("userPassword") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }catch (SQLException e){
            e.printStackTrace();
        }

        DB.close(rs);
        DB.close(stmt);
        DB.close(conn);

    }
}
