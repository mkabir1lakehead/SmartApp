import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TestLifeCycleServlet extends HttpServlet {
    @Override
    public void destroy() {
        System.out.println("destroy");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {//servletcinfig配置servlet,通过web.xml配置
        System.out.println("init");
    }

    public TestLifeCycleServlet(){
        System.out.println("constructor!");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet");
    }
}
