/*function getAllExceptionTypesByTime(sTime, eTime){
    let sTimeTmp = new Date().Format("yyyy-MM-dd 00:00:00");
    let eTimeTmp = new Date().Format("yyyy-MM-dd 23:59:59");
    if(sTime == null || sTime === ""){
        sTime = sTimeTmp;
        eTime = eTimeTmp;
    }
    if(eTime == null || eTime === ""){
        eTime = eTimeTmp;
    }

    let result = [];
    $.ajax({
        type: "post",
        async: false,
        url: "/statistics/getAllExceptionTypesByTime",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        data: {"sTime": sTime, "eTime": eTime},
        success: function(msg){
            // console.log(msg);
            let data = $.parseJSON(msg);
            // for(let i = 0; i < data.length; i++){
            //     let obj = new Object();
            //     obj.name = data[i].name;
            //     obj.value = data[i].value;
            //     result[i]=obj;
            // }
            result = data;
        },
        error: function(e){
            console.log("statistics --> getAllExceptionTypesByTime 出错了！！！", e)
        }
    });
    return result;
}

function getTotalCountByTime(sTime, eTime, conditions){
    let sTimeTmp = new Date().Format("yyyy-MM-dd 00:00:00");
    let eTimeTmp = new Date().Format("yyyy-MM-dd 23:59:59");
    if(sTime == null || sTime === ""){
        sTime = sTimeTmp;
        eTime = eTimeTmp;
    }
    if(eTime == null || eTime === ""){
        eTime = eTimeTmp;
    }

    let result = [];
    $.ajax({
        type: "post",
        async: false,
        url: "/statistics/getTotalCountByTime",
        data: {"sTime": sTime, "eTime": eTime, "conditions": conditions},
        success: function(msg){
            result = $.parseJSON(msg);
        },
        error: function(e){
            console.log("statistics --> getTotalCountByTime 出错了！！！", e)
        }
    });
    return result;
}

function getTendencyData(sTime, eTime, conditions){
    let sTimeTmp = new Date().Format("yyyy-MM-dd 00:00:00");
    let eTimeTmp = new Date().Format("yyyy-MM-dd 23:59:59");
    if(sTime == null || sTime === ""){
        sTime = sTimeTmp;
        eTime = eTimeTmp;
    }
    if(eTime == null || eTime === ""){
        eTime = eTimeTmp;
    }

    let result = [];
    $.ajax({
        type: "post",
        async: false,
        url: "/statistics/getTendencyDataByTime",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        data: {"sTime": sTime, "eTime": eTime, "conditions": conditions},
        success: function(msg){
            // console.log(msg);
            let data = $.parseJSON(msg);
            result = data;
        },
        error: function(e){
            console.log("statistics --> getTotalCountByTime 出错了！！！", e)
        }
    });
    return result;
}

function getRankListData(sTime, eTime, conditions, limit){
    let sTimeTmp = new Date().Format("yyyy-MM-dd 00:00:00");
    let eTimeTmp = new Date().Format("yyyy-MM-dd 23:59:59");
    if(sTime == null || sTime === ""){
        sTime = sTimeTmp;
        eTime = eTimeTmp;
    }
    if(eTime == null || eTime === ""){
        eTime = eTimeTmp;
    }

    let result = {};
    $.ajax({
        type: "post",
        async: false,
        url: "/statistics/getRankListDataByTime",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        data: {"sTime": sTime, "eTime": eTime, "conditions": conditions, "limit": limit},
        success: function(msg){
            // console.log(msg);
            let data = $.parseJSON(msg);
            result = data;
        },
        error: function(e){
            console.log("statistics --> getTotalCountByTime 出错了！！！", e)
        }
    });
    return result;
}*/
//检查时间
function checkTime(sTime, eTime){
    let sTimeTmp = new Date().Format("yyyy-MM-dd 00:00:00");
    let eTimeTmp = new Date().Format("yyyy-MM-dd 23:59:59");
    if(sTime == null || sTime === ""){
        sTime = sTimeTmp;
        eTime = eTimeTmp;
    }
    if(eTime == null || eTime === ""){
        eTime = eTimeTmp;
    }
    let result = [sTime, eTime];
    return result;
}

