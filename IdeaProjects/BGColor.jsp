<html>
    <head>
        <title>Color Testing</title>
    </head>

    <!--html注释，在浏览器查看源代码可见-->
    <%--jsp注释，客户端不可见，程序员可见--%>

    <%
        String bgColor = request.getParameter("bgColor");
        boolean hasExplicitColor;
        if (bgColor != null){
            hasExplicitColor = true;

        }else{
            hasExplicitColor = false;
            bgColor = "white";
        }
    %>

    <body bgcolor="<%=bgColor%>">
        <h2 align="center">Color Testing</h2>

        <%
            if (hasExplicitColor){
                out.println("You suppiled an explicit background color of " + bgColor + ".");
            }else{
                out.println("request add bgColor=...");
            }
        %>
    </body>
</html>