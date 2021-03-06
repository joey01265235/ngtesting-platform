package com.ngtesting.platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.itfsw.query.builder.support.model.JsonRule;
import com.ngtesting.platform.dao.IssueQueryDao;
import com.ngtesting.platform.model.IsuQuery;
import com.ngtesting.platform.model.TstUser;
import com.ngtesting.platform.service.intf.IssueQueryService;
import com.ngtesting.platform.service.intf.PushSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueQueryServiceImpl extends BaseServiceImpl implements IssueQueryService {
    @Autowired
    PushSettingsService pushSettingsService;

	@Autowired
	IssueQueryDao queryDao;

	@Override
	public List<IsuQuery> list(Integer orgId, Integer userId, String keywords) {
        return queryDao.list(orgId, userId, keywords);
	}

    @Override
    public List<IsuQuery> listRecentQuery(Integer orgId, Integer userId) {
        return queryDao.listRecentQuery(orgId, userId);
    }

    @Override
    public IsuQuery get(Integer queryId, Integer userId) {
        return queryDao.get(queryId, userId);
    }

    @Override
	public IsuQuery save(String queryName, JsonRule rule, TstUser user) {
		IsuQuery query = new IsuQuery();
        query.setName(queryName);
        query.setRule(JSON.toJSONString(rule));

		query.setProjectId(user.getDefaultPrjId());
		query.setOrgId(user.getDefaultOrgId());
		query.setUserId(user.getId());

		queryDao.save(query);

        pushSettingsService.pushRecentQueries(user);
		return query;
	}

    @Override
    public Integer update(IsuQuery vo, TstUser user) {
	    Integer count = queryDao.update(vo, user.getId());
        pushSettingsService.pushRecentQueries(user);

        return count;
    }

    @Override
	public Integer delete(Integer id, TstUser user) {
        Integer count = queryDao.delete(id, user.getId());
        pushSettingsService.pushRecentQueries(user);

        return count;
	}

    @Override
    public Integer updateUseTime(IsuQuery query, TstUser user) {
        Integer count = queryDao.updateUseTime(query);
        pushSettingsService.pushRecentQueries(user);

        return count;
    }

}
