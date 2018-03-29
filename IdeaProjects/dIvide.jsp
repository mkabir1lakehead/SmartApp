<html>
<head>
    <title>
        Divide
    </title>
    <meta http-equiv="Content-Type" content="text/html;charset=gb2312">
</head>
<body bgcolor="#FFFFFF">
<center>
    <h1>
        <%
            try{
                float dividend = Float.parseFloat(request.getParameter("v1"));
                float divisor = Float.parseFloat(request.getParameter("v2"));
                double result = dividend/divisor;
                %>
                <%=result%>
                <%
            }
            catch(Exception e){
                out.println("ileagle dividend or divisor");
            }
        %>
    </h1>
</center>
</body>
</html>