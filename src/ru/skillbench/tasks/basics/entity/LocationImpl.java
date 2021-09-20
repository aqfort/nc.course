package ru.skillbench.tasks.basics.entity;

public class LocationImpl implements Location {
    private String name;
    private Type type;
    private Location parent;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public void setParent(Location parent) {
        this.parent = parent;
    }

    @Override
    public String getParentName() {
        if (this.parent == null) {
            return "--";
        } else {
            return this.parent.getName();
        }
    }

    @Override
    public Location getTopLocation() {
        if (this.parent == null) {
            return this;
        } else {
            return this.parent.getTopLocation();
        }
    }

    @Override
    public boolean isCorrect() {
        if (this.parent == null) {
            return true;
        } else {
            if (this.parent.getType().compareTo(this.getType()) < 0) {
                return this.parent.isCorrect();
            } else {
                return false;
            }
        }
    }

    @Override
    public String getAddress() {
        if (this.parent == null) {
            return this.getName();
        } else {
            int countMatches = this.getName().length() - this.getName().replace(" ", "").length();
            if (this.getName().indexOf('.') == -1 | countMatches > 1) {
                return this.getType().getNameForAddress() + this.getName() + ", " + this.parent.getAddress();
            } else {
                return this.getName() + ", " + this.parent.getAddress();
            }
        }
    }

    @Override
    public String toString() {
        return this.getName() + " (" + this.getType() + ")";
    }
}