//获取并设置异常级别总数
function getTotalCountDataAndSet(sTime, eTime, conditions){
    let time = checkTime(sTime, eTime);
    sTime = time[0];
    eTime = time[1];

    let countData = [];
    $.ajax({
        type: "post",
        async: false,
        url: "/statistics/getTotalCountByTime",
        data: {"sTime": sTime, "eTime": eTime, "conditions": conditions},
        success: function(msg){
            countData = $.parseJSON(msg);
        },
        error: function(e){
            console.log("statistics --> getTotalCountDataAndSet 出错了！！！", e)
        }
    });
    //设置异常级别总数
    $("#errorSpan").html('<font style="font-size: xx-large;">'+ (countData.ERROR === undefined ? 0 :  countData.ERROR)  +'</font>');
    $("#warnSpan").html('<font style="font-size: xx-large;">'+(countData.WARN === undefined ? 0 :  countData.WARN)+'</font>');

}

function getRankListDataAndSet(sTime, eTime, conditions, limit) {
    let errorRankChart = echarts.init(document.getElementById('errorRank'));
    let warnRankChart = echarts.init(document.getElementById('warnRank'));

    let time = checkTime(sTime, eTime);
    sTime = time[0];
    eTime = time[1];

    let rankListDatas = {};
    $.ajax({
        type: "post",
        async: false,
        url: "/statistics/getRankListDataByTime",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        data: {"sTime": sTime, "eTime": eTime, "conditions": conditions, "limit": limit},
        success: function(msg){
            // console.log(msg);
            let data = $.parseJSON(msg);
            rankListDatas = data;
        },
        error: function(e){
            console.log("statistics --> getTotalCountByTime 出错了！！！", e)
        }
    });

    //将排行的数据放到echarts图中
    let errorModule = [];
    let errorCount = [];
    let errorList = rankListDatas.errorList;
    for(let i = 0; i < errorList.length; i++){
        errorModule[errorList.length - i - 1]=errorList[i].module;
        errorCount[errorList.length - i - 1]=errorList[i].count;
    }

    let warnModule = [];
    let warnCount = [];
    let warnList = rankListDatas.warnList;
    for(let i = 0; i < warnList.length; i++){
        warnModule[warnList.length - i - 1]=warnList[i].module;
        warnCount[warnList.length - i - 1]=warnList[i].count;
    }


    //错误排行
    let errorRank = {
        color: ['#FF2D2D'],
        title: {
            text: '错误排行',
            subtext: ''
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {
            data: ['error']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            boundaryGap: [0, 0.01]
        },
        yAxis: {
            type: 'category',
            // data: ['空指针', 'classNotFound', 'IndexOutOfBonded', 'FileReadException', 'IoException', 'MySQLSyntaxErrorException']
            data: errorModule
        },
        series: [
            {
                name: 'error',
                type: 'bar',
                // data: [18203, 23489, 29034, 104970, 131744, 325478]
                data: errorCount,
                itemStyle: {
                    normal: {
                        barBorderRadius:[6, 6, 6, 6],
                        color: function(params) {
                            let colorList = ['#FF2D2D'];
                            return colorList[0]
                        }
                    }
                }
            }
        ]
    };
    //警告排行
    let warnRank = {
        color: ['#FF9224'],
        title: {
            text: '警告排行',
            subtext: ''
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {
            data: ['warn']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            boundaryGap: [0, 0.01]
        },
        yAxis: {
            type: 'category',
            // data: ['空指针', 'classNotFound', 'IndexOutOfBonded', 'FileReadException', 'IoException', 'MySQLSyntaxErrorException']
            data: warnModule
        },
        series: [
            {
                name: 'warn',
                type: 'bar',
                // data: [18203, 23489, 29034, 104970, 131744, 325478]
                data: warnCount,
                itemStyle: {
                    normal: {
                        barBorderRadius:[6, 6, 6, 6],
                        color: function(params) {
                            let colorList = ['#FF9224'];
                            return colorList[0]
                        }
                    }
                }
            }
        ]
    };

    errorRankChart.setOption(errorRank);
    warnRankChart.setOption(warnRank);
}

function getExcepTypesAndSet(sTime, eTime, limit){
    let typePieChart = echarts.init(document.getElementById('typePie'));
    let time = checkTime(sTime, eTime);
    sTime = time[0];
    eTime = time[1];

    let pieRes = [];
    $.ajax({
        type: "post",
        async: false,
        url: "/statistics/getAllExceptionTypesByTime",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        data: {"sTime": sTime, "eTime": eTime, "limit": limit},
        success: function(msg){
            let data = $.parseJSON(msg);
            pieRes = data;
        },
        error: function(e){
            console.log("statistics --> getExcepTypesAndSet 出错了！！！", e)
        }
    });

    //全圆饼图
    let option = {
        title: {
            text: '异常类型',
            subtext: '',
            left: 'true'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{b} <br/>次数：{c} ({d}%)'
        },
        series: [
            {
                name: '异常类型',
                type: 'pie',
                radius: '55%',
                center: ['50%', '50%'],
                data: pieRes,
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    typePieChart.setOption(option);
}

function getTendencyDataAndSet(sTime, eTime, conditions){
    let tendencyChart = echarts.init(document.getElementById('tendency'));
    let time = checkTime(sTime, eTime);
    sTime = time[0];
    eTime = time[1];

    let tenData = [];
    $.ajax({
        type: "post",
        async: false,
        url: "/statistics/getTendencyDataByTime",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        data: {"sTime": sTime, "eTime": eTime, "conditions": conditions},
        success: function(msg){
            // console.log(msg);
            let data = $.parseJSON(msg);
            tenData = data;
        },
        error: function(e){
            console.log("statistics --> getTotalCountByTime 出错了！！！", e)
        }
    });

    //解析成柱图的数据
    let barData = [];
    for(let i = 0; i < tenData.length; i++){
        let obj = new Object();
        obj.time = tenData[i].time;
        obj.错误 = tenData[i].ERROR === undefined ? 0 : tenData[i].ERROR;
        obj.警告 = tenData[i].WARN === undefined ? 0 : tenData[i].WARN;
        barData[i]=obj;
    }

    //异常趋势的柱状图
    let option = {
        title: {
            text: '异常趋势的柱状图'
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                saveAsImage: {},
                magicType: {show: true, type: ['line', 'bar', 'pie']},
            }
        },
        legend: {},
        tooltip: {
            trigger: 'axis'
        },
        dataset: {
            dimensions: ['time', '错误', '警告'],
            source: barData
        },
        xAxis: {type: 'category'},
        yAxis: {},
        series: [
            {type: 'bar'},
            {type: 'bar'}
        ]
    };
    tendencyChart.setOption(option);
}

function getAllDatas(sTime, eTime){
    console.log("The search time is " + sTime + " to " +eTime + ".");

    let conditions = [];
    let options = document.getElementById("conditionSlt").options;
    $.each(options, function(i, option){
        let value = option.value;
        conditions.push(value);
    });

    /*//模拟页面上的参数
    let conditions = [];
    //生产环境修改、添加下面的参数即可。
    conditions.push("ERROR");
    conditions.push("WARN");*/

    //获取的异常级别总数的数据并设置
    getTotalCountDataAndSet(sTime, eTime, conditions);

    getRankListDataAndSet(sTime, eTime, conditions, 5);

    getExcepTypesAndSet(sTime, eTime, conditions, 5);

    getTendencyDataAndSet(sTime, eTime, conditions);

}

$(function(){
    let conditions = [];
    let options = document.getElementById("conditionSlt").options;
    $.each(options, function(i, option){
        let value = option.value;
        conditions.push(value);
    });

    // //模拟页面上的参数
    // let conditions = [];
    // //生产环境修改、添加下面的参数即可。
    // conditions.push("ERROR");
    // conditions.push("WARN");
    let sTime = new Date().Format("yyyy-MM-dd 00:00:00");
    let eTime = new Date().Format("yyyy-MM-dd 23:59:59")
    getAllDatas(sTime, eTime);
    /*let errorRankChart = echarts.init(document.getElementById('errorRank'));
    let warnRankChart = echarts.init(document.getElementById('warnRank'));
    let typePieChart = echarts.init(document.getElementById('typePie'));
    let tendencyChart = echarts.init(document.getElementById('tendency'));
    // let tendencyChart1 = echarts.init(document.getElementById('tendency1'));



    let sTime = "2020-02-16 00:00:00";
    let eTime = "2020-02-20 23:59:59";






    //模拟页面上的参数
    let conditions = [];
    //生产环境修改、添加下面的参数即可。
    conditions.push("ERROR");
    conditions.push("WARN");

    //获取的异常级别总数的数据
    let countData = getTotalCountByTime(sTime, eTime, conditions);
    console.log(countData);
    //设置异常级别总数
    $("#errorSpan").html('<font style="font-size: xx-large;">'+countData.ERROR+'</font>');
    $("#warnSpan").html('<font style="font-size: xx-large;">'+countData.WARN+'</font>');


    //获取日志排行的数据
    let rankListDatas = getRankListData(sTime, eTime, conditions, 5);
    console.log(rankListDatas);

    /!*!//将排行的数据放到页面上
    let errTab = "<table>";
    $.each(rankListDatas.errorList, function(i, map){
        let tr = "<tr>";
        let td = "<td>";
        td += map.module;
        td += "</td>";
        let td1 = "<td>";
        td1 += map.count;
        td1 += "</td>";
        tr += td + td1 + "</tr>";
        errTab += tr;
    });
    errTab += "</table>";
    $("#errorRankSpan").html(errTab);

    let warnTab = "<table>";
    $.each(rankListDatas.warnList, function(i, map){
        let tr = "<tr>";
        let td = "<td>";
        td += map.module;
        td += "</td>";
        let td1 = "<td>";
        td1 += map.count;
        td1 += "</td>";
        tr += td + td1 + "</tr>";
        warnTab += tr;
    });
    warnTab += "</table>";
    $("#warnRankSpan").html(warnTab);*!/

    //将数据放到echarts图中
    let errorModule = [];
    let errorCount = [];
    let errorList = rankListDatas.errorList;
    for(let i = 0; i < errorList.length; i++){
        errorModule[errorList.length - i - 1]=errorList[i].module;
        errorCount[errorList.length - i - 1]=errorList[i].count;
    }

    let warnModule = [];
    let warnCount = [];
    let warnList = rankListDatas.warnList;
    for(let i = 0; i < warnList.length; i++){
        warnModule[warnList.length - i - 1]=warnList[i].module;
        warnCount[warnList.length - i - 1]=warnList[i].count;
    }

    //获取的异常类型的饼图的数据
    let pieRes = getAllExceptionTypesByTime(sTime, eTime);
    console.log(pieRes);

    //获取的异常趋势图的数据
    let tenData = getTendencyData(sTime, eTime, conditions);
    //解析成柱图的数据
    let barData = [];
    for(let i = 0; i < tenData.length; i++){
        let obj = new Object();
        obj.time = tenData[i].time;
        obj.错误 = tenData[i].ERROR === undefined ? 0 : tenData[i].ERROR;
        obj.警告 = tenData[i].WARN === undefined ? 0 : tenData[i].WARN;
        barData[i]=obj;
    }
    console.log(barData);

    //解析成折线图的数据
    let dateSet = [];
    let errorSet = [];
    let warnSet = [];
    for(let i = 0; i < tenData.length; i++){
        dateSet[i] = tenData[i].time;
        errorSet[i] = tenData[i].ERROR === undefined ? 0 : tenData[i].ERROR;
        warnSet[i] = tenData[i].WARN === undefined ? 0 : tenData[i].WARN;
    }
    console.log(dateSet);
    console.log(errorSet);
    console.log(warnSet);

    //错误排行
    let errorRank = {
        color: ['#FF2D2D'],
        title: {
            text: '错误排行',
            subtext: ''
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {
            data: ['error']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            boundaryGap: [0, 0.01]
        },
        yAxis: {
            type: 'category',
            // data: ['空指针', 'classNotFound', 'IndexOutOfBonded', 'FileReadException', 'IoException', 'MySQLSyntaxErrorException']
            data: errorModule
        },
        series: [
            {
                name: 'error',
                type: 'bar',
                // data: [18203, 23489, 29034, 104970, 131744, 325478]
                data: errorCount,
                itemStyle: {
                    normal: {
                        barBorderRadius:[6, 6, 6, 6],
                        color: function(params) {
                            let colorList = ['#FF2D2D'];
                            return colorList[0]
                        }
                    }
                }
            }
        ]
    };
    //警告排行
    let warnRank = {
        color: ['#FF9224'],
        title: {
            text: '警告排行',
            subtext: ''
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {
            data: ['warn']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            boundaryGap: [0, 0.01]
        },
        yAxis: {
            type: 'category',
            // data: ['空指针', 'classNotFound', 'IndexOutOfBonded', 'FileReadException', 'IoException', 'MySQLSyntaxErrorException']
            data: warnModule
        },
        series: [
            {
                name: 'warn',
                type: 'bar',
                // data: [18203, 23489, 29034, 104970, 131744, 325478]
                data: warnCount,
                itemStyle: {
                    normal: {
                        barBorderRadius:[6, 6, 6, 6],
                        color: function(params) {
                            let colorList = ['#FF9224'];
                            return colorList[0]
                        }
                    }
                }
            }
        ]
    };



    //异常类型漏斗饼图的option
    /!*let option = {
        title: {
            text: '',
            subtext: '',
            left: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{b}<br/>次数: {c} ({d}%)'
        },
        legend: {
            left: 'center',
            top: 'bottom',
            data: []
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {
                    show: true,
                    type: ['pie', 'funnel']
                },
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        series: [
            {
                name: '异常类型',
                type: 'pie',
                radius: [10, 80],
                center: ['50%', '60%'],
                roseType: 'radius',
                label: {
                    show: true
                },
                emphasis: {
                    label: {
                        show: true
                    }
                },
                data: pieRes
            }
        ]
    };*!/

    //全圆饼图
    let option = {
        title: {
            text: '异常类型',
            subtext: '',
            left: 'true'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{b} <br/>次数：{c} ({d}%)'
        },
        series: [
            {
                name: '异常类型',
                type: 'pie',
                radius: '55%',
                center: ['50%', '50%'],
                data: pieRes,
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    //异常趋势的柱状图
    let option0 = {
        title: {
            text: '异常趋势的柱状图'
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                saveAsImage: {},
                magicType: {show: true, type: ['line', 'bar', 'pie']},
            }
        },
        legend: {},
        tooltip: {
            trigger: 'axis'
        },
        dataset: {
            dimensions: ['time', '错误', '警告'],
            source: barData
        },
        xAxis: {type: 'category'},
        yAxis: {},
        series: [
            {type: 'bar'},
            {type: 'bar'}
        ]
    };

    //异常趋势的折线图
    /!*let option1 = {
        title: {
            text: '异常趋势的折线图'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['错误', '警告']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                saveAsImage: {},
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            // data: ['2020-02-16', '2020-02-17', '2020-02-18', '2020-02-19']
            data: dateSet
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: '错误',
                type: 'line',
                stack: '总量',
                // data: [1, 0, 9, 0]
                data: errorSet
            },
            {
                name: '警告',
                type: 'line',
                stack: '总量',
                // data: [1, 0, 3, 7]
                data: warnSet
            }
        ]
    };*!/

    // 使用刚指定的配置项和数据显示图表。
    errorRankChart.setOption(errorRank);
    warnRankChart.setOption(warnRank);
    typePieChart.setOption(option);
    tendencyChart.setOption(option0);
    // tendencyChart1.setOption(option1);*/

    $('#eDate').hide();
    $('#sDate').val(new Date().Format("yyyy-MM-dd"));
    $('#eDate').val(new Date().Format("yyyy-MM-dd"));

    $('#timeSelect').change(function(){
        let selVal =  $('#timeSelect').val();
        if(selVal === 'day'){
            $('#eDate').hide();
            document.getElementById("sDate").type='date';
            $('#sDate').val(new Date().Format("yyyy-MM-dd"));
        }
        if(selVal === 'week'){
            $('#eDate').hide();
            document.getElementById("sDate").type='week';
            let num = getWeek(new Date().Format("yyyy-MM-dd"));
            let sWeek = num.toString().length === 1 ? new Date().Format("yyyy-W0" + num) : new Date().Format("yyyy-W" +num);
            $('#sDate').val(sWeek);
        }
        if(selVal === 'month'){
            $('#eDate').hide();
            document.getElementById("sDate").type='month';
            $('#sDate').val(new Date().Format("yyyy-MM"));
        }
        if(selVal === 'display'){
            document.getElementById("sDate").type='date';
            $('#sDate').val(new Date().Format("yyyy-MM-dd"));
            $('#eDate').val(new Date().Format("yyyy-MM-dd"));
            $('#eDate').show();
        }
    });


    $('#serBtn').click(function(){
        let times = getTime();
        getAllDatas(times[0], times[1]);
    });

    $('#moreRank').click(function(){
        if($("#moreRank").html() === '查看更多'){
            let times = getTime();
            getRankListDataAndSet(times[0], times[1], conditions, "");
            $("#moreRank").html("查看Top5");
        }else{
            let times = getTime();
            getRankListDataAndSet(times[0], times[1], conditions, 5);
            $("#moreRank").html("查看更多");
        }
    });



});

//获取页面要查询的时间参数
function getTime(){
    let sTime = "";
    let eTime = "";
    let selVal =  $('#timeSelect').val();
    let sDate = $('#sDate').val();
    let eDate = $('#eDate').val();
    if(selVal === 'day'){
        sTime = sDate + " 00:00:00";
        eTime = sDate + " 23:59:59";
    }
    if(selVal === 'week'){
        let year = parseInt(sDate.split('-W')[0]);
        let weeks = parseInt(sDate.split('-W')[1]);
        let s = weekGetDate(year, weeks);
        sDate = s.split('---')[0];
        eDate = s.split('---')[1];
        sTime = sDate + " 00:00:00";
        eTime = eDate + " 23:59:59";
    }
    if(selVal === 'month'){
        let flag = false;
        let year = parseInt(sDate.split("-")[0]);
        let month = parseInt(sDate.split("-")[1]);
        month = month + 1 > 12 ? 1 & (flag = true) : month + 1;
        year = flag ? year + 1 : year;
        sTime = sDate + "-01 00:00:00";
        eTime = year + "-" + month + "-01 00:00:00";
    }
    if(selVal === 'display'){
        sTime = sDate + " 00:00:00";
        eTime = eDate + " 23:59:59";
    }
    let times = [];
    times.push(sTime);
    times.push(eTime);
    return times;
}

function getWeek(dt){
    let d1 = new Date(dt);
    let d2 = new Date(dt);
    d2.setMonth(0);
    d2.setDate(1);
    let rq = d1-d2;
    let days = Math.ceil(rq/(24*60*60*1000));
    let num = Math.ceil(days/7);
    return num;
}

//通过周取日期范围   year 年   weeks 周
function weekGetDate(year,weeks){
    let date = new Date(year,"0","1");
    // 获取当前星期几,0:星期一
    let time = date.getTime();
    //当这一年的1月1日为周日时则本年有54周,否则没有54周,没有则去除第54周的提示
    let _week = date.getDay();

    if(_week != 0){//一年53周情况
        if(weeks===54){
            return '此年没有第54周';
        }
        let cnt=0;// 获取距离周末的天数
        if(_week===0){
            cnt = 7;
        }else if(_week===1){
            cnt = 6;
        }else if(_week===2){
            cnt = 5;
        }else if(_week===3){
            cnt = 4;
        }else if(_week===4){
            cnt = 3;
        }else if(_week===5){
            cnt = 2;
        }else if(_week===6){
            cnt = 1;
        }
        cnt += 1;//加1表示以星期一为一周的第一天    // 将这个长整形时间加上第N周的时间偏移
        time += cnt*24*3600000; //第2周开始时间
        let nextYear = new Date(parseInt(year,10)+1,"0","1");
        let nextWeek = nextYear.getDay();
        let lastcnt = 0;//获取最后一周开始时间到周末的天数
        if(nextWeek===0){
            lastcnt = 6;
        }else if(nextWeek===1){
            lastcnt = 0;
        }else if(nextWeek===2){
            lastcnt = 1;
        }else if(nextWeek===3){
            lastcnt = 2;
        }else if(nextWeek===4){
            lastcnt = 3;
        }else if(nextWeek===5){
            lastcnt = 4;
        }else if(nextWeek===6){
            lastcnt = 5;
        }
        if(weeks===1){//第1周特殊处理    // 为日期对象 date 重新设置成时间 time
            let start = date.Format("yyyy-MM-dd");
            date.setTime(time-24*3600000);
            let _end = date.Format("yyyy-MM-dd");
            return start + "---" + _end;
        }else if(weeks ===53){//第53周特殊处理
            //第53周开始时间
            let start = time+(weeks-2)*7*24*3600000;
            //第53周结束时间
            let end = time+(weeks-2)*7*24*3600000 + lastcnt*24*3600000 - 24*3600000;
            date.setTime(start);
            let _start = date.Format("yyyy-MM-dd");
            date.setTime(end);
            let _end = date.Format("yyyy-MM-dd");
            return _start + "---"  + _end;
        }else{
            let start = time+(weeks-2)*7*24*3600000; //第n周开始时间
            let end = time+(weeks-1)*7*24*3600000 - 24*3600000; //第n周结束时间
            date.setTime(start);
            let _start = date.Format("yyyy-MM-dd");
            date.setTime(end);
            let _end = date.Format("yyyy-MM-dd");
            return _start + "---"  + _end;
        }
    }else{//一年54周情况
        let cnt=0;// 获取距离周末的天数
        if(_week===0 && weeks===1){//第一周
            cnt = 0;
        }else if(_week===0){
            cnt = 7;
        }else if(_week===1){
            cnt = 6;
        }else if(_week===2){
            cnt = 5;
        }else if(_week===3){
            cnt = 4;
        }else if(_week===4){
            cnt = 3;
        }else if(_week===5){
            cnt = 2;
        }else if(_week===6){
            cnt = 1;
        }
        cnt += 1;//加1表示以星期一为一周的第一天
        // 将这个长整形时间加上第N周的时间偏移
        time += 24*3600000; //第2周开始时间
        let nextYear = new Date(parseInt(year,10)+1,"0","1");
        let nextWeek = nextYear.getDay();
        let lastcnt = 0;//获取最后一周开始时间到周末的天数
        if(nextWeek===0){
            lastcnt = 6;
        }else if(nextWeek===1){
            lastcnt = 0;
        }else if(nextWeek===2){
            lastcnt = 1;
        }else if(nextWeek===3){
            lastcnt = 2;
        }else if(nextWeek===4){
            lastcnt = 3;
        }else if(nextWeek===5){
            lastcnt = 4;
        }else if(nextWeek===6){
            lastcnt = 5;
        }
        if(weeks===1){//第1周特殊处理
            let start = date.Format("yyyy-MM-dd");
            date.setTime(time-24*3600000);
            let _end = date.Format("yyyy-MM-dd");
            return start + "---"  + _end;
        }else if(weeks===54){//第54周特殊处理
            //第54周开始时间
            let start = time+(weeks-2)*7*24*3600000;
            //第53周结束时间
            let end = time+(weeks-2)*7*24*3600000 + lastcnt*24*3600000 - 24*3600000;
            date.setTime(start);
            let _start = date.Format("yyyy-MM-dd");
            date.setTime(end);
            let _end = date.Format("yyyy-MM-dd");
            return _start + "---"  + _end;
        }else{
            let start = time+(weeks-2)*7*24*3600000; //第n周开始时间
            let end = time+(weeks-1)*7*24*3600000 - 24*3600000; //第n周结束时间
            date.setTime(start);
            let _start = date.Format("yyyy-MM-dd");
            date.setTime(end);
            let _end = date.Format("yyyy-MM-dd");
            return _start + "---"  + _end;
        }
    }
}
