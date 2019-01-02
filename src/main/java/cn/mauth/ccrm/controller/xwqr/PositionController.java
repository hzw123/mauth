package cn.mauth.ccrm.controller.xwqr;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.xwqr.SysPosition;
import cn.mauth.ccrm.server.xwqr.PositionServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/position")
public class PositionController extends BaseController{

	private static final String VIEW="/sys/position/";
	@Autowired
	private PositionServer positionServer;

	/**
	 * 部门树页面
	 */
	@RequestMapping("/list")
	public String list() throws Exception {
		return redirect(VIEW,"list");
	}
	
	/**
	 * 添加部门信息
	 */
	@RequestMapping("/edit")
	public String edit(Integer dbid, Model model) {
		if (dbid > 0) {
			SysPosition position2 = positionServer.get(dbid);
			model.addAttribute("position", position2);
		}
		return redirect(VIEW,"edit");
	}

	/**
	 * 部门信息保存
	 */
	@RequestMapping("/save")
	public void save(SysPosition position,HttpServletRequest request) throws Exception {
		Integer parentId = ParamUtil.getIntParam(request, "parentId", -1);
		try {
			if (null!=position.getDbid()&&position.getDbid()>0) {
				SysPosition parent=null;
				if(parentId>0){
					 parent = positionServer.get(parentId);
				}
				SysPosition position2 = positionServer.get(position.getDbid());
				position2.setDiscription(position.getDiscription());
				position2.setName(position.getName());
				position2.setParent(parent);
				position2.setSuqNo(position.getSuqNo());
				positionServer.save(position2);
			}
			else{
				if(parentId>0){
					SysPosition parent = positionServer.get(parentId);
					position.setParent(parent);
					positionServer.save(position);
				}else{
					position.setParent(null);
					positionServer.save(position);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderErrorMsg(e, "");
		}
		renderMsg("/position/list", "保存数据成功！");
	}

	/**
	 * 删除部门信息
	 */
	@RequestMapping("/delete")
	public void delete(Integer dbid) throws Exception {
		positionServer.deleteById(dbid);

		renderMsg("", "删除数据成功！");
	}

	@RequestMapping("/getPositionByDbid")
	public void getPositionByDbid(Integer dbid) throws Exception{
		if(dbid>0){
			JSONObject object=new JSONObject();
			SysPosition position2 = positionServer.get(dbid);
			if(null!=position2){
				object.put("dbid", position2.getDbid());
				object.put("name", position2.getName());
				object.put("suqNo", position2.getSuqNo());
				object.put("discription", position2.getDiscription());
				renderJson(object.toString());
			}else{
				renderText("error");
			}
		} else{
			renderText("error");
		}
	}

	/**
	 * 功能描述：部门树生成JSON串
	 * 逻辑描述：默认绑定一颗根节点，更节点的父节点为0
	 */
	@ResponseBody
	@RequestMapping("/editResourceJson")
	public Object editResourceJson() {

		JSONArray array=new JSONArray();

		List<SysPosition> positions = positionServer.findAll((root, query, cb) -> {
			return cb.isNull(root.get("parent"));
		},Sort.by("suqNo"));

		if (null != positions && positions.size() > 0) {
			JSONObject jsonObject=null;
			for (SysPosition position : positions) {
				jsonObject = makeJSONObject(position);
				if(jsonObject!=null){
					array.add(jsonObject);
				}
			}
		}
		return array;
	}

	/**
	 * 前台拖拽更新资源
	 */
	@RequestMapping("/updateParent")
	public void updateParent(Integer dbid,Integer parentId)  {
		if(dbid>0){
			SysPosition position2 = positionServer.get(dbid);
			SysPosition parent = positionServer.get(parentId);
			position2.setParent(parent);
			positionServer.save(position2);
		}
		renderMsg("", "更新数据成功！");
	}

	/**
	 *功能描述： 将传入的对象转化为JSON数据格式
	 * 部门树：根节点，具有子结构节点，子节点
	 * @throws JSONException
	 */
	private JSONObject makeJSONObject(SysPosition position) throws JSONException {
		JSONObject jObject = new JSONObject();

		List<SysPosition> children = positionServer.findAll((root, query, cb) -> {
			return cb.equal(root.join("parent").get("dbid"),position.getDbid());
		}, Sort.by("suqNo"));

		if (null != children && children.size() > 0) {// 如果子部门不空
			if (position.getParent() != null&&position.getParent().getDbid()>0) {
				jObject.put("icon","/widgets/ztree/css/zTreeStyle/img/diy/2.png");// 菜单阶段
			} else{
				jObject.put("icon","/widgets/ztree/css/zTreeStyle/img/diy/2.png");// 菜单阶段
			}
			
			jObject.put("id", position.getDbid());
			jObject.put("name", position.getName());
			jObject.put("open", true);
			jObject.put("children", makeJSONChildren(children));
			return jObject;
		} else {
			if (position.getParent() != null&&position.getParent().getDbid()>0) {
				jObject.put("icon","/widgets/ztree/css/zTreeStyle/img/diy/2.png");// 菜单阶段
			}
			jObject.put("id", position.getDbid());
			jObject.put("name", position.getName());
			jObject.put("children", "");
			return jObject;
		}
	}

	/**
	 * 将部门数据生成可以编辑的JSON格式
	 * **/
	private JSONArray makeJSONChildren(List<SysPosition> children)throws JSONException {
		JSONArray jsonArray = new JSONArray();
		for (SysPosition position : children) {
			JSONObject subJSONjObject = makeJSONObject(position);
			jsonArray.add(subJSONjObject);
		}
		return jsonArray;
	}
}
