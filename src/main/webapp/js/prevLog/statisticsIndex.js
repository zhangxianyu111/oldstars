function getAllExceptionTypesByTime(sTime, eTime){
    let sTimeTmp = new Date().Format("yyyy-MM-dd 00:00:00");
    let eTimeTmp = new Date().Format("yyyy-MM-dd 23:59:59");
    if(sTime == null || sTime === ""){
        sTime = sTimeTmp;
        eTime = eTimeTmp;
    }
    if(eTime == null || eTime === ""){
        eTime = eTimeTmp;
    }

    let res = [];
    $.ajax({
        type: "post",
        async: false,
        url: "/statistics/getAllExceptionTypesByTime",
        data: {"sTime": sTime, "eTime": eTime},
        success: function(data){
            console.log(data);
            res = data;
        },
        error: function(e){
            console.log("statistics --> getAllExceptionTypesByTime 出错了！！！", e)
        }
    });
    return res;
}

function getTotalCountByTime(sTime, eTime, conditions){
    alert(conditions);
    let res = [];
    $.ajax({
        type: "post",
        async: false,
        url: "/statistics/getTotalCountByTime",
        dataType: 'json',
        data: {"sTime": sTime, "eTime": eTime, "conditions": conditions},
        success: function(data){
            console.log(data);
            res = data;
        },
        error: function(e){
            console.log("statistics --> getTotalCountByTime 出错了！！！", e)
        }
    });
    return res;
}

$(function(){
    let myChart = echarts.init(document.getElementById('main'));
    let myChart0 = echarts.init(document.getElementById('main0'));
    let myChart1 = echarts.init(document.getElementById('main1'));

    let res = getAllExceptionTypesByTime("2020-02-16 00:00:00", "2020-02-18 23:59:59");
    // let res = getAllExceptionTypesByTime(sTime, eTime);

    let conditions = [];
    conditions.push("ERROR");
    conditions.push("WARN");

    let result = getTotalCountByTime("2020-02-16 00:00:00", "2020-02-18 23:59:59", conditions);

    let option = {
        title: {
            text: '',
            subtext: '',
            left: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>次数: {c} ({d}%)'
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
                name: '异常名称',
                type: 'pie',
                radius: [10, 80],
                center: ['50%', '60%'],
                roseType: 'radius',
                label: {
                    show: false
                },
                emphasis: {
                    label: {
                        show: true
                    }
                },
                data:
                    res
            }
        ]
    };



    // let option = {
    //     title: {
    //         // text: '异常类名异常量TOP5分布',
    //         subtext: '',
    //         left: 'center'
    //     },
    //     tooltip: {
    //         trigger: 'item',
    //         // formatter: '{a} <br/> {b} : {c} ({d}%)'
    //         formatter: '{b} <br/>次数:{c} ({d}%)'
    //     },
    //     toolbox: {
    //         show: false,
    //         feature: {
    //             saveAsImage: {show: true}
    //         }
    //     },
    //     label: {
    //         show: false
    //     },
    //     emphasis: {
    //         label: {
    //             show: true
    //         }
    //     },
    //     series: [
    //         {
    //             name: '异常类',
    //             type: 'pie',
    //             radius: [5, 100],
    //             center: ['50%', '60%'],
    //             roseType: 'area',
    //             label: {
    //                 show: false
    //             },
    //             data: res
    //                 // [{name: 'notDeFoundException', value: 135},
    //                 // {name: 'NULLPOINTEException', value: 510},
    //                 // {name: '7', value: 235},
    //                 // {name: 'class', value: 734},
    //                 // {name: 'method', value: 635},
    //                 // { name: 'test', value: 848}]
    //         }
    //     ]
    // };

    let option0 = {
        legend: {},
        tooltip: {},
        dataset: {
            source: [
                ['product', '错误日志', '警告日志'],
                ['2020-02-10', 43.3, 85.8],
                ['2020-02-11', 83.1, 73.4],
                ['2020-02-12', 43.3, 85.8],
                ['2020-02-13', 83.1, 73.4],
                ['2020-02-14', 86.4, 65.2],
                ['2020-02-15', 43.3, 85.8],
                ['2020-02-16', 83.1, 73.4],
                ['2020-02-17', 43.3, 85.8],
                ['2020-02-18', 83.1, 73.4],
                ['2020-02-19', 86.4, 65.2],
                ['2020-02-20', 43.3, 85.8],
                ['2020-02-21', 83.1, 73.4],
                ['2020-02-22', 43.3, 85.8],
                ['2020-02-23', 83.1, 73.4],
                ['2020-02-24', 86.4, 65.2],
                ['2020-02-25', 43.3, 85.8],
                ['2020-02-26', 83.1, 73.4],
                ['2020-02-27', 43.3, 85.8],
                ['2020-02-28', 83.1, 73.4],
                ['2020-02-29', 86.4, 65.2],
                ['2020-03-01', 43.3, 85.8],
                ['2020-03-02', 83.1, 73.4],
                ['2020-03-03', 43.3, 85.8],
                ['2020-03-04', 83.1, 73.4],
                ['2020-03-05', 86.4, 65.2],
                ['2020-03-06', 72.4, 53.9]
            ]
        },
        xAxis: {type: 'category'},
        yAxis: {},
        series: [
            {type: 'bar'},
            {type: 'bar'}
        ]
    };

    //折线
    let option1 = {
        title: {
            text: '折线图堆叠'
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
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: '错误',
                type: 'line',
                stack: '总量',
                data: [120, 132, 101, 134, 90, 230, 210]
            },
            {
                name: '警告',
                type: 'line',
                stack: '总量',
                data: [220, 182, 191, 234, 290, 330, 310]
            }
        ]
    };



    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    myChart0.setOption(option0);
    myChart1.setOption(option1);


});