$(function () {
    //初始化加载数据
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/user/list',
        datatype: "json",
        //数据字段一一绑定 <> 根据name
        colModel: [			
			{ label: 'Id', name: 'userId', index: "user_id", width: 45, key: true },
			{ label: '用户名', name: 'username', width: 75 },
            { label: '姓名', name: 'name', width: 75 },
            { label: '岗位', name: 'postName', width: 75 },
            { label: '所属部门', name: 'deptName', sortable: false, width: 75 },
			{ label: '邮箱', name: 'email', width: 90 },
			{ label: '手机号', name: 'mobile', width: 100 },
			{ label: '状态', name: 'status', width: 60, formatter: function(value, options, row){
				return value === 0 ? 
					'<span class="label label-danger">禁用</span>' : 
					'<span class="label label-success">正常</span>';
			}},
			{ label: '创建时间', name: 'createTime', index: "create_time", width: 85}
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,20,30,40,50,60,100],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        //读取服务器返回的json数据并解析
        jsonReader : {
            root: "data.page.list",
            page: "data.page.currPage",
            total: "data.page.totalPage",
            records: "data.page.totalCount"
        },
        //设置jqGrid将要向服务端传递的参数名称
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
            username: null
        },
        showList: true,
        title:null,
        roleList:{},
        postList:{},

        user:{
            status:1,
            deptId:null,
            deptName:null,
            roleIdList:[],
            postIdList:[]
        }
    },

    //请求方法
    methods: {

        //查询
        query: function () {
            vm.reload();
        },

        //重新load数据时的请求方法
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'username': vm.q.username},
                page:page
            }).trigger("reloadGrid");
        },

        //重置
        reset: function () {
            window.location.href=baseURL+"modules/sys/user.html"
        },

        //进入新增
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.roleList = {};
            vm.postList = {};
            vm.user = {deptName:null, deptId:null, status:1, roleIdList:[],postIdList:[]};

            this.getRoleList();
            this.getPostList();
            this.getDept();
        },

        //获取角色列表
        getRoleList: function(){
            $.get(baseURL + "sys/role/select", function(r){
                vm.roleList = r.data.list;
            });
        },


        //获取岗位列表
        getPostList: function(){
            $.get(baseURL + "sys/post/select", function(r){
                vm.postList = r.data.list;
            });
        },

        //获取部门列表
        getDept: function(){
            //加载部门树
            $.get(baseURL + "sys/dept/list", function(r){
                ztree = $.fn.zTree.init($("#deptTree"), setting, r);
                var node = ztree.getNodeByParam("deptId", vm.user.deptId);
                if(node != null){
                    ztree.selectNode(node);

                    vm.user.deptName = node.name;
                }
            })
        },

        //进入修改
        update: function () {
            var userId = getSelectedRow();
            if(userId == null){
                return ;
            }

            vm.showList = false;
            vm.title = "修改";

            //详情
            vm.getInfo(userId);

            this.getRoleList();
            this.getPostList();
        },

        //根据userId获取用户信息
        getInfo: function(userId){
            $.get(baseURL + "sys/user/info/"+userId, function(r){
                vm.user = r.data.user;
                vm.user.password = null;

                vm.getDept();
            });
        },

        //删除
        del: function () {
            var userIds = getSelectedRows();
            if(userIds == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/user/delete",
                    contentType: "application/json",
                    data: JSON.stringify(userIds),
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },

        //保存-更新数据
        saveOrUpdate: function () {
            var url = vm.user.userId == null ? "sys/user/save" : "sys/user/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.user),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
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
                    vm.user.deptId = node[0].deptId;
                    vm.user.deptName = node[0].name;

                    layer.close(index);
                }
            });
        },
        
        //重置密码：
        resetPsd:function () {
            var userIds = getSelectedRows();
            if(userIds == null){
                return ;
            }

            confirm('确定要重置选中的用户的密码？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/user/psd/reset",
                    contentType: "application/json",
                    data: JSON.stringify(userIds),
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        }


    }
});



