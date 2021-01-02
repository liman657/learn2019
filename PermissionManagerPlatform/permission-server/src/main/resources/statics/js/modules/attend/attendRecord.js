$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'attend/record/list',
        datatype: "json",
        colModel: [
            { label: 'ID', name: 'id', index: "id", width: 45,sortable:false, key: true },
            { label: '部门名称', name: 'deptName', sortable:false, width: 75 },
            { label: '姓名', name: 'userName', sortable:false, width: 75 },
            { label: '日期', name: 'createTime', sortable:false, width: 85 },
            { label: '打卡状态', name: 'status', width: 60,sortable:false, formatter: function(value, options, row){
                return value === 0 ?
                    '<span class="label label-danger">未打卡</span>' :
                    '<span class="label label-success">已打卡</span>';
            }},
            { label: '打卡开始时间', name: 'startTime',sortable:false, width: 85},
            { label: '打卡结束时间', name: 'endTime',sortable:false, width: 85},
            { label: '工时/小时', name: 'total',sortable:false, width: 35}
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
			userName: null,

            deptId:null,
            deptName:null,

            startDate:null,
            endDate:null,

            status:null
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},

		reload: function (event) {
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'userName': vm.q.userName,'deptId':vm.q.deptId,'startDate':vm.q.startDate,'endDate':vm.q.endDate,'status':vm.q.status},
                page:page
            }).trigger("reloadGrid");
		},

        reset: function () {
            window.location.href=baseURL+"modules/attend/attendRecord.html"
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
        },

        exportExcel:function () {
            var userName='';
            if (vm.q.userName!=null){
                userName=vm.q.userName;
            }
            var deptId='';
            if(vm.q.deptId!=null){
                deptId=vm.q.deptId;
            }
            var startDate='';
            if (vm.q.startDate!=null){
                startDate=vm.q.startDate
            }
            var endDate='';
            if (vm.q.endDate!=null){
                endDate=vm.q.endDate
            }
            var status='';
            if (vm.q.status!=null){
                status=vm.q.status
            }

            var params='?userName='+userName+'&deptId='+deptId+'&startDate='+startDate+'&endDate='+endDate+'&status='+status;
            window.location.href= baseURL + "attend/record/export"+params;
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

    vm.q.status=null;
});

//下拉框
$('#attendStatus').change(function () {
    var value=$(this).children("option:selected").val();
    //alert(value);
    //console.log(value);

    vm.q.status=value;
});



















