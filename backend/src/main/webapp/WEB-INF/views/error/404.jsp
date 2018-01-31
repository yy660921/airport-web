<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%response.setStatus(200);%>


<rapid:override name="head">
    <title>404 - 页面不存在</title>
</rapid:override>

<rapid:override name="body">
    <h2>404 - 页面不存在.</h2>

    <p><a href="<c:url value="/"/>">返回首页</a></p>
</rapid:override>
<%@ include file="/WEB-INF/layouts/default.jsp" %>
