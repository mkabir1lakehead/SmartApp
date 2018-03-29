import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

public class ShowParameters extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws HTTPException, IOException{
        response.setContentType("text/html;charset=gb2312");
        PrintWriter pw = response.getWriter();
        String title = "Reading All Request Parameters";
        pw.println("<html><head><title>读取所有参数</title></head>"
            +"<body bgcolor='#FDF5E6'>\n" + "<h1 align=center>" + title
            + "</h1>\n" + "<table border=1 align=center>\n"
            + "<tr bgcolor = \"#FFAD00\">\n"
            + "<th>Parameter Name<th>Parameter Value(s)");
        Enumeration paramNames = request.getParameterNames();
        while(paramNames.hasMoreElements()){
            String paramName = (String) paramNames.nextElement();
            pw.println("<tr><td>" + paramName + "\n<td>");
            String [] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1){
                String paramValue = paramValues[0];
                if(paramValue.length() == 0)
                    pw.println("<I>No Value</I>");
                else
                    pw.println(paramValue);
            }else{
                pw.println("<ul>");
                for (int i = 0;i<paramValues.length;i++){
                    pw.println("<li>" + paramValues[i]);
                }
                pw.println("</ul>");
            }
        }
    }

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws HTTPException, IOException {
        doGet(request, response);
    }
}
