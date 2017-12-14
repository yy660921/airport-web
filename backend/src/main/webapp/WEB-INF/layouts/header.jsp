<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="stx" value="${pageContext.request.servletPath}"/>
<style>
    #header_content a{
        color: white;
        font-size: 15px;
    }
    .newbackground{
        border: 1px solid rgba(255,255,255,0.3);
        color: white;
        background-color: rgba(255,255,255,0.1);
    }
</style>
<div id="header_content"  >
    <nav>
        <div class="container-fluid" style="display: inline-block;">
            <div class="navbar-header" id="headImg" >
                <a class="navbar-brand" style="margin-top: -20px;" href='<c:url value="/"></c:url>'>
                    <img src="${ctx}/static/images/logo_white.png" width="150" alt="ring">
                </a>
            </div>
            <div class="collapse navbar-collapse col-md-6"  id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                </ul>
            </div>
        </div><!-- /.container-fluid -->
    </nav>
</div>
