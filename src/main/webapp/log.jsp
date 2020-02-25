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
    <link rel="icon" href="/favicon.ico">
    <title>日志管理后台</title>
</head>
<body class="layui-view-body">
<div class="layui-content">
    <div class="layui-page-header">
        <div class="pagewrap">
                <span class="layui-breadcrumb">
                  <a href="">实时日志分析</a>
                  <a><cite>日志类型</cite></a>
                </span>
        </div>
    </div>
    <div class="layui-tab" lay-filter="test">
        <div class="layui-card-body">
            <form class="layui-form">
                <div class="layui-form layui-form-item">
                    <div >
                        <div class="layui-form-mid" style="font-size: 24px">模块:</div>
                        <input class="layui-btn layui-btn-sm" onclick = "getAll()" id="allModule" value="所有"></input>
                        <span id="class">

                        </span>
                    </div>
                    <br/>
                    <div class="layui-inline">
                        <div class="layui-form-mid">开始时间:</div>
                        <div class="layui-input-inline" style="width: 100px;">
                            <input type="text" autocomplete="off" id="sTime" class="layui-input" name="sTime">
                        </div>
                        <div class="layui-form-mid">结束时间:</div>
                        <div class="layui-input-inline" style="width: 100px;">
                            <input type="text" autocomplete="off" id="eTime" class="layui-input" name="eTime">
                        </div>
                        <button class="layui-btn layui-btn-blue" lay-filter="searchSubmit" id="submitData" data-type="reload" onclick="return false;" lay-submit="">查询</button>
                        <button class="layui-btn layui-btn-primary" type="reset">重置</button>
                    </div>
                </div>
            </form>
        </div>
        <ul class="layui-tab-title">
            <li class="layui-this" lay-id="1">日志查询</li>
            <li lay-id="2">日志统计<span id="aa"></span></li>
            <li lay-id="3">日志告警<span id="bb"></span></li>
        </ul>
        <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">

                <table id="demo" lay-filter="demo" class="layui-hide"></table>
                <script type="text/html" id="barDemo">
                    <a class="layui-btn layui-btn-xs" lay-event="export">下载</a>
                </script>
                <script type="text/html" id="toolbarDemo">
                    <div class="layui-btn-container">
                        <button class="layui-btn layui-btn-sm" lay-event="">同步更新</button>
                        <button class="layui-btn layui-btn-sm" lay-event="getCheckData">全量下载</button>
                        <button class="layui-btn layui-btn-sm" lay-event="isALL" id="allLog">全部日志</button>
                        <button class="layui-btn layui-btn-sm" lay-event="errLogs" id="errLog">错误日志<span id="errCount" style="color: red;font-size: 20px"></span></button>
                        <button class="layui-btn layui-btn-sm" lay-event="warnLogs" id="warnLog">警告日志<span id="warnCount" style="color: greenyellow;font-size: 20px"></span></button>
                    </div>
                </script>
            </div>
            <div class="layui-tab-item">内容2</div>

            <div class="layui-tab-item" lay-filter="content3">
                <div class="layui-tab-item layui-show">
                    <table id="demo3" lay-filter="demo3" class="layui-hide"></table>
                    <script type="text/html" id="barDemo3">
                        <a class="layui-btn layui-btn-xs" lay-event="chuli">处理</a>
                        <a class="layui-btn layui-btn-xs" lay-event="chakan">查看</a>
                    </script>
                    <script type="text/html" id="toolbarDemo3">
                        <div class="layui-btn-container">
                            <button class="layui-btn layui-btn-sm" lay-event="handleCheckData">批量处理</button>
                            <button class="layui-btn layui-btn-sm" lay-event="isAllWarn" id="isAllWarn">全部</button>
                            <button class="layui-btn layui-btn-sm" lay-event="wWarnLogs" id="wWarnLogs">未处理<span id="wCount" style="color: red;font-size: 20px"></span></button>
                            <button class="layui-btn layui-btn-sm" lay-event="yWarnLogs" id="yWarnLogs">已处理<span id="yCount" style="color: greenyellow;font-size: 20px"></span></button>
                        </div>
                    </script>
                </div>

            </div>
        </div>
    </div>
 <%--   <div class="layui-row">
        <div class="layui-card">

        </div>
    </div>--%>
</div>
<%--<script src="<%=basePath%>assets/layui.all.js"></script>--%>
<script src="/layui/layui.all.js"></script>
<script src="/jquery.js"></script>

