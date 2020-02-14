<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>assets/css/layui.css"/>
    <script src="/jquery.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>assets/layui.js" type="text/javascript" charset="utf-8"></script>
    <title> </title>
    <style type="text/css">
        *{
            margin: 0;
            padding: 0;
            font-family: "微软雅黑";
        }
        body{
            background-color: rgba(0,0,0,0.5);
        }
        #wai{
            width:100%;
            line-height: 70px;
        }

        .spp{
            margin-left: 39px;
        }
        h1{
            text-align: center;
        }
        p{
            margin-left: -13%;
            font-size: 18px;
        }
        input{
            width: 200px;
            height:30px;
            border-radius: 5px;
            border-color: lawngreen;
        }
        .p1{
            margin-left: -7%;
            margin-left: 13px;
        }

        #sel{
            width: 200px;
            border-radius: 5px;
            border-color: lawngreen;
            margin-left: 5px;
        }
        #spp1{
            display: none;
            color: red;

        }
        #spp2{
            display: none;
            color: red;

        }
        #spp3{
            display: none;
            color: red;

        }
        #spp4{
            display: none;
            color: red;

        }

    </style>
</head>
<body>
<div id="zong">
    <h1>添加任务</h1>
    <div id="wai">
        <span class="spp">任务名称：</span>
        <input type="text"  id="jobName" value="" placeholder=""/><span id="spp1"></span><br />
        <span class="spp">任务描述：</span>
        <input type="text"  id="description" value="" placeholder="请输入中文字符"/><span id="spp2"></span><br />
        <span class="spp">任务分组：</span>
        <input type="text"  id="jobGroup" value=""  placeholder=""/><span id="spp3"></span><br />
        <span class="spp">表达式&nbsp;&nbsp;&nbsp;：</span>
        <input class= "text" type="text" id="cronExpression" placeholder="30 * * * * ? 每半分钟触发任务 "><span id="spp4"></span><br /><br />
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    //姓名的input失去焦点事件
    $("#jobName").blur(function(){
        debugger
        var jobName = $("#jobName").val();
        if(jobName === ''){
            $("#spp1").html("不能为空");
            $("#spp1").css("display","inline-block");
        }else{
            $("#spp1").css("display","none");
        }
    })
    //姓名的input失去焦点事件
    $("#description").blur(function(){
        debugger
        var xm = $("#description").val();
        if (xm === ''){
            $("#spp2").html("请重新输入");
            $("#spp2").css("display","inline-block");
        } else{
            var ret = /^[\u4e00-\u9fa5]+$/;//只能输入中文
            if(!ret.test(xm)){
                $("#spp2").html("请重新输入");
                $("#spp2").css("display","inline-block");
            }else{
                $("#spp2").css("display","none");
            }
        }

    })
    //姓名的input失去焦点事件
    $("#jobGroup").blur(function(){
        debugger
        var jobGroup = $("#jobGroup").val();
        if(jobGroup === ''){
            $("#spp3").html("不能为空");
            $("#spp3").css("display","inline-block");
        }else{
            $("#spp3").css("display","none");
        }
    })
    //密码的input失去焦点事件
    $("#cronExpression").blur(function(){
        debugger
        var cronExpression = $("#cronExpression").val();
        if(cronExpression === ''){
            $("#spp4").html("不能为空");
            $("#spp4").css("display","inline-block");
        }else{
            $("#spp4").css("display","none");
        }
    })
</script>
