package com.ngtesting.platform.action.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.itfsw.query.builder.support.model.JsonRule;
import com.ngtesting.platform.action.BaseAction;
import com.ngtesting.platform.config.Constant;
import com.ngtesting.platform.model.IsuIssue;
import com.ngtesting.platform.model.IsuQuery;
import com.ngtesting.platform.model.TstUser;
import com.ngtesting.platform.service.intf.*;
import com.ngtesting.platform.vo.IsuJqlColumn;
import com.ngtesting.platform.vo.IsuJqlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(Constant.API_PATH_CLIENT + "issue_tql/")
public class IssueTplAction extends BaseAction {

	@Autowired
    IsuJqlService isuJqlService;
    @Autowired
    IsuJqlFilterService isuJqlFilterService;
    @Autowired
    IsuJqlColumnService isuJqlColumnService;

    @Autowired
    IssueQueryService issueQueryService;
    @Autowired
    UserService userService;
    @Autowired
    IssueDynamicFormService dynamicFormService;

	@RequestMapping(value = "query", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, @RequestBody JSONObject json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		TstUser user = (TstUser) request.getSession().getAttribute(Constant.HTTP_SESSION_USER_PROFILE);
		Integer orgId = user.getDefaultOrgId();
        Integer projectId = user.getDefaultPrjId();

        Integer pageNum = json.getInteger("page");
        Integer pageSize = json.getInteger("pageSize");
        Boolean init = json.getBoolean("init");

        JsonRule rule;
        if (!json.getJSONObject("rule").containsKey("condition")) {
            rule = isuJqlService.buildDefaultJql(orgId, projectId);
        } else {
            rule = json.getObject("rule", JsonRule.class);
        }

        if (StringUtils.isEmpty(user.getIssueColumns())) {
            isuJqlColumnService.buildDefaultColStr(user);
        }

        List<Map<String, String>> orderBy;
        if (json.getJSONArray("orderBy") == null || json.getJSONArray("orderBy").size() == 0) {
            orderBy = isuJqlService.buildDefaultOrderBy();
        } else {
            orderBy = json.getObject("orderBy", List.class);
        }

        com.github.pagehelper.Page page = PageHelper.startPage(pageNum, pageSize);
        List<IsuIssue> data = isuJqlService.query(rule, user.getIssueColumns(), orderBy, orgId, projectId);

        if (init) {
            List<IsuJqlFilter> filters = isuJqlFilterService.buildUiFilters(rule, orgId, projectId);
            List<IsuJqlColumn> columns = isuJqlColumnService.loadColumns(user);
            Map<String, Object> issuePropMap = dynamicFormService.fetchOrgField(orgId, projectId);

            ret.put("rule", rule);
            ret.put("filters", filters);
            ret.put("columns", columns);
            ret.put("orderBy", orderBy);

            ret.put("issuePropMap", issuePropMap);
        }

        ret.put("total", page.getTotal());
        ret.put("data", data);
		ret.put("code", Constant.RespCode.SUCCESS.getCode());
		return ret;
	}

    @RequestMapping(value = "queryById", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryById(HttpServletRequest request, @RequestBody JSONObject json) {
        Map<String, Object> ret = new HashMap<String, Object>();
        TstUser user = (TstUser) request.getSession().getAttribute(Constant.HTTP_SESSION_USER_PROFILE);
        Integer orgId = user.getDefaultOrgId();
        Integer projectId = user.getDefaultPrjId();

        Integer pageNum = json.getInteger("page");
        Integer pageSize = json.getInteger("pageSize");

        Boolean init = json.getBoolean("init");
        Integer queryId = json.getInteger("queryId");

        IsuQuery query = issueQueryService.get(queryId, user.getId());
        String ruleString = query == null? null: query.getRule();
        String orderByString = query == null? null: query.getOrderBy();

        JsonRule rule;
        if (ruleString == null) {
            rule = isuJqlService.buildDefaultJql(orgId, projectId);
        } else {
            rule = JSONObject.parseObject(ruleString, JsonRule.class);
        }

        List<Map<String, String>> orderBy;
        if (orderByString == null) {
            orderBy = null;
        } else {
            orderBy = JSON.parseObject(orderByString, List.class);
        }

        if (StringUtils.isEmpty(user.getIssueColumns())) {
            isuJqlColumnService.buildDefaultColStr(user);
        }

        com.github.pagehelper.Page page = PageHelper.startPage(pageNum, pageSize);
        List<IsuIssue> data = isuJqlService.query(rule, user.getIssueColumns(), orderBy, orgId, projectId);

        ret.put("rule", rule);

        if (init) {
            List<IsuJqlFilter> filters = isuJqlFilterService.buildUiFilters(rule, orgId, projectId);
            List<IsuJqlColumn> columns = isuJqlColumnService.loadColumns(user);
            Map<String, Object> issuePropMap = dynamicFormService.fetchOrgField(orgId, projectId);

            ret.put("filters", filters);
            ret.put("columns", columns);
            ret.put("issuePropMap", issuePropMap);
        }

        issueQueryService.updateUseTime(query, user);

        ret.put("total", page.getTotal());
        ret.put("data", data);
        ret.put("code", Constant.RespCode.SUCCESS.getCode());
        return ret;
    }

    @RequestMapping(value = "changeColumns", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> changeColumns(HttpServletRequest request, @RequestBody JSONObject json) {
        Map<String, Object> ret = new HashMap<String, Object>();
        TstUser user = (TstUser) request.getSession().getAttribute(Constant.HTTP_SESSION_USER_PROFILE);

        String columnsStr = json.getString("columns");

        userService.saveIssueColumns(columnsStr, user);

        ret.put("code", Constant.RespCode.SUCCESS.getCode());
        return ret;
    }

}
