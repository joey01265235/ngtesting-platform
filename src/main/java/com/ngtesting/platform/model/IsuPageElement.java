package com.ngtesting.platform.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class IsuPageElement extends BaseModel {
    private static final long serialVersionUID = 8715160448820762150L;

    private String code;
    private String label;
    private String type;
    private String input;
    private Boolean fullLine;
    private Boolean required;
    private Boolean readonly;

    private String key;
    private List<Map> options = new LinkedList<>();

    private Integer ordr;

    private Integer fieldId;
    private Integer pageId;

    private Integer orgId;

    public IsuPageElement(){}
    public IsuPageElement(String code, String label, String type,
                          String input, Boolean fullLine, Boolean required, Boolean readonly,
                          String key, Integer fieldId, Integer pageId, Integer orgId,
                          Integer ordr) {
        this.code = code;
        this.label = label;
        this.type = type;
        this.input = input;
        this.fullLine = fullLine;
        this.required = required;
        this.readonly = readonly;
        this.key = key;
        this.fieldId = fieldId;
        this.pageId = pageId;
        this.orgId = orgId;
        this.ordr = ordr;
    }

    public Boolean getReadonly() {
        return readonly;
    }

    public void setReadonly(Boolean readonly) {
        this.readonly = readonly;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public Integer getOrdr() {
        return ordr;
    }

    public void setOrdr(Integer ordr) {
        this.ordr = ordr;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getFullLine() {
        return fullLine;
    }

    public void setFullLine(Boolean fullLine) {
        this.fullLine = fullLine;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public List<Map> getOptions() {
        return options;
    }

    public void setOptions(List<Map> options) {
        this.options = options;
    }
}
