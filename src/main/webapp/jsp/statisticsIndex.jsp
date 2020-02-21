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
    <link rel="stylesheet" href="/css/statisticsIndex.css" type="text/css">
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
        <div class="layui-col-md12" style="width: 100%; height: 35px;" id="timeDiv">
            时间：
            <select class="slt" id="timeSelect">
                <option value="day">按日</option>
                <option value="week">按周</option>
                <option value="month">按月</option>
                <option value="display">自定义</option>
            </select>
            <input class="timeInput" id="sDate" type="date">
            <input class="timeInput" id="eDate" type="date">
            <button class="serBtn" id="serBtn" >查询</button>
            <select id="conditionSlt" class="slt hid">
                <option value="ERROR">错误</option>
                <option value="WARN">警告</option>
            </select>
        </div>
        <div class="layui-col-md12" style="width: 100%; height: 50px;" id="moduleDiv">
            模块：
        </div>
    </div>
    <div class="layui-row">
        <div class="layui-col-md2" style="margin-top: 35px;">
            <div class="error_div">错误日志总计：<span id="errorSpan"></span></div>
            <div class="warn_div">警告日志总计：<span id="warnSpan"></span></div>
        </div>
        <div class="layui-col-md10">
            <div id="errorRank" class="rankEle"></div>
            <div id="warnRank" class="rankEle"></div>
            <div class="layui-col-md2"><button id="moreRank" class="moreBtn">查看更多</button></div>
        </div>
    </div>
    <div style="height: 50px;width: 100%;"></div>
    <div class="layui-row">
        <div class="layui-col-md3">
            <div id="typePie" style="width: 285px;height:301px;"></div>
        </div>
        <div class="layui-col-md9">
            <div id="tendency" style="width: 850px;height:300px;"></div>
<%--            <div id="tendency1" style="width: 850px;height:300px;"></div>--%>
        </div>
    </div>
</div>

<script type="text/javascript">
</script>
</body>
</html>
