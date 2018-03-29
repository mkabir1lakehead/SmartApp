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

public class Supervise extends HttpServlet {
    private Gson userGson;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        user = userGson.fromJson(params, user_Info.class);


        response.setHeader("errorNum", "0");
        String userFirstName = user.USER_INFO_FIRSTNAME;
        String userparentnum = user.USER_INFO_PARENTSNUMBER;
        String app = user.APP;

        String[] args = new String[] { "python", "/home/zlf/Downloads/supervise.py", userFirstName, userparentnum, app};  //传参
        Process pr = Runtime.getRuntime().exec(args);
        BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line;

        while ((line = in.readLine()) != null) {
//                line = decodeUnicode(line);
            System.out.println(line);
        }
        in.close();
        try {
            pr.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");

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
        private String APP;

    }
}
