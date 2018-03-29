<html>
<head>
    <title>forward.jsp</title>
</head>
<body bgcolor="green">
welcome to here!
<jsp:forward page="forforward.jsp">
    <jsp:param name="name" value="m"/>
    <jsp:param name="oldName" value='<%=request.getParameter("name")%>'/>
    <jsp:param name="roles" value="manager"/>
</jsp:forward>
</body>
</html>