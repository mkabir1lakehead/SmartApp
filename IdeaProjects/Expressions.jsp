<html>
<head>
    <title>JSP Expressions</title>
    <meta name="author" content="Marty Hall">
    <meta name="keywords" content="JSP,expressions,JavaServer,Pages,servlets">
    <meta name="description" content="A quick example of JSP expressions.">
</head>

<!--html注释，在浏览器查看源代码可见-->
<%--jsp注释，客户端不可见，程序员可见--%>

<body>
<h2>JSP Expressions</h2>

<ul>
    <li>Current time: <%= new java.util.Date()%></li>
    <li>Your hostname: <%=request.getRemoteHost()%></li>
    <li>Your session ID: <%=session.getId()%></li>
    <li>The <CODE>testParam</CODE> from parameter: <%= request.getParameter("testParam")%></li>
</ul>
</body>
</html>