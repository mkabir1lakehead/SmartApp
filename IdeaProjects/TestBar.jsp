<%@page contentType="text/html;charset=gb2312" %>
<html>
    <head>
        <title>TestBar.jsp</title>
    </head>
    <body>
        <table width="100%">
            <tr>
                <%--������������ ֮��ͳһ���б���--%>
                <td><%@include file="TitleBar.jsp"%></td>
                <td><% out.println("<p>�����û���ʾ��</p>");%></td>
            </tr>
        </table>
    </body>
</html>