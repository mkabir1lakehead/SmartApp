import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public class Test {
    public static void main(String[] args) throws Exception{
        Socket s = new Socket("www.baidu.com",80);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
        pw.println("GET / HTTP/1.1");
        pw.println("Host: www.baidu.com");
        pw.println("Content-Type: text/html");
        pw.println();
        pw.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String str = "";
        while ((str = br.readLine())!= null){
            System.out.println(str);
        }
        br.close();
        pw.close();
        s.close();
    }
}
