$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + '/role/list',
        datatype: "json",
        colModel: [
            { label: '角色ID', name: 'roleId', index: "role_id", width: 45, key: true },
            { label: '角色名称', name: 'roleName', index: "role_name", width: 75 },
            { label: '备注', name: 'remark', width: 100 },
            { label: '创建时间', name: 'createTime', index: "create_time", width: 80}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList : [5,10,20,50,100],
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

//菜单树
var menu_ztree;
var menu_setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "menuId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    },
    check:{
        enable:true,
        nocheckInherit:true
    }
};

//部门树
var data_ztree;
var data_setting = {
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
    },
    check:{
        enable:true,
        nocheckInherit:true
        // chkboxType:{ "Y" : "", "N" : "" }
    }
};


var vm = new Vue({
    el:'#pmpapp',
    //定义vue初始化加载的数据
    data:{
        q:{
            roleName: null
        },
        showList: true,
        title:null,

        role:{
            roleId:null,
            roleName:null,
            remark:null,

            //当前角色关联的菜单id列表
            menuIdList:[],
            //当前角色关联的部门id列表
            deptIdList:[]
        }
    },
    //定义一些操作方法
    methods: {
        query: function () {
            vm.reload();
        },

        //返回按钮的点击事件函数
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'search': vm.q.roleName},
                page:page
            }).trigger("reloadGrid");
        },

        //重置按钮的点击事件函数
        reset: function () {
            window.location.href=baseURL+"/modules/role.html"
        },

        //点击新增按钮的事件函数，这里会去加载菜单数据和部门数据
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.role = {roleId:null, roleName:null, remark:null, menuIdList:null,deptIdList:null};
            vm.getMenuTree(null);

            vm.getDeptDataTree();
        },

        //点击修改按钮的事件函数，这里会去加载菜单数据和部门数据
        update: function () {
            var roleId = getSelectedRow();
            if(roleId == null){
                return ;
            }

            vm.showList = false;
            vm.title = "修改";
            vm.getDeptDataTree();
            vm.getMenuTree(roleId);

        },

        del: function () {
            var roleIds = getSelectedRows();
            if(roleIds == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "/role/delete",
                    contentType: "application/json",
                    data: JSON.stringify(roleIds),
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
        getRole: function(roleId){
            $.get(baseURL + "/role/info/"+roleId, function(r){
                vm.role = r.data.role;

                //勾选角色所拥有的菜单
                var menuIds = vm.role.menuIdList;
                for(var i=0; i<menuIds.length; i++) {
                    var node = menu_ztree.getNodeByParam("menuId", menuIds[i]);
                    menu_ztree.checkNode(node, true, false);
                }

                //勾选角色所拥有的部门数据权限
                var deptIds = vm.role.deptIdList;
                for(var i=0; i<deptIds.length; i++) {
                    var node = data_ztree.getNodeByParam("deptId", deptIds[i]);
                    data_ztree.checkNode(node, true, false);
                }

            });
        },

        saveOrUpdate: function () {
            //获取选择的菜单
            var nodes = menu_ztree.getCheckedNodes(true);
            var menuIdList = new Array();
            for(var i=0; i<nodes.length; i++) {
                menuIdList.push(nodes[i].menuId);
            }
            vm.role.menuIdList = menuIdList;

            //获取选择的部门
            var nodes = data_ztree.getCheckedNodes(true);
            var deptIdList = new Array();
            for(var i=0; i<nodes.length; i++) {
                deptIdList.push(nodes[i].deptId);
            }
            vm.role.deptIdList = deptIdList;

            var url = vm.role.roleId == null ? "/role/save" : "role/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.role),
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

        //加载菜单树
        getMenuTree: function(roleId) {
            $.get(baseURL + "/menu/list", function(r){
                menu_ztree = $.fn.zTree.init($("#menuTree"), menu_setting, r);
                //展开所有节点
                menu_ztree.expandAll(true);

                if(roleId != null){
                    vm.getRole(roleId);
                }
            });
        },

        //加载部门树
        getDeptDataTree: function(roleId) {
            $.get(baseURL + "dept/list", function(r){
                data_ztree = $.fn.zTree.init($("#dataTree"), data_setting, r);
                data_ztree.expandAll(true);
            });
        }

    }
});