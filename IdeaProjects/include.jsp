<html>
    <head>
        <title>include test</title>
    </head>
    <body bgcolor="white">
        <font color="blue">
            The current date and time are:
            <%@include file="date.jsp"%>
            <jsp:include page="date.jsp" flush="true"></jsp:include>
        </font>
    </body>
</html>