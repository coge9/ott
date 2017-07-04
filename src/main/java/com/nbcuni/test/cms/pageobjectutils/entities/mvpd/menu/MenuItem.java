package com.nbcuni.test.cms.pageobjectutils.entities.mvpd.menu;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {

    String name;
    MenuItem parent;
    List<MenuItem> children;

    public MenuItem(MpvdMenuName mvpdMenu) {
        this.name = mvpdMenu.get();
        children = new ArrayList<MenuItem>();
    }

    public String getName() {
        return name;
    }

    public MenuItem getParent() {
        return parent;
    }

    public void setParent(MenuItem parent) {
        this.parent = parent;
    }

    public void addChild(MenuItem item) {
        item.setParent(this);
        children.add(item);
    }

    public List<MenuItem> getChilds() {
        return children;
    }

    public List<String> getChildNames() {
        List<String> list = new ArrayList<String>();
        for (MenuItem child : children) {
            list.add(child.getName());
        }
        return list;
    }

    public int getNumberOfChildElements() {
        return children.size();
    }

    public String[] getHierarchyNames() {
        List<String> list = new ArrayList<String>();
        list.add(this.getName());
        MenuItem tempParent = this.getParent();
        while (tempParent != null) {
            list.add(tempParent.getName());
            tempParent = tempParent.getParent();
        }
        String[] names = new String[list.size()];
        for (int i = list.size() - 1; i >= 0; i--) {
            names[names.length - 1 - i] = list.get(i);
        }
        return names;
    }

    @Override
    public String toString() {
        return "MenuItem [name=" + name + "]";
    }

}
