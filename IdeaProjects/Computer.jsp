<%@ page language="java" %>

<%
    String valuse1 = request.getParameter("value1");
    String valuse2 = request.getParameter("value2");

%>

<% if(request.getParameter("computer").equals("division")){ %>
    <jsp:include page="dlvide.jsp" flush="true">
        <jsp:param name="v1" value="<%=valuse1%>"/>
        <jsp:param name="v2" value="<%=valuse2%>"/>
    </jsp:include>
<% ) else { %>
    <%@include file="multipi.jsp"%>
<%}%>