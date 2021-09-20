package ru.skillbench.tasks.javaapi.collections;

import java.util.*;

public class TreeNodeImpl implements TreeNode {
    private TreeNode parent = null;
    private List<TreeNode> children = null;
    private boolean expanded = false;
    private Object data = null;

    public TreeNodeImpl() {

    }

    @Override
    public TreeNode getParent() {
        return this.parent;
    }

    @Override
    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    @Override
    public TreeNode getRoot() {
        if (this.getParent() == null && this.isLeaf()) {
            return null;
        } else if (this.getParent() == null) {
            return this;
        } else {
            return this.getParent().getRoot();
        }
    }

    @Override
    public boolean isLeaf() {
        return this.getChildCount() == 0;
    }

    @Override
    public int getChildCount() {
        if (this.children == null) {
            return 0;
        } else {
            return this.children.size();
        }
    }

    @Override
    public Iterator<TreeNode> getChildrenIterator() {
        return this.children.iterator();
    }

    @Override
    public void addChild(TreeNode child) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        child.setParent(this);
        this.children.add(child);
    }

    @Override
    public boolean removeChild(TreeNode child) {
        if (this.children == null) {
            return false;
        } else if (!this.children.contains(child)) {
            return false;
        } else {
            this.children.remove(child);
            child.setParent(null);
            return true;
        }
    }

    @Override
    public boolean isExpanded() {
        return this.expanded;
    }

    @Override
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        if (!this.isLeaf()) {
            Iterator<TreeNode> iterator = this.getChildrenIterator();
            while (iterator.hasNext()) {
                iterator.next().setExpanded(expanded);
            }
        }
    }

    @Override
    public Object getData() {
        return this.data;
    }

    @Override
    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String getTreePath() {
        TreeNode treeNode = this;
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(this.getData().toString());

        while (treeNode.getParent() != null) {
            treeNode = treeNode.getParent();
            arrayList.add("->");
            if (treeNode.getData() != null) {
                arrayList.add(treeNode.getData().toString());
            } else {
                arrayList.add("empty");
            }
        }

        Collections.reverse(arrayList);

        StringBuilder stringBuilder = new StringBuilder();

        for (String item : arrayList) {
            stringBuilder.append(item);
        }

        return stringBuilder.toString();
    }

    @Override
    public TreeNode findParent(Object data) {
        if (this.getParent() != null) {
            if ((this.getData() != null && this.getData().equals(data)) || (this.getData() == null && this.getData() == data)) {
                return this;
            } else {
                return this.getParent().findParent(data);
            }
        } else {
            return null;
        }
    }

    @Override
    public TreeNode findChild(Object data) {
        if (this.children != null) {
            Iterator<TreeNode> iterator = this.getChildrenIterator();
            while (iterator.hasNext()) {
                TreeNode nextNode = iterator.next();
                if ((nextNode.getData() != null && nextNode.getData().equals(data)) || (nextNode.getData() == null && nextNode.getData() == data)) {
                    return nextNode;
                } else if (!nextNode.isLeaf()) {
                    return nextNode.findChild(data);
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        TreeNodeImpl a = new TreeNodeImpl();
        TreeNodeImpl b = new TreeNodeImpl();
        TreeNodeImpl c = new TreeNodeImpl();
        TreeNodeImpl d = new TreeNodeImpl();
        TreeNodeImpl e = new TreeNodeImpl();
        TreeNodeImpl f = new TreeNodeImpl();
        TreeNodeImpl g = new TreeNodeImpl();

        a.setData("a");
//        b.setData("b");
        c.setData("c");
        d.setData("d");
        e.setData("e");
        f.setData("f");
        g.setData("g");

        a.addChild(b);

        b.addChild(c);
        b.addChild(d);

        c.addChild(e);

        d.addChild(f);

        f.addChild(g);

        System.out.println(f.getTreePath());
    }
}