<script>

    //var id_str = "";
    //var warn_id_str = "";
    var logpage = "";
    var logModule1 = "";
    var logId1 = "";
    var htmlPage = "1";


    <!-- 跳转页面参数 -->
    var tWarnId = "";
    var tWarnModule = "";
    var tSTime = "";
    var tETime = "";
    <!--是否弹出告警-->
    var isTan = true;
    <!-- 处理或未处理 -->
    var chuliStatus = "";

    <!-- error or warn -->
    var isAll = "";

    layui.use(['table','element','util'], function () {  // 引入 table模块
        var element = layui.element;
        var table = layui.table
            ,form = layui.form,
            util = layui.util;
        element.on('tab(test)', function(data){
            if(data.index==0){//日志查询
                htmlPage = "1";
                var change = {
                    sTime: $("#sTime").val(),
                    eTime: $("#eTime").val(),
                    logModule:logModule1,
                    logLevel:""
                };
                load1(change);

            }else if(data.index==1){//日志统计
                htmlPage = "2";
                layer.msg("1")
            }else if(data.index==2){//日志告警
                htmlPage = "3";
                var change = {
                    //sTime: tSTime === ""?$("#sTime").val():new Date(tSTime).Format("yyyy-MM-dd HH:mm:ss"),
                    sTime: tSTime === ""?$("#sTime").val():util.toDateString(tSTime , 'yyyy-MM-dd HH:mm:ss'),
                    //eTime: tETime === ""?$("#eTime").val():new Date(tETime).Format("yyyy-MM-dd HH:mm:ss"),
                    eTime: tETime === ""?$("#eTime").val():util.toDateString(tETime , 'yyyy-MM-dd HH:mm:ss'),
                    warnModule: tWarnModule === ""?logModule1:tWarnModule,
                    warnId: tWarnId === ""?logId1:tWarnId
                };
                load3(change);
            }
        });




        <!-- 日志查询 -->
        tableIns = table.render({
           /* id:"dataTable",//*/
            elem: '#demo',//指定表格元素
            url: '/log4j/getAllLogs',  //请求路径
            contentType : "application/json",
            toolbar: '#toolbarDemo' ,//开启头部工具栏，并为其绑定左侧模板
            cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            ,skin: 'line ' //表格风格 line （行边框风格）row （列边框风格）nob （无边框风格）
            ,even: true    //隔行换色
            ,page: true  //开启分页
            ,where:{
                sTime: $("#sTime").val(),
                eTime: $("#eTime").val(),
                logModule:logModule1,
                logLevel:""
            }
            ,limits: [20,30,50,100]  //每页条数的选择项，默认：[10,20,30,40,50,60,70,80,90]。
            ,limit: 20 //每页默认显示的数量
            ,method:'post'  //提交方式
            ,cols: [[
                {type:'checkbox', width: 60, fixed: "left"}
                ,{ field: 'logId',  title: 'ID',width: 40 ,hide:true }
                ,{ field: 'createTime',  title: '时间', width: 160,templet: '<div>{{ layui.util.toDateString(d.createTime, "yyyy-MM-dd HH:mm:ss") }}</div>', align: "left" }
                ,{ field: 'logModule',  title: '模块', width: 350,align: "left"  }
                ,{ field: 'logLevel',  title: '日志级别',minWidth: 50,align: "left" }
                ,{ field: 'logMsg',  title: '日志内容',minWidth: 200,align: "left" }
                ,{ fixed: 'right', title: '操作', width: 80, align:'center', toolbar: '#barDemo'  }
            ]]
            ,height: 600
            ,done: function(res, curr, count){
                $("[data-field='logLevel']").children().each(function(){
                    if($(this).text()=='ERROR'){
                        $(this).text("错误")
                    }else if($(this).text()=='WARN'){
                        $(this).text("警告")
                    }
                });
                if (isAll === ""){//全部日志
                    $("#allLog").attr("class","layui-btn layui-btn-sm layui-btn-blue");
                    $("#errLog").attr("class","layui-btn layui-btn-sm");
                    $("#warnLog").attr("class","layui-btn layui-btn-sm");
                }else if (isAll === "0"){//错误日志
                    $("#allLog").attr("class","layui-btn layui-btn-sm");
                    $("#errLog").attr("class","layui-btn layui-btn-sm layui-btn-blue");
                    $("#warnLog").attr("class","layui-btn layui-btn-sm");
                } else if (isAll === "1") {//警告日志
                    $("#allLog").attr("class","layui-btn layui-btn-sm");
                    $("#errLog").attr("class","layui-btn layui-btn-sm");
                    $("#warnLog").attr("class","layui-btn layui-btn-sm layui-btn-blue");
                }
                var c = {logModule:logModule1,sTime:$("#sTime").val(),eTime:$("#eTime").val(),logLevel:""};
                $.ajax({
                    type:"post",
                    url:"<%=basePath%>log4j/selectModule",
                    async:false,
                    contentType : 'application/json',//添加这句话
                    data:JSON.stringify(c),
                    dataType:"json",
                    success:function(data){
                        if(data.code=="0"){
                            $("#errCount").html(data.errCount);
                            $("#warnCount").html(data.warnCount);
                            var logModules = data.logModules;
                            var str = "";
                            debugger
                            logModules.forEach(function(value, key) {
                                str += "<input type='text' class=\"layui-btn layui-btn-sm\" id=\'"+value.name+"\' onclick = 'getModule(\""+value.name+"\",\""+value.desc+"\")' value ="+value.desc+"></input>";
                            });

                            $("#class").html(str);
                            $("#class input[type='text']").each(function(){
                                if (logModule1 != "" && $(this).attr("id")===logModule1){
                                    $("#allModule").attr("class","layui-btn layui-btn-sm");
                                    $(this).attr("class","layui-btn layui-btn-sm layui-btn-blue");
                                }
                            });
                            if (isTan){
                                isTan = false;
                                var warnList = data.warnList;
                                for (var i =0 ;i<2;i++){
                                    var warnModule = warnList[i].warnModule;
                                    var warnId = warnList[i].warnId;
                                    var content = warnList[i].warnMsg;
                                    var warnTime = warnList[i].warnTime;
                                    var startTime = warnList[i].startTime;
                                    var htmlStr = '<div id="htmlTanStr"><input hidden name=\"warnModule2\" id=\"logModule2\" value=\"'+warnModule+'\">\n' +
                                        '                <input hidden name=\"sTime2\" id=\"sTime2\" value=\"'+startTime+'\">\n' +
                                        '                <input hidden name=\"eTime2\" id=\"eTime2\" value=\"'+warnTime+'\">\n' +
                                        '                <input hidden name=\"warnId2\" id=\"warnId2\" value=\"'+warnId+'\">\n' +
                                        '                <span>'+content+'</span></div>';
                                    parent.layer.open({
                                        id:warnId,
                                        type: 1
                                        ,title: '警告'
                                        ,area: ['400px', '200px']
                                        ,shade: 0
                                        ,maxmin: true
                                        ,offset: [ //为了演示，随机坐标
                                            Math.random()*($(window).height()-300)
                                            ,Math.random()*($(window).width()-390)
                                        ]
                                        ,content:htmlStr
                                        ,btn: ['查看'] //只是为了演示
                                        ,yes: function(){
                                            debugger
                                            chuliStatus = "0";
                                            tWarnId = warnId;
                                            tWarnModule = warnModule;
                                            tSTime = startTime;
                                            tETime = startTime+24*60*60*1000;
                                            parent.layer.closeAll();
                                            element.tabChange('test', "3");

                                        }
                                    });
                                }
                            }


                        }else{
                            layer.msg(result.msg,{icon: 5,time:2000});

                        }
                    }
                });
            }
        });
        //头工具栏事件
        table.on('toolbar(demo)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            if (obj.event === 'getCheckData'){
                debugger
                var id_str = "";
                var data = checkStatus.data;
                if (data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        id_str += data[i].logId + ',';
                    }
                }
                id_str = id_str.substr(0, id_str.length-1);
                window.open("<%=basePath%>/log4j/batchDownLoad?ids="+id_str);
            }
            if (obj.event === 'isALL'){
                isAll = "";
                var change = {logModule:logModule1,sTime:$("#sTime").val(),eTime:$("#eTime").val(),logLevel:""};
                logpage = "";
                load1(change);
            }
            if (obj.event === 'errLogs'){
                isAll = "0";
                var change = {logModule:logModule1,sTime:$("#sTime").val(),eTime:$("#eTime").val(),logLevel:"ERROR"};
                logpage = "ERROR";
                load1(change);
            }
            if (obj.event === 'warnLogs'){
                isAll = "1";
                var change = {logModule:logModule1,sTime:$("#sTime").val(),eTime:$("#eTime").val(),logLevel:"WARN"};
                logpage = "WARN";
                load1(change);
            }
        });
        //监听工具条
        table.on('tool(demo)', function(obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data //获得当前行数据
            layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === 'export') {
                window.open("<%=basePath%>/log4j/downLoad?id="+data.logId);
            }

        });
        <!-- 日志查询 -->


        <!-- 日志统计-->
        <!-- 日志统计-->
        <!-- 日志告警-->
        tableIns3 = table.render({
            elem: '#demo3',//指定表格元素
            url: '/warn/getAllWarn',  //请求路径
            contentType : "application/json",
            toolbar: '#toolbarDemo3' ,//开启头部工具栏，并为其绑定左侧模板
            cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            ,skin: 'line ' //表格风格 line （行边框风格）row （列边框风格）nob （无边框风格）
            ,even: true    //隔行换色
            ,page: true  //开启分页
            ,where:{
                sTime: $("#sTime").val(),
                eTime: $("#eTime").val(),
                warnModule:logModule1,
                warnId:logId1
            }
            ,limits: [20,30,50,100]  //每页条数的选择项，默认：[10,20,30,40,50,60,70,80,90]。
            ,limit: 20 //每页默认显示的数量
            ,method:'post'  //提交方式
            ,cols: [[
                {type:'checkbox', width: 60, fixed: "left"}
                ,{ field: 'warnId',  title: 'ID',  sort: true,width: 80 ,hide:true }
                ,{ field: 'warnTime',  title: '时间', width: 160,templet: '<div>{{ layui.util.toDateString(d.warnTime, "yyyy-MM-dd HH:mm:ss") }}</div>', align: "left" }
                ,{ field: 'warnModule',  title: '模块', width: 250,align: "left"  }
                ,{ field: 'warnMsg',  title: '告警内容',minWidth: 300,align: "left" }
                ,{ field: 'warnStatus',  title: '处理状态',minWidth: 50,align: "left" }
                ,{ fixed: 'right', title: '操作', minWidth: 40, align:'center', toolbar: '#barDemo3'  }
            ]]
            ,height: 600
            ,done: function(res, curr, count){
                debugger
                $("#class input[type='text']").each(function(){
                    if (tWarnModule != "" && $(this).attr('id')=== tWarnModule){
                        $(this).attr("class","layui-btn layui-btn-sm layui-btn-blue");
                    }else{
                        $(this).attr("class","layui-btn layui-btn-sm");
                    }
                });
                tWarnModule===""?"":$("#allModule").attr("class","layui-btn layui-btn-sm");
                tSTime ===""?"":$("#sTime").val(util.toDateString(tSTime , 'yyyy-MM-dd HH:mm:ss'));
                tETime ===""?"":$("#eTime").val(util.toDateString(tETime , 'yyyy-MM-dd HH:mm:ss'));
                if (chuliStatus === ""){//全部
                    //改变颜色
                    $("#isAllWarn").attr("class","layui-btn layui-btn-sm layui-btn-blue");
                    $("#wWarnLogs").attr("class","layui-btn layui-btn-sm");
                    $("#yWarnLogs").attr("class","layui-btn layui-btn-sm");
                }else if (chuliStatus === "0"){//未处理
                    //改变颜色
                    $("#isAllWarn").attr("class","layui-btn layui-btn-sm");
                    $("#wWarnLogs").attr("class","layui-btn layui-btn-sm layui-btn-blue");
                    $("#yWarnLogs").attr("class","layui-btn layui-btn-sm");
                } else if (chuliStatus === "1"){
                    //改变颜色
                    $("#isAllWarn").attr("class","layui-btn layui-btn-sm");
                    $("#wWarnLogs").attr("class","layui-btn layui-btn-sm");
                    $("#yWarnLogs").attr("class","layui-btn layui-btn-sm layui-btn-blue");
                }
                var c = {
                    warnId:tWarnId === ''?logId1:tWarnId,
                    warnModule:tWarnModule===''?logModule1:tWarnModule,
                    sTime:$("#sTime").val(),
                    eTime:$("#eTime").val()
                };
                $.ajax({
                    type:"post",
                    url:"<%=basePath%>warn/getModule",
                    async:false,
                    contentType : 'application/json',//添加这句话
                    data:JSON.stringify(c),
                    dataType:"json",
                    success:function(data){
                        if(data.code=="0"){
                            $("#wCount").html(data.untreatedCount);
                            $("#yCount").html(data.processedCount);
                        }
                    }
                });
                $("[data-field='warnStatus']").children().each(function(){
                    if($(this).text()=='0'){

                        $(this).html("<span style=\"color: red\">未处理</span>")
                    }else if($(this).text()=='1'){
                        $(this).html("<span style=\"color: green\">已处理</span>")
                    }
                });
            }
        })
        //监听头工具栏
        table.on('toolbar(demo3)', function(obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            var change;
            if (obj.event === 'handleCheckData') {
                //批量处理
                debugger
                var warn_id_str = "";
                var data = checkStatus.data;
                var isBreak = false;
                if (data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].warnStatus == 1){
                            isBreak = true;
                        }
                        warn_id_str += data[i].warnId + ',';
                    }
                }else{
                    layer.msg("请勾选后操作");
                    return false;
                }
                if (isBreak){
                    layer.msg("包含已处理数据，请重新选择")
                    return false;
                }
                warn_id_str = warn_id_str.substr(0, warn_id_str.length-1);
                layer.confirm('确定批量处理吗？', {
                    btn: ['确定'] //按钮
                }, function(){
                    debugger
                    var condition = {warnIds:warn_id_str};
                    $.ajax({
                        type:"post",
                        url:"<%=basePath%>warn/handleWarn",
                        contentType : 'application/json',
                        async:true,
                        data:JSON.stringify(condition),
                        dataType:"json",
                        success:function(data){
                            var parseJSON = $.parseJSON(data);
                            if(data.code=="0"){
                                layer.close(layer.index);
                                layer.msg(data.msg);
                                var chang = {
                                    sTime: $("#sTime").val(),
                                    eTime: $("#eTime").val(),
                                    warnModule:tWarnModule,
                                    status: chuliStatus,
                                    warnId:tWarnId
                                };
                                load3(chang)
                            }
                        }
                    });

                });

            }if (obj.event === 'isAllWarn') {
                chuliStatus = "";
                tWarnId = "";
                 change = {
                    sTime: $("#sTime").val(),
                    eTime: $("#eTime").val(),
                     warnModule:tWarnModule,
                    warnId:""
                };

            }else if (obj.event === 'wWarnLogs'){
                chuliStatus = "0";
                change = {
                    sTime: $("#sTime").val(),
                    eTime: $("#eTime").val(),
                    warnModule:tWarnModule,
                    warnId:tWarnId,
                    status:0
                };
            }else if (obj.event === 'yWarnLogs'){
                chuliStatus = "1";
                 change = {
                    sTime: $("#sTime").val(),
                    eTime: $("#eTime").val(),
                     warnModule:tWarnModule,
                    warnId:tWarnId,
                    status:1
                };

            }
            load3(change);
        });
        //监听工具条
        table.on('tool(demo3)', function(obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data //获得当前行数据
            layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === 'chuli') {
                if (data.warnStatus == 1){
                    layer.msg("已处理，请重新选择")
                    return false;
                }
                layer.confirm('<textarea id="textDemo" rows="10" cols="20"></textarea>', {
                    btn: ['提交'] //按钮
                }, function(){
                    var condition = {warnId:data.warnId,content:$("#textDemo").val()};
                    $.ajax({
                        type:"post",
                        url:"<%=basePath%>warn/handleWarn",
                        contentType : 'application/json',//添加这句话
                        data:JSON.stringify(condition),
                        async:true,
                        dataType:"json",
                        success:function(data){
                            //var parseJSON = $.parseJSON(data);
                            if(data.code=="0"){
                                layer.close(layer.index);
                                debugger
                                var chang = {
                                    sTime: $("#sTime").val(),
                                    eTime: $("#eTime").val(),
                                    warnModule:tWarnModule,
                                    status: chuliStatus,
                                    warnId:tWarnId
                                };
                                load3(chang)
                            }
                        }
                    });

                });
               /* window.open("<%=basePath%>/log4j/downLoad?id="+data.logId);*/
            }
            if (layEvent === 'chakan') {//查看
                $.ajax({
                    type:"post",
                    url:"<%=basePath%>warn/seeWarn",
                    async:true,
                    data:{warnId:data.warnId},
                    dataType:"text",
                    success:function(data){
                        var parseJSON = $.parseJSON(data);
                        if(parseJSON.code=="0"){
                            layer.alert(parseJSON.singleData.handleMsg);
                        }
                    }
                });
            }

        });
        <!-- 日志告警-->




    });


    //所有
    function getAll(){
        debugger
        $("#class input[type='text']").each(function(){
                $(this).attr("class","layui-btn layui-btn-sm");
        });
        logModule1 = "";
        tWarnModule = "";
        tWarnId = "";
        chuliStatus = "";
        $("#allModule").attr("class","layui-btn layui-btn-sm layui-btn-blue");
        if (htmlPage === '1'){
            var change = {logModule:"",sTime:$("#sTime").val(),eTime:$("#eTime").val(),logLevel:logpage};
            load1(change);
            return false;
        }else if(htmlPage === '2'){

        }else if (htmlPage === '3') {
            var change = {
                sTime: $("#sTime").val(),
                eTime: $("#eTime").val(),
                warnModule:"",
                warnId:""
            };
            //重新加载table
            load3(change);
            return false;
        }
    }


    function getModule(key,value){
        debugger
        $("#class input[type='text']").each(function(){
            if ($(this).val()===value){
                $(this).attr("class","layui-btn layui-btn-sm layui-btn-blue");
            }else{
                $(this).attr("class","layui-btn layui-btn-sm");
            }
        });
        $("#allModule").attr("class","layui-btn layui-btn-sm");
        chuliStatus = "";
        /*$("#"+key).attr("class","layui-btn layui-btn-sm layui-btn-blue");*/
        if (htmlPage === '1'){
            var change = {logModule:key,sTime:$("#sTime").val(),eTime:$("#eTime").val(),logLevel:logpage};
            logModule1 = key;
            load1(change);
            return false;
        }else if(htmlPage === '2'){

        }else if (htmlPage === '3') {
            tWarnModule = key;
            var change = {
                sTime: $("#sTime").val(),
                eTime: $("#eTime").val(),
                warnModule:tWarnModule,
                warnId:""
            };
            //重新加载table
            load3(change);
            return false;
        }

    }

    //搜索框
    layui.use(['form','laydate'], function(){
        var form = layui.form ,layer = layui.layer;
        //监听搜索框
        form.on('submit(searchSubmit)', function(data){
            if (htmlPage === '1'){
                var change = {logModule:logModule1,sTime:$("#sTime").val(),eTime:$("#eTime").val(),logLevel:logpage};
                //重新加载table
                load1(change);
                return false;
            }else if(htmlPage === '2'){

            }else{
                tSTime = $("#sTime").val();
                tETime = $("#eTime").val();
                var change = {
                    sTime: $("#sTime").val(),
                    eTime: $("#eTime").val(),
                    warnModule:logModule1,
                    warnId:""
                };
                //重新加载table
                load3(change);
                return false;
            }

        });
    });
    //重新加载table1
    function load1(obj){
        tableIns.reload({
            where: obj
            , page: {
                curr: 1 //从当前页码开始
            }
        });
    }
    //重新加载table
    function load2(obj){
        tableIns.reload({
            where: obj.field
            , page: {
                curr: 1 //从当前页码开始
            }
        });
    }
    //重新加载table3
    function load3(obj){
        tableIns3.reload({
            where: obj
            , page: {
                curr: 1 //从当前页码开始
            }
        });
    }
    Date.prototype.Format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, // 月份
            "d+": this.getDate(), // 日
            "h+": this.getHours(), // 小时
            "m+": this.getMinutes(), // 分
            "s+": this.getSeconds(), // 秒
            "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
            "S": this.getMilliseconds() // 毫秒
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + ""));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
    //时间
    layui.use('laydate', function(){
        var laydate = layui.laydate;
        //执行一个laydate实例
        laydate.render({
            elem: '#sTime', //指定元素
            type:'datetime', // 可选择：年、月、日、时、分、秒
            format: 'yyyy-MM-dd HH:mm:ss' //指定时间格式
        });
        laydate.render({
            elem: '#eTime', //指定元素
            type:'datetime', // 可选择：年、月、日、时、分、秒
            format: 'yyyy-MM-dd HH:mm:ss'
        });
    });
</script>


</body>
</html>
