<%@ page language = "java" contentType= "text/html; charset=UTF-8" pageEncoding= "UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>贵金属期货</title>
</head>
<body>
<h1>贵金属期货列表</h1>
<div>
    <div>
        <h2 id="hint"></h2>
    </div>
    <div id="goldShow" style="width:99%;height:200px;"></div>
    <hr>
    <div id="yinShow" style="width:99%;height:200px;"></div>

</div>
<script type="text/javascript" src="assets/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="assets/js/echarts.min.js"></script>
<script type="text/javascript">

    var goldChart = echarts.init(document.getElementById('goldShow'));

    //显示标题，图例和空的坐标轴
    goldChart.setOption({
        title: {
            text: '黄金期货实时行情图'
        },
        tooltip: {trigger: 'axis'},
        legend: {
            data:['黄金']
        },
        xAxis: {
            data: []
        },
        yAxis: {},
        series: [{
            name: '黄金',
            type: 'line',
            data: []
        }]
    });

    var yinChart = echarts.init(document.getElementById('yinShow'));

    //显示标题，图例和空的坐标轴
    yinChart.setOption({
        title: {
            text: '白银期货实时行情图'
        },
        tooltip: {trigger: 'axis'},
        legend: {
            data:['白银']
        },
        xAxis: {
            data: []
        },
        yAxis: {},
        series: [{
            name: '白银',
            type: 'line',
            data: []
        }]
    });

    var gold_data = [];
    var yin_data = [];

    function show(e) {
        var dataObj=e.data;
        if(dataObj=='stop'){
            alert("服务器停止发送数据");
            source.close();
            return;
        }
        var arr = dataObj.split(',');

        $.each(arr, function (i, item) {
            if(i==0)
                gold_data.push(item);
            else
                yin_data.push(item);
        });
        goldChart.setOption({
            series: [{
                name: '黄金',
                type: 'line',
                data: gold_data
            }]
        });
        yinChart.setOption({
            series: [{
                name: '白银',
                type: 'line',
                data: yin_data
            }]
        });
    }

    if(!!window.EventSource){
        $("#hint").html("");
        var source = new EventSource('needPricer');
        source.onmessage=function (e) {
                show(e);
        };

        source.onopen=function (e) {
            console.log("Connecting server!");
        };

        source.onerror=function () {
            console.log("error");
        };

    }else{
        $("#hint").html("您的浏览器不支持SSE！");
    }


</script>
</body>
</html>