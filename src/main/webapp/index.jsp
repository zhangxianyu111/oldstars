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
    <link rel="stylesheet" href="<%=basePath%>assets/css/admin.css">
    <link rel="icon" href="/favicon.ico">
    <title>日志管理后台</title>
</head>
<body>
<div class="layui-layout layui-layout-admin">
    <div class="layui-header custom-header">
        <ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item slide-sidebar" lay-unselect>
                <a href="javascript:;" class="icon-font"><i class="ai ai-menufold"></i></a>
            </li>
        </ul>
    </div>

    <div class="layui-side custom-admin">
        <div class="layui-side-scroll">

            <div class="custom-logo">
                <img src="<%=basePath%>assets/images/logo.png" alt=""/>
                <h1>LOG</h1>
            </div>
            <ul id="Nav" class="layui-nav layui-nav-tree">
                <li class="layui-nav-item">
                    <a href="javascript:;">
                        <i class="layui-icon">&#xe612;</i>
                        <em>实时日志分析</em>
                    </a>
                    <dl class="layui-nav-child">
                        <dd><a href="/log/logs">日志类型</a></dd>
                        <dd><a href="">日志来源</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">
                        <i class="layui-icon">&#xe612;</i>
                        <em>检索</em>
                    </a>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">
                        <i class="layui-icon">&#xe612;</i>
                        <em>告警</em>
                    </a>

                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">
                        <i class="layui-icon">&#xe612;</i>
                        <em>统计分析</em>
                    </a>

                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">
                        <i class="layui-icon">&#xe612;</i>
                        <em>配置管理</em>
                    </a>
                    <dl class="layui-nav-child">
                        <dd><a href="/qrtzJobDetails/qrtzIndex">动态定时任务</a></dd>
                        <dd><a href="/logLevel/level/toList">日志级别管理</a></dd>
                    </dl>
                </li>


            </ul>

        </div>
    </div>

    <div class="layui-body">
        <div class="layui-tab app-container" lay-allowClose="true" lay-filter="tabs">
            <ul id="appTabs" class="layui-tab-title custom-tab"></ul>
            <div id="appTabPage" class="layui-tab-content"></div>
        </div>
    </div>

    <div class="mobile-mask"></div>
</div>
<script src="<%=basePath%>assets/layui.js"></script>
<script src="<%=basePath%>js/index.js" data-main="home"></script>
</body>
</html>
