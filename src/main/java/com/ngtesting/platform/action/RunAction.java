package com.ngtesting.platform.action;

import com.alibaba.fastjson.JSONObject;
import com.ngtesting.platform.bean.websocket.OptFacade;
import com.ngtesting.platform.config.Constant;
import com.ngtesting.platform.model.TstTask;
import com.ngtesting.platform.model.TstUser;
import com.ngtesting.platform.service.CustomFieldService;
import com.ngtesting.platform.service.TestTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(Constant.API_PATH_CLIENT + "run/")
public class RunAction extends BaseAction {
    @Autowired
    private OptFacade optFacade;

	@Autowired
	TestTaskService taskService;

    @Autowired
    CustomFieldService customFieldService;

//	@RequestMapping(value = "loadCase", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> loadCase(HttpServletRequest request, @RequestBody JSONObject json) {
//		Map<String, Object> ret = new HashMap<String, Object>();
//
//		Integer orgId = json.getInteger("orgId");
//        Integer projectId = json.getInteger("projectId");
//		Integer runId = json.getInteger("runId");
//
//		List<TstCaseInRun> ls = taskService.lodaCase(runId);
//		List<TstCaseInRunVo> vos = taskService.genCaseVos(ls);
//
//        List<TstCustomField> customFieldList = customFieldService.listForCaseByProject(orgId, projectId);
//
//		ret.put("data", vos);
//        ret.put("customFields", customFieldList);
//		ret.put("code", Constant.RespCode.SUCCESS.getCode());
//		return ret;
//	}

    @RequestMapping(value = "get", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> get(HttpServletRequest request, @RequestBody JSONObject json) {
        Map<String, Object> ret = new HashMap<String, Object>();

        TstUser userVo = (TstUser) request.getSession().getAttribute(Constant.HTTP_SESSION_USER_KEY);
        Integer runId = json.getInteger("id");

        TstTask vo = taskService.getById(runId);

        ret.put("data", vo);
        ret.put("code", Constant.RespCode.SUCCESS.getCode());
        return ret;
    }

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request, @RequestBody JSONObject json) {
		Map<String, Object> ret = new HashMap<String, Object>();

		Integer id = json.getInteger("id");

		TstUser userVo = (TstUser) request.getSession().getAttribute(Constant.HTTP_SESSION_USER_KEY);

		TstTask po = taskService.delete(id, userVo.getId());

		ret.put("code", Constant.RespCode.SUCCESS.getCode());
		return ret;
	}

	@RequestMapping(value = "close", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> close(HttpServletRequest request, @RequestBody JSONObject json) {
		Map<String, Object> ret = new HashMap<String, Object>();

		Integer id = json.getInteger("id");
		TstUser userVo = (TstUser) request.getSession().getAttribute(Constant.HTTP_SESSION_USER_KEY);

		TstTask po = taskService.closePers(id, userVo.getId());
		taskService.closePlanIfAllRunClosedPers(po.getPlanId());
		TstTask vo = taskService.genVo(po);

        ret.put("data", vo);
		ret.put("code", Constant.RespCode.SUCCESS.getCode());
		return ret;
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest request, @RequestBody JSONObject json) {
		Map<String, Object> ret = new HashMap<String, Object>();

		TstUser userVo = (TstUser) request.getSession().getAttribute(Constant.HTTP_SESSION_USER_KEY);

		TstTask po = taskService.save(json, userVo);
		TstTask vo = taskService.genVo(po);

//        optFacade.opt(WsConstant.WS_TODO, userVo.getId().toString());

		ret.put("data", vo);
		ret.put("code", Constant.RespCode.SUCCESS.getCode());
		return ret;
	}

	@RequestMapping(value = "saveCases", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveCases(HttpServletRequest request, @RequestBody JSONObject json) {
		Map<String, Object> ret = new HashMap<String, Object>();

        TstUser userVo = (TstUser) request.getSession().getAttribute(Constant.HTTP_SESSION_USER_KEY);

		TstTask po = taskService.saveCases(json, userVo);
		TstTask caseVo = taskService.genVo(po);

		ret.put("data", caseVo);
		ret.put("code", Constant.RespCode.SUCCESS.getCode());
		return ret;
	}

}
