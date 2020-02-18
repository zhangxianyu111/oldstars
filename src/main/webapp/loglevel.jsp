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
                  <a href="">定时任务管理</a>
                  <a><cite>定时任务</cite></a>
                </span>
        </div>
    </div>
    <div class="layui-row">
        <div class="layui-card">
            <div class="demoTable" style="margin-left: 1%;">
                任务名:
                <div class="layui-inline">
                    <input name="jobName" class="layui-input" id="demoReload" autocomplete="off" width="600px">
                </div>
                <%--<div class="layui-input-block">
                    <input type="text" name="jobName" lay-verify="jobName" autocomplete="off" placeholder="任务名" class="layui-input" id="demoReload">
                </div>--%>
                <button class="layui-btn" data-type="reload">搜索</button>
                <span class="site-demo-button" id="layerDemo">
                <button class="layui-btn" data-method="notice">添加</button>
                </span>
             </div>
             <table class="layui-hide" id="test"  lay-filter="demo"></table>
        </div>
    </div>
</div>
<script src="<%=basePath%>assets/layui.all.js"></script>
<script src="/jquery.js"></script>
<script>
    layui.use('table', function(){
        var table = layui.table;
        var layer = layui.layer;  //layer初始化
        var index = layer.load(1);
        table.render({
            elem: '#test'
            ,url:'<%=basePath%>qrtzJobDetails/listByPage'
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            ,limit:20
            ,limits:[20,30,50]
            ,method:'post'
            ,where:{
                type:'load'
            }
            ,cols: [[
                //{type:'checkbox'}
                {field:'schedName', width:"14.2%", title: '调度器', sort: true}
                ,{field:'jobName', width:"14.2%", title: '任务名称'}
                ,{field:'description', width:"14.2%", title: '任务描述', sort: true}
                ,{field:'jobGroup', width:"14.2%", title: '任务分组'}
                ,{field:'cronExpression', title: '表达式', width:"14.2%", minWidth: 100, edit:"text"}
                ,{field:'triggerState', title: '状态', width:"14.2%", minWidth: 100}
                ,{fixed: 'right',width:"14.2%", align:'center', toolbar: '#barDemo', edit:"text"} //minWidth：局部定义当前单元格的最小宽度，layui 2.2.1 新增
            ]],
            done:function (res) {   //返回数据执行回调函数
                layer.close(index);    //返回数据关闭loading
            }

            ,id: 'testReload'
            ,page:true
        });
        //监听表格复选框选择
        table.on('checkbox(demo)', function(obj){
            console.log(obj)
        });
        //监听工具条
        table.on('tool(demo)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    shanchu(data.jobName,data.jobGroup,index);//alert(data.id);
                    //obj.del();
                    //layer.close(index);
                });
            }else if(obj.event === 'pause'){
                //停止
                tingzhi(data.jobName,data.jobGroup);
            }else if(obj.event === 'resume'){
                //恢复
                huifu(data.jobName,data.jobGroup);
            }
        });
        //监听单元格实时编辑
        table.on('edit(demo)',function(obj){
            //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data;
            /*var field = obj.field;//字段名
            var value = obj.value;//修改后的值
            var id = obj.id;*/
            //加载中样式...
            var loading = layer.msg('正在更新', {icon: 16, shade: 0.3, time:0});
            $.ajax({
                type:"post",
                url:"<%=basePath%>qrtzJobDetails/edit",
                async:true,
                data:{
                    jobName:data.jobName,
                    jobGroup:data.jobGroup,
                    cronExpression:data.cronExpression
                },
                dataType:"text",
                success:function(data){
                    debugger
                    var result = $.parseJSON( data );
                    layer.close(loading);
                    if (result.success === true) {
                        layer.msg(result.msg,{icon: 6,time:1000});
                    }else{
                        layer.msg(result.msg,{icon: 5,time:1000,end:function () {
                                location.reload();
                            }});
                    }

                }
            });
        });
        var $ = layui.$, active = {
            reload: function(){
                debugger
                var jobName = $('#demoReload').val();
                //执行重载
                table.reload('testReload', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    ,where: {
                        jobName:jobName
                    }
                });
            }
        };
        //console.log( $('.demoTable .layui-btn'));
        $('.demoTable .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
    //删除
    function shanchu(t,b,index){
        //加载中样式...
        var loading = layer.msg('正在删除', {icon: 16, shade: 0.3, time:0});
        $.ajax({
            type:"post",
            url:"<%=basePath%>qrtzJobDetails/delete",
            async:true,
            data:{
                jobName:t,
                jobGroup:b
            },
            dataType:"text",
            success:function(data){
                var result = $.parseJSON( data );
                layer.close(loading);
                if (result.code === '0') {
                    layer.msg(result.msg,{icon: 6,time:1000,end:function () {
                            location.reload();
                        }});

                }else{
                    layer.msg(result.msg,{icon: 5,time:1000});
                }
            }
        });
    }
    //停止
    function tingzhi(t,b) {
        //加载中样式...
        var loading = layer.msg('正在停止', {icon: 16, shade: 0.3, time: 0});
        $.ajax({
            type: "post",
            url: "<%=basePath%>qrtzJobDetails/pause",
            async: true,
            data: {
                jobName: t,
                jobGroup: b
            },
            dataType: "text",
            success: function (data) {
                var result = $.parseJSON(data);
                layer.close(loading);
                if (result.success === true) {
                    layer.msg(result.msg, {
                        icon: 6, time: 1000, end: function () {
                            location.reload();
                        }
                    });

                } else {
                    layer.msg(result.msg, {icon: 5, time: 1000});
                }
            }
        });
    }
    //恢复
    function huifu(t,b) {
        //加载中样式...
        var loading = layer.msg('正在恢复', {icon: 16, shade: 0.3, time: 0});
        $.ajax({
            type: "post",
            url: "<%=basePath%>qrtzJobDetails/resume",
            async: true,
            data: {
                jobName: t,
                jobGroup: b
            },
            dataType: "text",
            success: function (data) {
                var result = $.parseJSON(data);
                layer.close(loading);
                if (result.success === true) {
                    layer.msg(result.msg, {
                        icon: 6, time: 1000, end: function () {
                            location.reload();
                        }
                    });

                } else {
                    layer.msg(result.msg, {icon: 5, time: 1000});
                }
            }
        });
    }

    //添加人员弹出框
    layui.use('layer', function(){ //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
    });
    var active = {
        notice: function(){
            //示范一个公告层
            layer.open({
                type: 2
                ,title: false //不显示标题栏
                ,closeBtn: false
                ,area: ['40%', '100%']
                ,shade: 0.8
                ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
                ,btn: ['提交', '返回']
                ,btnAlign: 'c'
                ,moveType: 1 //拖拽模式，0或者1
                ,content: '<%=basePath%>qrtzJobDetails/addQrtz'
                ,success: function(layero,index){
                    var btn = layero.find('.layui-layer-btn');
                    btn.find('.layui-layer-btn0').click(function(){
                        var body = layer.getChildFrame('body', index);
                        var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                        var jobName = body.find('#jobName').val();
                        var description = body.find('#description').val();
                        var jobGroup = body.find('#jobGroup').val();
                        var cronExpression = body.find('#cronExpression').val();
                        //加载中样式...
                        var loading = layer.msg('正在新增', {icon: 16, shade: 0.3, time:0});
                        $.ajax({
                            type:"post",
                            url:"<%=basePath%>qrtzJobDetails/add",
                            async:true,
                            data:{
                                jobName:jobName,
                                description:description,
                                jobGroup:jobGroup,
                                cronExpression:cronExpression
                            },
                            dataType:"text",
                            success:function(data){
                                var result = $.parseJSON( data );
                                if(result.code=="0"){
                                    layer.close(loading);
                                    layer.msg(result.msg,{icon: 6,time:1000,end:function () {
                                            location.reload();
                                        }});
                                }else{
                                    layer.msg(result.msg,{icon: 5,time:1000});

                                }
                            }
                        });

                    });
                }
            });
        }
    }
    $('#layerDemo .layui-btn').on('click', function(){
        var othis = $(this), method = othis.data('method');
        active[method] ? active[method].call(this, othis) : '';
    });


</script>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    <a class="layui-btn layui-btn-xs" lay-event="pause">暂停</a>
    <a class="layui-btn layui-btn-xs" lay-event="resume">恢复</a>
</script>



</body>
</html>
