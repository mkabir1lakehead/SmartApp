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

public class Register extends HttpServlet {
    private Gson userGson;

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
            rs = stmt.executeQuery("select USER_INFO_PARENTSNUMBER, USER_INFO_PARENTSEMAIL from USER_INFO where USER_INFO_PHONENUMBER=\'" + user.USER_INFO_PHONENUMBER
                    + "\' and USER_INFO_PASSWORD=\'" + user.USER_INFO_PASSWORD + "\'");
            if (rs.next()){
                response.setHeader("errorNum","-1");




            }else{
                response.setHeader("errorNum","0");
                String userFirstName = user.USER_INFO_FIRSTNAME;
                String userLastName = user.USER_INFO_LASTNAME;
                String userPwd = user.USER_INFO_PASSWORD;
                String userPhone = user.USER_INFO_PHONENUMBER;

                Boolean finish = stmt.execute("insert into USER_INFO(USER_INFO_FIRSTNAME,USER_INFO_LASTNAME,USER_INFO_PASSWORD,USER_INFO_PHONENUMBER) values(\'"+ userFirstName+"\',\'"+userLastName+"\',\'"+userPwd+"\',\'"+userPhone+"\')");

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
                }else if(rs == null){
                    rs.close();
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

    class user_Info{
        private String USER_INFO_ID;
        private String USER_INFO_FIRSTNAME;
        private String USER_INFO_LASTNAME;
        private String USER_INFO_PHONENUMBER;
        private String USER_INFO_EMAIL;
        private String USER_INFO_PASSWORD;
        private String USER_INFO_PARENTSNUMBER;
        private String USER_INFO_PARENTSEMAIL;

    }
}



