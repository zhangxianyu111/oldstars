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
    <link rel="stylesheet" href="/ztree/css/zTreeStyle.css" type="text/css">
    <link rel="icon" href="/favicon.ico">
    <script src="/ztree/jquery-1.4.4.min.js"></script>
    <script src="/ztree/jquery.ztree.core.js"></script>
    <script src="<%=basePath%>assets/layui.all.js"></script>
    <title>日志管理后台</title>
</head>
<body class="layui-view-body">
<div class="layui-content">
    <div class="layui-page-header">
        <div class="pagewrap">
                <span class="layui-breadcrumb">
                  <a href="">日志级别管理</a>
                  <a><cite>级别管理</cite></a>
                </span>
        </div>
    </div>
    <div class="layui-row">
        <div>
            <!--显示当前选中的包或类的日志级别-->
            <div layui-form layui-form-item>
                <span id="fullName">^_^</span>
                  =  
                <span id="level">^_^</span>
            </div>
            <!---更改当选中的包或类的日志级别 - 下拉选择列表--->
            <div>
                <select id="updateLevelSelect">
                    <option selected="selected" disabled="disabled"  style='display:none' value=''></option>
                    <option value="OFF">OFF</option>
                    <option value="ERROR">ERROR</option>
                    <option value="WARN">WARN</option>
                    <option value="INFO">INFO</option>
                    <option value="DEBUG">DEBUG</option>
                    <option value="TRACE">TRACE</option>
                    <option value="ALL">ALL</option>
                </select>
                <!--提交按钮-->
                <button id="modifyLogLevelBtn" type="button" class="btn  btn-bold ">修改</button>
            </div>
        </div>
        <!--class 树形目录结构-->
        <div>
            <ul id="logLeverTree" class="ztree"></ul>
        </div>

    </div>
</div>

<script type="text/javascript">
    var treeName="logLeverTree";
    var zTreeObj;
    var fullName = $("#fullName");
    var level = $("#level");
    var updateLevelSelect = $("#updateLevelSelect");
    /**当前选中的节点**/
    var currentTreeNode;
    var setting = {
        data: {
            key:{
                title:"type"
            }
        },
        callback: {
            onClick:function(e,treeId,treeNode){
                currentTreeNode = treeNode;
                $.post("<%=basePath%>logLevel/get/level",{"target":currentTreeNode.fullName}, function(data) {
                    var result = $.parseJSON(data);
                    if(result.code === '0'){
                        updateLevelSelect.val(result.singleData.level);
                        fullName.html(result.singleData.name);
                        level.html(result.singleData.level);
                        if(result.singleData.level.indexOf(".") > -1){
                            var firstStr = currentTreeNode.name.substring(0,1);
                            if(firstStr == firstStr.toUpperCase()){

                            }
                        }

                    }else{
                        fullName.html("error");
                        level.html("");
                        currentTreeNode = null;
                        layer.msg("请求失败："+result.msg, {icon: 5, time: 2000});
                    }
                });


            }
        }
    };

    $("#modifyLogLevelBtn").click(function(){
        if(!currentTreeNode){
            layer.msg("请选择要修改的日志包或类", {icon: 5, time: 2000});
            return false;
        }
        var value =updateLevelSelect.val();
        var target = currentTreeNode.fullName;
        $.post("<%=basePath%>/logLevel/level",{"target":target,"level":value}, function(data) {
            var result =  $.parseJSON(data);
            if(result.code === '0'){
                fullName.html(result.data.name);
                level.html(result.data.afterLevel);
                layer.msg("操作成功!更改前级别"+result.data.beforeLevel+","+"更改后级别："+result.data.afterLevel, {icon: 6, time: 2000});
            } else {
                layer.msg(result.msg, {icon: 5, time: 2000});
            }
        });
    });


    $(document).ready(function(){
        $.post("<%=basePath%>/logLevel/tree", function(data) {
            var result =  $.parseJSON(data);
            if(result.code === '0'){
                zTreeObj = $.fn.zTree.init($("#"+treeName), setting,result.data );
                zTreeObj.expandAll(false);
            }
        });

    });
</script>
</body>
</html>
