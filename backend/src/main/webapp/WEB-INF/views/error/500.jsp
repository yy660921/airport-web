<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%response.setStatus(200);%>

<%
    Throwable ex = null;
    ex = exception;
    if (request.getAttribute("javax.servlet.error.exception") != null) {
        ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
    }

    //记录日志
    Logger logger = LoggerFactory.getLogger("500.jsp");
    logger.error(ex.getMessage(), ex);
%>

<rapid:override name="head">
    <title>500 - 系统发生内部错误</title>
</rapid:override>

<rapid:override name="body">
    <h2>500 - 系统发生内部错误</h2>

    <h3><%=ex.getMessage() %>
    </h3>

    <p><a href="<c:url value="/"/>">返回首页</a></p>
</rapid:override>
<%@ include file="/WEB-INF/layouts/default.jsp" %>
