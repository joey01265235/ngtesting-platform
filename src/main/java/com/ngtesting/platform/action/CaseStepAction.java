package com.ngtesting.platform.action;

import com.alibaba.fastjson.JSONObject;
import com.ngtesting.platform.config.Constant;
import com.ngtesting.platform.model.TstCaseStep;
import com.ngtesting.platform.model.TstUser;
import com.ngtesting.platform.service.CaseStepService;
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
@RequestMapping(Constant.API_PATH_CLIENT + "case_step/")
public class CaseStepAction extends BaseAction {
    @Autowired
    CaseStepService caseStepService;

    @RequestMapping(value = "up", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> up(HttpServletRequest request, @RequestBody JSONObject json) {
        Map<String, Object> ret = new HashMap<String, Object>();

        TstUser userVo = (TstUser) request.getSession().getAttribute(Constant.HTTP_SESSION_USER_KEY);

        TstCaseStep po = caseStepService.changeOrderPers(json, "up", userVo.getId());
//        TstCaseStepVo stepVo = caseStepService.genVo(po);
//
//        ret.put("data", stepVo);
        ret.put("code", Constant.RespCode.SUCCESS.getCode());
        return ret;
    }

    @RequestMapping(value = "down", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> down(HttpServletRequest request, @RequestBody JSONObject json) {
        Map<String, Object> ret = new HashMap<String, Object>();

        TstUser userVo = (TstUser) request.getSession().getAttribute(Constant.HTTP_SESSION_USER_KEY);

        TstCaseStep po = caseStepService.changeOrderPers(json, "down", userVo.getId());
//        TstCaseStepVo stepVo = caseStepService.genVo(po);
//
//        ret.put("data", stepVo);
        ret.put("code", Constant.RespCode.SUCCESS.getCode());
        return ret;
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(HttpServletRequest request, @RequestBody JSONObject json) {
        Map<String, Object> ret = new HashMap<String, Object>();

        TstUser userVo = (TstUser) request.getSession().getAttribute(Constant.HTTP_SESSION_USER_KEY);

        TstCaseStep po = caseStepService.save(json, userVo.getId());
//        TstCaseStepVo stepVo = caseStepService.genVo(po);
//
//        ret.put("data", stepVo);
        ret.put("code", Constant.RespCode.SUCCESS.getCode());
        return ret;
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(HttpServletRequest request, @RequestBody JSONObject json) {
        Map<String, Object> ret = new HashMap<String, Object>();

        TstUser userVo = (TstUser) request.getSession().getAttribute(Constant.HTTP_SESSION_USER_KEY);

        boolean success = caseStepService.delete(json.getInteger("id"), userVo.getId());

        ret.put("code", Constant.RespCode.SUCCESS.getCode());
        return ret;
    }
}