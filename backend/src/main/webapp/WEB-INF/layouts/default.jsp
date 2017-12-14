<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <title><rapid:block name="title">weibo</rapid:block></title>
    <meta name="google-site-verification"
          content="yI46yfk4AYDy6m2NugDh-YcYcobzB7qFw9SaHuJyuoU"/>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>

    <link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
    <link href="${ctx}/static/node_modules/bootstrap/dist/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link id="theme-css" type="text/css" rel="stylesheet"/>
    <link href="${ctx}/static/vendor/js/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css" type="text/css"
          rel="stylesheet"/>
    <link href="${ctx}/static/vendor/js/jquery-validation/1.11.1/validate.css" type="text/css" rel="stylesheet"/>
    <link href="${ctx}/static/dist/css/default.min.css" type="text/css" rel="stylesheet"/>

    <script src="${ctx}/static/node_modules/jquery/dist/jquery.min.js" type="text/javascript"></script>

    <script data-main="${ctx}/static/src/js/main" src="${ctx}/static/vendor/js/common/require.min.js"></script>
    <script src="${ctx}/static/src/js/main.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $(".container").css("min-height", document.body.clientHeight - 70);
            window.onresize=function(){
                $(".container").css("min-height", document.body.clientHeight - 70);
            } ;
        });
    </script>
    <rapid:block name="head">

    </rapid:block>
    <%-- <sitemesh:head/> --%>
</head>

<body>
    <header style="height: 5%;">
        <%@ include file="/WEB-INF/layouts/header.jsp" %>
    </header>

    <div id="wrapper">
        <div>
            <div id="content">
                <div class="container" style="min-height: 879px;">
                    <div id="content" style="margin-top: 40px;padding: 20px 10px 0 10px;box-shadow: 0 0 4px 1px rgba(153,153,153,.5);">
                        <rapid:block name="body">
                        </rapid:block>
                    </div>
                </div>
                <%@ include file="/WEB-INF/layouts/footer.jsp" %>
            </div>
        </div>
    </div>
</body>
</html>
