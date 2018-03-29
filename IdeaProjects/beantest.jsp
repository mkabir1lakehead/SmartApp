<%@ page import="com.longfeizeng.*"%>

<%!
    CounterBean cd = new CounterBean();
%>
<font color="red" size="5">
    <%=cd.getCount()%>
</font>

<jsp:useBean id="cd2" class="com.longfeizeng.CounterBean" scope="page"> <!--type="java.lang.Object"-->

</jsp:useBean>

<%--<jsp:setProperty name="cd2" property="count" value="23"/>--%>
<%--cb2.setCount(23)--%>
<jsp:getProperty name="cd2" property="count"/>
<%--out.write(cb.getCount())--%>