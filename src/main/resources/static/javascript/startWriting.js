/**
 * 开单相关的js文件
 */
var gform;
function createEntItemTr(form) {
	if(form!=null&&form!=undefined){
		gform=form;
	}
	
	var size = $("#itemTable").find("tr").size();
	var str = ' <tr id="entItemTr'+size+'">'
			+ '<td style="text-align: left;">'
			+ '<input type="text"  name="entItemName" id="entItemName'+size+'" value=""  onFocus=\'autoItemByName("entItemName'+size+'");createEntItem(this)\' placeholder="请输入快查码" class="layui-input"/>'
			+ '<input type="hidden" name="entItemId" id="entItemId'+size+'" value="">'
			+ '</td>'
			+ '<td  style="text-align: center;">'
				+ '<input  type="text" name="artificerName" id="artificerName'+size+'" placeholder="请输入技师编码或姓名" onfocus="autoArtificerByName(\'artificerName'+size+'\')"  class="layui-input">'
				+ '<input type="hidden" name="artificerId" id="artificerId'+size+'" value="">'
			+ '</td>'
			+ '<td style="text-align: center;">'
				+ '<input type="text" name="num" id="num'+size+'"value="0" onchange="itemPrice()" class="layui-input" >'
			+ '</td>'
			+ '<td style="text-align: center;">'
				+ '<input type="text" readonly="readonly" id="price'+size+'" name="price" class="layui-input">'
			+ '</td>'
			+ '<td>'
        	+ '<select id="serviceType'+size+'" name="serviceType">'
			+ '<option value="1">轮钟</option>'
			+ '<option value="2">点钟</option>'
			+ '<option value="3">加钟</option>'
			+ '</select>'
			+ '</td>'
			+ '<td align="center" style="text-align: center;padding-top:8px;">'
			+ '<a style="cursor: pointer;" onclick="deleteItemTr(this)"><i class="layui-icon">&#x1006;</i></a>'
			+ '</td>' 
			+ '</tr>';
	$("#itemTable tr").last().before(str);
	gform.render('select');
}

function createEntItem(v){
	var value=$(v).val();
	if(null==value||value.length<=0){
		return ;
	}
	var id=$(v).parent().parent().attr("id");
	if(id==$("#itemTable tr").eq(-2).attr("id")){
		createEntItemTr();
	}
}

//删除项目表格行
function deleteItemTr(tr) {
	// 删除规格值
	if ($("#itemTable").find("tr").size() <= 3) {
		layer.msg("必须至少保留一个");
		return;
	} else {
		var dd = $(tr).parent().parent();
		$(dd).remove();
		//计算总金额、总数量
		itemPrice();
	}
}