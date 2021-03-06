package com.ngtesting.platform.service.intf;

import com.ngtesting.platform.model.IsuField;

import java.util.List;
import java.util.Map;

public interface IssueDynamicFormService extends BaseService {
  List<IsuField> listNotUsedField(Integer orgId, Integer projectId, Integer pageId);
  Map<String, Object> fetchOrgField(Integer orgId, Integer projectId);
}
