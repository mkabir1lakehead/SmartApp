import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.*;

public class ShowRs extends HttpServlet {
    private Gson userGson;
    private parents_Info parents_info;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        response.setContentType("text/html");
        response.setCharacterEncoding("gb2312");
        PrintWriter out = response.getWriter();


        String params;
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        params = sb.toString();
        Gson userGson = new Gson();
        user_Info user;
        user = userGson.fromJson(params,user_Info.class);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartapp?user=root&password=123456zlf");
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select USER_INFO_FIRSTNAME,USER_INFO_PARENTSNUMBER, USER_INFO_PARENTSEMAIL from USER_INFO where USER_INFO_PHONENUMBER=\'" + user.USER_INFO_PHONENUMBER
                                    + "\' and USER_INFO_PASSWORD=\'" + user.USER_INFO_PASSWORD + "\'");
            if (rs.next()){
                response.setHeader("errorNum","0");


                userGson = new Gson();
                parents_info = new parents_Info();
                parents_info.USER_INFO_PARENTSNUMBER = rs.getString("USER_INFO_PARENTSNUMBER");
                parents_info.USER_INFO_PARENTSEMAIL = rs.getString("USER_INFO_PARENTSEMAIL");
                parents_info.USER_INFO_FIRSTNAME = rs.getString("USER_INFO_FIRSTNAME");
                String userGsonString = userGson.toJson( parents_info );
                out.write(userGsonString);

            }else{
                response.setHeader("errorNum","-1");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(rs != null){
                    rs.close();
                    rs = null;
                }
                if(stmt != null){
                    stmt.close();
                    stmt = null;
                }
                if(conn != null){
                    conn.close();
                    conn = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        doGet(request,response);

        System.out.println("visit");
    }
}

class user_Info{
    public String USER_INFO_ID;
    public String USER_INFO_PHONENUMBER;
    public String USER_INFO_EMAIL;
    public String USER_INFO_PASSWORD;

}
class parents_Info{
    public String USER_INFO_PARENTSNUMBER;
    public String USER_INFO_PARENTSEMAIL;
    public String USER_INFO_FIRSTNAME;

}
