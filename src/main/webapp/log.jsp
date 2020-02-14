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
    <div class="layui-row">
        <div class="layui-card">
            <div class="layui-card-body">
                <form class="layui-form">
                    <div class="layui-form layui-form-item">
                        <div class="layui-inline">
                            <div class="layui-form-mid">模块:</div>
                            <div class="layui-input-inline" style="width: 100px;">
                                <input type="text" autocomplete="off" class="layui-input" name="module">
                            </div>
                            <div class="layui-form-mid">时间:</div>
                            <div class="layui-input-inline" style="width: 100px;">
                                <input type="text" autocomplete="off" id="logDate" class="layui-input" name="logDate">
                            </div>
                            <button class="layui-btn layui-btn-blue" lay-filter="searchSubmit" id="submitData" data-type="reload" onclick="return false;" lay-submit="">查询</button>
                            <button class="layui-btn layui-btn-primary" type="reset">重置</button>
                        </div>
                    </div>
                </form>
             </div>

            <table id="demo" lay-filter="logTable"></table>
        </div>
    </div>
</div>
<script src="<%=basePath%>assets/layui.all.js"></script>
<script src="/jquery.js"></script>
<script>
    var table = layui.table
        ,form = layui.form;
    layui.use('table', function () {  // 引入 table模块
        tableIns = table.render({
            id:"dataTable",//
            elem: '#demo',//指定表格元素
            url: '/log/allLogs',  //请求路径
            cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            ,skin: 'line ' //表格风格 line （行边框风格）row （列边框风格）nob （无边框风格）
            //,even: true    //隔行换色
            ,page: true  //开启分页
            /*,page: { //分页设定
                layout: ['count', 'prev', 'page', 'next'] //自定义分页布局
                , page: 1 //设定初始在第 1 页
                , limit: 20//每页多少数据
                , groups: 5 //只显示 5 个连续页码
            }*/
            ,limits: [20,30,50,100]  //每页条数的选择项，默认：[10,20,30,40,50,60,70,80,90]。
            ,limit: 20 //每页默认显示的数量
            ,method:'post'  //提交方式
            ,cols: [[
                /*{type:'checkbox', width: 60, fixed: true}
                ,*/{ field: 'id',  title: 'ID',  sort: true,width: 40  }
                ,{ field: 'logDate',  title: '时间', width: 160,templet: '<div>{{ layui.util.toDateString(d.logDate, "yyyy-MM-dd HH:mm:ss") }}</div>', align: "left" }
                ,{ field: 'logTitle',  title: '日志标题', width: 400,align: "left"  }
                ,{ field: 'logDesc',  title: '日志详情',minWidth: 300,align: "left" }
                ,{ fixed: 'right', title: '操作', width: 80, align:'center', toolbar: '#barDemo'  }
            ]]
            ,height: 600
            ,done: function(res, curr, count){
                //隐藏ID列
                $("[data-field='id']").css('display','none');
                //得到当前页码
                //console.log(curr); 可以设置变量接收 替换掉最底下load 重新加载 的1  可进行当前页刷新
            }
        });
    });
    //监听工具条
    table.on('tool(logTable)', function(obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        debugger
        var data = obj.data //获得当前行数据
            , layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'export') {
            window.open("<%=basePath%>/log/downloadLog?id="+data.id);
        }

    });

    //搜索框
    layui.use(['form','laydate'], function(){
        var form = layui.form ,layer = layui.layer;
        //监听搜索框
        form.on('submit(searchSubmit)', function(data){
            //重新加载table
            load(data);
            return false;
        });
    });

    //重新加载table
    function load(obj){
        tableIns.reload({
            where: obj.field
            , page: {
                curr: 1 //从当前页码开始
            }
        });
    }

    layui.use('laydate', function(){
        var laydate = layui.laydate;
        //执行一个laydate实例
        laydate.render({
            elem: '#logDate' //指定元素
        });
    });

</script>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="export">下载</a>
</script>
</body>
</html>
