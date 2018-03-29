<%@page errorPage="ErrPage.jsp" %>

<%
    String s = "123Plus";
    int i = Integer.parseInt(s);
    out.println("s=" +s +" i=" +i);
%>