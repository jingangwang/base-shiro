package com.wjg.base.shiro.vo;

/**
 * Created by wjg on 2017/6/2.
 */
public class JsTreeDataVO {
    private String id;
    private String parent;
    private String text;
    private String icon;
    private boolean children;

    public JsTreeDataVO() {
    }

    public JsTreeDataVO(String id, String parent, String text, String icon, boolean children) {
        this.id = id;
        this.parent = parent;
        this.text = text;
        this.icon = icon;
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isChildren() {
        return children;
    }

    public void setChildren(boolean children) {
        this.children = children;
    }
}
