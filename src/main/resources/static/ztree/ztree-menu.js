//树形只包含菜单，不包含按钮
function onlyGetMenuTree() {
    var root = {
        id : 0,
        name : "root",
        open : true,
    };

    $.ajax({
        type : 'get',
        url : '/permission/listAllPermission',
        contentType : "application/json; charset=utf-8",
        async : false,
        success : function(ret) {
            var data = ret.data
            var length = data.length;
            var children = [];
            for (var i = 0; i < length; i++) {
                var d = data[i];
                var node = createNode(d,true);
                children[i] = node;
            }
			console.log(children);
            root.children = children;
        }
    });

    return root;
}
//获取的树形包括菜单和按钮
function getMenuTree() {
	var root = {
		id : 0,
		name : "root",
		open : true,
	};

	$.ajax({
		type : 'get',
		url : '/permission/listAllPermission',
		contentType : "application/json; charset=utf-8",
		async : false,
		success : function(ret) {
			var data = ret.data
			var length = data.length;
			var children = [];
			for (var i = 0; i < length; i++) {
				var d = data[i];
				var node = createNode(d);
				children[i] = node;
			}

			root.children = children;
		}
	});

	return root;
}

function initMenuDatas(roleId){
    $.ajax({
        type : 'get',
        url : '/permission/listAllPermissionByRoleId?id=' + roleId,
        success : function(ret) {
            var data = ret.data;
            var length = data.length;
            var ids = [];
            for(var i=0; i<length; i++){
                ids.push(data[i]['id']);
            }

            initMenuCheck(ids);
        }
    });
}

function initMenuCheck(ids) {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var length = ids.length;
	if(length > 0){
		var node = treeObj.getNodeByParam("id", 0, null);
		treeObj.checkNode(node, true, false);
	}
	
	for(var i=0; i<length; i++){
		var node = treeObj.getNodeByParam("id", ids[i], null);
		treeObj.checkNode(node, true, false);
	}
	
}

function initRadioCheckTree(){
	var id = $("#parentId").attr("value");
	if(id != undefined && id.length > 0){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var node = treeObj.getNodeByParam("id", id, null);
        treeObj.checkNode(node, true, false);
	}
}
function initSelectType(){
    var type = $("#selectType").attr("value");
    if(type != undefined && type.length > 0){
        $("#selectType").siblings("div.layui-form-select").find('dl').find('dd[lay-value='+type+']').click()
    }
}
function getCheckedMenuIds(){
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getCheckedNodes(true);
	
	var length = nodes.length;
	var ids = [];
	for(var i=0; i<length; i++){
		var n = nodes[i];
		var id = n['id'];
		ids.push(id);
	}
	
	return ids;
}
//noShowBtn:树形列表中不显示按钮选择项，默认没值代表显示，true代表不显示
function createNode(d,noShowBtn) {

	var id = d['id'];
	var pId = d['parentId'];
	var name = d['name'];
	var child = d['child'];

	var node = {
		open : true,
		id : id,
		name : name,
		pId : pId,
	};

	if (child != null) {
		var length = child.length;
		if (length > 0) {
			var children = [];
			var j = 0;
			for (var i = 0; i < length; i++) {
                if(!(noShowBtn && child[i].type == 2)){
                    children[j] = createNode(child[i],noShowBtn);
                    j++;
                }
			}
			if(children.length > 0){
                node.children = children;
			}

		}

	}
	return node;
}

function initParentMenuSelect(){
	$.ajax({
        type : 'get',
        url : '/permissions/parents',
        async : false,
        success : function(data) {
            var select = $("#parentId");
            select.append("<option value='0'>root</option>");
            for(var i=0; i<data.length; i++){
                var d = data[i];
                var id = d['id'];
                var name = d['name'];
                
                select.append("<option value='"+ id +"'>" +name+"</option>");
            }
        }
    });
}

function getSettting(isRadioType){
	var setting = {
		check : {
			enable : true,
			chkboxType : {
				"Y" : "ps",
				"N" : "ps"
			}
		},
		async : {
			enable : true,
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : 0
			}
		},
		callback : {
			onCheck : zTreeOnCheck
		}
	};
	if(isRadioType){
        setting.check =  {
            enable: true,
			chkStyle: "radio",
			radioType: "all"
        }
	}
	return setting;
}

function zTreeOnCheck(event, treeId, treeNode) {
//	console.log(treeNode.id + ", " + treeNode.name + "," + treeNode.checked
//			+ "," + treeNode.pId);
//	console.log(JSON.stringify(treeNode));
}