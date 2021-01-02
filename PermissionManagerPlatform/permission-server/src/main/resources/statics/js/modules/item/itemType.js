$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'item/type/list',
        datatype: "json",
        colModel: [
            { label: 'Id', name: 'typeId', index: "type_id", width: 45, key: true },
            { label: '类别编码', name: 'typeCode', index: "type_code", width: 75 },
            { label: '类别名称', name: 'typeName', index: "type_name",width: 75 },
            { label: '排序编号', name: 'orderNum', index: "order_num", width: 75 },
            { label: '状态', name: 'status', width: 60, formatter: function(value, options, row){
                return value === 0 ?
                    '<span class="label label-danger">禁用</span>' :
                    '<span class="label label-success">正常</span>';
            }},
            { label: '创建时间', name: 'createTime', index: "create_time", width: 85}
        ],
		viewrecords: true,
        height: 425,
        rowNum: 10,
		rowList : [10,20,30,40,50,60,100],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "data.page.list",
            page: "data.page.currPage",
            total: "data.page.totalPage",
            records: "data.page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
    
});

//设置自定义数据
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};

//ztree-层级树
var ztree;

var vm = new Vue({
	el:'#pmpapp',
	data:{
		q:{
			typeCode: null,
            typeName: null,
            postId:null,

            deptId:null,
            deptName:null,
            startDate:null,
            endDate:null
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},

		reload: function (event) {
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'typeCode': vm.q.typeCode,'typeName': vm.q.typeName,'postId':vm.q.postId,'deptId':vm.q.deptId
                    ,'startDate':vm.q.startDate,'endDate':vm.q.endDate},
                page:page
            }).trigger("reloadGrid");
		},

        reset: function () {
            window.location.href=baseURL+"modules/item/itemType.html"
        },

        //点击ztree的选择事件
        deptTree: function(){
            layer.open({
                type: 1,
                offset: '30px',
                skin: 'layui-layer-molv',
                title: "选择部门",
                area: ['300px', '300px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级部门
                    vm.q.deptId = node[0].deptId;
                    vm.q.deptName = node[0].name;

                    layer.close(index);
                }
            });
        }
	}
});


$(function () {
    //加载部门树
    $.get(baseURL + "sys/dept/list", function(r){
        ztree = $.fn.zTree.init($("#deptTree"), setting, r);
        var node = ztree.getNodeByParam("deptId", vm.q.deptId);
        if(node != null){
            ztree.selectNode(node);

            //vm.user.deptName = node.name;
        }
    });


    //初始化InputMask控件
    //Date picker
    $('#daterange-btn').daterangepicker({

            ranges: {
                '今日': [moment().startOf('day'), moment()],
                '昨日': [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],
                '最近7日': [moment().subtract('days', 6), moment()],
                '最近30日': [moment().subtract('days', 29), moment()]
            },
            opens: 'left', //日期选择框的弹出位置
            buttonClasses: ['btn btn-default'],
            applyClass: 'btn-small btn-primary blue',
            cancelClass: 'btn-small',
            format: 'YYYY-MM-DD HH:mm:ss', //控件中from和to 显示的日期格式
            separator: ' to ',
            locale: {
                applyLabel: '确定',
                cancelLabel: '取消',
                fromLabel: '打卡起始时间',
                toLabel: '打卡结束时间',
                customRangeLabel: '手动选择',
                daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
                monthNames: ['一月', '二月', '三月', '四月', '五月', '六月','七月', '八月', '九月', '十月', '十一月', '十二月'],
                firstDay: 1
            },

            startDate: moment(),
            endDate: moment().endOf('month')
        },
        function(start, end) {
            $('#daterange-btn span').html(start.format('YYYY-MM-DD') + ' 到 ' + end.format('YYYY-MM-DD'));
            //alert(start.format('YYYY-MM-DD') + " " + end.format('YYYY-MM-DD'));

            vm.q.startDate=start.format('YYYY-MM-DD');
            vm.q.endDate=end.format('YYYY-MM-DD');
        }
    );


});

/*下框框动态绑定数据-案例*/
// $('#postList').change(function () {
//    var val=$(this).children("option:selected").val();
//    vm.q.postId=val;
// });
//
// $(function () {
//     //下框框动态绑定数据
//     $.get(baseURL + "sys/post/select", function(r){
//         var dataList=r.data.list;
//         for(var key in dataList){
//             var data=dataList[key];
//
//             $("#postList").append("<option value='" + data.postId + "'>" + data.postName + "</option>");
//         }
//     });
//
//     $("#postList").prepend("<option value='0'>请选择岗位....</option>");
// });



//以下为DateTimePicker的配置 https://www.cnblogs.com/beileixinqing/p/7794190.html

/*$('#config-demo').daterangepicker({
    maxDate: moment(), //最大时间
    dateLimit: {
        days: 30
    }, //起止时间的最大间隔
    showDropdowns: true,
    showWeekNumbers: true, //是否显示第几周
    timePicker: false, //是否显示小时和分钟
    timePickerIncrement: 60, //时间的增量，单位为分钟
    timePicker12Hour: false, //是否使用12小时制来显示时间
    ranges: {
        //'最近1小时': [moment().subtract('hours',1), moment()],
        '今日': [moment().startOf('day'), moment()],
        '昨日': [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],
        '最近7日': [moment().subtract('days', 6), moment()],
        '最近30日': [moment().subtract('days', 29), moment()]
    },
    opens: 'right', //日期选择框的弹出位置
    buttonClasses: ['btn btn-default'],
    applyClass: 'btn-small btn-primary blue',
    cancelClass: 'btn-small',
    format: 'YYYY-MM-DD HH:mm:ss', //控件中from和to 显示的日期格式
    separator: ' to ',
    locale: {
        applyLabel: '确定',
        cancelLabel: '取消',
        fromLabel: '起始时间',
        toLabel: '结束时间',
        customRangeLabel: '手动选择',
        daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
        monthNames: ['一月', '二月', '三月', '四月', '五月', '六月',
            '七月', '八月', '九月', '十月', '十一月', '十二月'
        ],
        firstDay: 1
    }
}, function(start, end, label) {
    console.log("New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')");
});*/



















