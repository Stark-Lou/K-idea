<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>K线图在线比对系统</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <style>
        .line{
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
<div class="jumbotron">
    <h2 class="text-center">K线图在线对比系统</h2>
    <p class="text-center">
        <%--data-target="#myModal" onclick="getKLineGraph()"--%>
        <a class="btn btn-primary btn-lg " href="#" role="button" onclick="getKLineGraph()">相似度分析</a>
        <a class="btn btn-primary btn-lg " href="#" role="button" data-toggle="modal" data-target="#myModal">参数设置</a>
    </p>
</div>
<div class="container-fluid">
    <div class="row line">
        <div class="col-md-6">
            <div id="container1" style="height: 600px;width: 100%;"></div>
        </div>
        <div class="col-md-6">
            <div id="container2" style="height: 600px;width: 100%;"></div>
        </div>
    </div>
    <div class="row line">
        <div class="col-md-6">
            <div id="container3" style="height: 600px;width: 100%;"></div>
        </div>
        <div class="col-md-6">
            <div id="container4" style="height: 600px;width: 100%;"></div>
        </div>
    </div>
    <div class="row line">
        <div class="col-md-6">
            <div id="container5" style="height: 600px;width: 100%;"></div>
        </div>
        <div class="col-md-6">
            <div id="container6" style="height: 600px;width: 100%;"></div>
        </div>
    </div>
    <nav>
        <ul class="pager">
            <li><a href="#" onclick="prePage()">&larr; 上一页</a></li>
            <li><a href="#" onclick="nextPage()">下一页 &rarr;</a></li>
        </ul>
    </nav>

    <!-- Modal -->
    <div class="modal fade bs-example-modal-lg" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="myModalLabel">参数设置</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><span class="glyphicon glyphicon-cog"></span>对比算法</h3>
                                </div>
                                <div class="panel-body">
                                    <div class="col-md-4 line">
                                        <div class="input-group input-group-lg">
                                            <span class="input-group-addon">
                                                <span class="glyphicon glyphicon-stats"></span>欧氏距离法
                                            </span>
                                            <input type="number" class="form-control" name="algorithmInput" id="algorithmInput1" placeholder="权重">
                                        </div>
                                    </div>
                                    <div class="col-md-4 line">
                                        <div class="input-group input-group-lg">
                                            <span class="input-group-addon">
                                                <span class="glyphicon glyphicon-stats"></span>余弦相似度
                                            </span>
                                            <input type="number" class="form-control" name="algorithmInput" id="algorithmInput2" placeholder="权重">
                                        </div>
                                    </div>
                                    <div class="col-md-4 line">
                                        <div class="input-group input-group-lg">
                                            <span class="input-group-addon">
                                                <span class="glyphicon glyphicon-stats"></span>动态时间规整
                                            </span>
                                            <input type="number" class="form-control" name="algorithmInput" id="algorithmInput3" placeholder="权重">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-8 line">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><span class="glyphicon glyphicon-file"></span>目标文件</h3>
                                </div>
                                <div class="panel-body">
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="fileNameInput">
                                        <div class="input-group-btn">
                                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                                <span class="caret"></span>
                                            </button>
                                            <ul class="dropdown-menu dropdown-menu-right" role="menu">
                                            </ul>
                                        </div>
                                        <!-- /btn-group -->
                                    </div>
                                </div>
                            </div>

                        </div>
                        <div class="col-md-4 line">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><span class="glyphicon glyphicon-th-large"></span>对比内容</h3>
                                </div>
                                <div class="panel-body">
                                    <div class="col-md-6">
                                        <label class="checkbox">
                                            <input type="checkbox" name="checkboxOptions" id="checkbox1" value="open"> 开盘价
                                        </label>
                                        <label class="checkbox">
                                            <input type="checkbox" name="checkboxOptions" id="checkbox2" value="close"> 收盘价
                                        </label>
                                        <label class="checkbox">
                                            <input type="checkbox" name="checkboxOptions" id="checkbox3" value="volume"> 成交量
                                        </label>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="checkbox">
                                            <input type="checkbox" name="checkboxOptions" id="checkbox4" value="highest"> 最高价
                                        </label>
                                        <label class="checkbox">
                                            <input type="checkbox" name="checkboxOptions" id="checkbox5" value="lowest"> 最低价
                                        </label>
                                        <label class="checkbox">
                                            <input type="checkbox" name="checkboxOptions" id="checkbox6" value="stretch" checked="checked"> 拉伸
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" onclick="setParas()">确定</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="js/jquery.min.js "></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootstrap-suggest.js"></script>
<script src="js/echarts.js"></script>
<script type="text/javascript">
    var total = 0;
    var page = 1;
    //算法权重
    var algorithmWeight = "";
    //对比内容
    var contrastContent = "";
    //目标文件
    var targetFile = "";

    function setParas() {
        //重新初始化
        algorithmWeight="";
        contrastContent="";
        targetFile = document.getElementById("fileNameInput").value;
        var algorithmInput = document.getElementsByName('algorithmInput');
        for(var i=0; i<algorithmInput.length; i++){
            if(algorithmInput.item(i).valueAsNumber > 0)
                algorithmWeight += algorithmInput.item(i).valueAsNumber + ",";
            else
                algorithmWeight += "0,";
        }
        var checkbox = document.getElementsByName('checkboxOptions');
        for(var i=0; i<checkbox.length; i++){
            if(checkbox[i].checked) contrastContent+=checkbox[i].value+',';
        }
        $('#myModal').modal('hide');

    }

    function initTest() {
        $("#fileNameInput").bsSuggest('init', {
            /*url: "/rest/sys/getuserlist?keyword=",
             effectiveFields: ["userName", "email"],
             searchFields: [ "shortAccount"],
             effectiveFieldsAlias:{userName: "姓名"},*/
            url: "getStockInfo",
            effectiveFieldsAlias:{stockName: "股票名", fileName: "文件"},
            showHeader: true,
            delayUntilKeyup: true, //获取数据的方式为 firstByUrl 时，延迟到有输入/获取到焦点时才请求数据
            idField: "stockName",
            keyField: "fileName"
        }).on('onDataRequestSuccess', function (e, result) {
            console.log('onDataRequestSuccess: ', result);
        }).on('onSetSelectValue', function (e, keyword, data) {
            console.log('onSetSelectValue: ', keyword, data);
        }).on('onUnsetSelectValue', function () {
            console.log('onUnsetSelectValue');
        });
    }
    //按钮方法事件监听
    $('#methodTest button').on('click', function() {
        var method = $(this).text();
        var $i;

        if (method === 'init') {
            initTest();
        } else {
            $i = $('#fileNameInput').bsSuggest(method);
            if (typeof $i === 'object') {
                $i = $i.data('bsSuggest');
            }
            console.log(method, $i);
            if (!$i) {
                alert('未初始化或已销毁');
            }
        }

        if (method === 'version') {
            alert($i);
        }
    });
    initTest();
    function getKLineGraph() {
        if(algorithmWeight == undefined || algorithmWeight== "0,0,0,"
            ||targetFile == undefined || targetFile== ""
            ||contrastContent == undefined || contrastContent== ""){
            alert("部分参数未设置!将执行默认运算！");
        }
        //从这个方法中触发的代表重新计算
        page = 1;
        $.ajax({
            method : 'POST',
            url : "getKLineGraph",
            data : {
                algorithmWeight: algorithmWeight,
                targetFile: targetFile,
                contrastContent: contrastContent,
            },
            success : init,
            dataType : "json"
        });
    };
    function nextPage(){
        page++;
        $.ajax({
            method : 'POST',
            url : "getKLineGraphInPage",
            data : {
                page : page,
            },
            success : init,
            error: function () {
                page--;
            },
            dataType : "json"
        });
    }
    function prePage(){
        page--;
        if(page > 0){
            $.ajax({
                method : 'POST',
                url : "getKLineGraphInPage",
                data : {
                    page : page,
                },
                success : init,
                error: function () {
                    page++;
                },
                dataType : "json"
            });
        }
    }
    function init(json){

        total = json.total;
        var rows = json.rows;
        if(total > 6)
            total = 6;
        for(var i=0; i<total;i++){
            var id = i+1;
            var dom = document.getElementById("container" + id);
            var myChart = echarts.init(dom);
            var data = splitData(rows[i].content);
            var option = {
                backgroundColor: '#eee',
                animation: false,
                title: {
                    text: rows[i].name + " " + rows[i].similarity,
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'line'
                    }
                },
                toolbox: {
                    feature: {
                        dataZoom: {
                            yAxisIndex: false
                        },
                        brush: {
                            type: ['lineX', 'clear']
                        }
                    }
                },
                grid: [
                    {
                        left: '10%',
                        right: '8%',
                        height: '50%'
                    },
                    {
                        left: '10%',
                        right: '8%',
                        top: '63%',
                        height: '16%'
                    }
                ],
                xAxis: [
                    {
                        type: 'category',
                        data: data.categoryData,
                        scale: true,
                        boundaryGap : false,
                        axisLine: {onZero: false},
                        splitLine: {show: false},
                        splitNumber: 20,
                        min: 'dataMin',
                        max: 'dataMax'
                    },
                    {
                        type: 'category',
                        gridIndex: 1,
                        data: data.categoryData,
                        scale: true,
                        boundaryGap : false,
                        axisLine: {onZero: false},
                        axisTick: {show: false},
                        splitLine: {show: false},
                        axisLabel: {show: false},
                        splitNumber: 20,
                        min: 'dataMin',
                        max: 'dataMax'
                    }
                ],
                yAxis: [
                    {
                        scale: true,
                        splitArea: {
                            show: true
                        }
                    },
                    {
                        scale: true,
                        gridIndex: 1,
                        splitNumber: 2,
                        axisLabel: {show: false},
                        axisLine: {show: false},
                        axisTick: {show: false},
                        splitLine: {show: false}
                    }
                ],
                dataZoom: [
                    {
                        type: 'inside',
                        xAxisIndex: [0, 1],
                        start: 0,
                        end: 100
                    },
                    {
                        show: true,
                        xAxisIndex: [0, 1],
                        type: 'slider',
                        top: '85%',
                        start: 98,
                        end: 100
                    }
                ],
                series: [
                    {
                        name: rows[i].name,
                        type: 'candlestick',
                        data: data.values,
                        itemStyle: {
                            normal: {
                                borderColor: null,
                                borderColor0: null
                            }
                        },
                        tooltip: {
                            formatter: function (param) {
                                var param = param[0];
                                return [
                                    'Date: ' + param.name + '<hr size=1 style="margin: 3px 0">',
                                    '开盘: ' + param.data[0] + '<br/>',
                                    '收盘: ' + param.data[1] + '<br/>',
                                    '最低: ' + param.data[2] + '<br/>',
                                    '最高: ' + param.data[3] + '<br/>'
                                ].join('');
                            }
                        }
                    },
                    {
                        name: 'Volume',
                        type: 'bar',
                        xAxisIndex: 1,
                        yAxisIndex: 1,
                        data: data.volume
                    }
                ]
            };
            if (option && typeof option === "object") {
                myChart.setOption(option, true);
            }
        }

    };
    function splitData(rawData) {
        var categoryData = [];
        var values = [];
        var volume = [];
        for (var i = 0; i < rawData.length; i++) {
            categoryData.push(rawData[i].splice(0, 1)[0]);
            values.push(rawData[i]);
            volume.push(rawData[i][4]);
        }
        return {
            categoryData: categoryData,
            values: values,
            volume: volume
        };
    }

</script>
</body>
</html>
