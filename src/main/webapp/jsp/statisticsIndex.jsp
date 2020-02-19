<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=basePath%>assets/css/layui.css">
    <link rel="stylesheet" href="<%=basePath%>assets/css/view.css"/>
    <link rel="stylesheet" href="/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="icon" href="/favicon.ico">
    <script src="/ztree/jquery-1.4.4.min.js"></script>
    <script src="/ztree/jquery.ztree.core.js"></script>
    <script src="/assets/layui.all.js"></script>
    <script src="/js/prevLog/echarts.min.js"></script>
    <script src="/js/prevLog/BaseJqueryJs.js"></script>
    <script src="/js/prevLog/statisticsIndex.js"></script>
    <title>日志管理后台</title>
</head>
<body class="layui-view-body">
<div class="layui-content">
    <div class="layui-row">
        <div id="main" style="width: 285px;height:301px;"></div>
<%--    </div>--%>
<%--    <div class="layui-row">--%>
        <div id="main0" style="width: 1053px;height:500px;"></div>
        <div id="main1" style="width: 1053px;height:500px;"></div>
    </div>
</div>

<script type="text/javascript">
</script>
</body>
</html>
