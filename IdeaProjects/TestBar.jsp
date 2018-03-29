<%@page contentType="text/html;charset=gb2312" %>
<html>
    <head>
        <title>TestBar.jsp</title>
    </head>
    <body>
        <table width="100%">
            <tr>
                <%--加入ｉｎｃｌｕｄｅ 之后统一进行编译--%>
                <td><%@include file="TitleBar.jsp"%></td>
                <td><% out.println("<p>这是用户显示区</p>");%></td>
            </tr>
        </table>
    </body>
</html>