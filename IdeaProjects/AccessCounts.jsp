<%--
  Created by IntelliJ IDEA.
  User: zlf
  Date: 11/01/18
  Time: 1:36 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>
    <!--成员变量或成员方法，ｊｓｐ转化成ｓｅｒｖｌｅｔ,就一个，是单例，成员变量就声明一次-->
    <%!
        int accessCount = 0;
    %>

    <!--局部变量，每次访问每次重新声明 -->
    <%
        int accessCount2 = 0;
    %>
    <h2>Accesses to page since server reboot:
    <%= ++ accessCount %>
    <br>
    <%= ++accessCount2%>
    </h2>
</body>
</html>