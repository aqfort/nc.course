package ru.skillbench.tasks.basics.entity;

import java.awt.image.ReplicateScaleFilter;

public class EmployeeImpl implements Employee {
    private String firstName;
    private String lastName;
    private Employee manager = null;
    private int salary = 1000;

    public EmployeeImpl() {

    }

    @Override
    public int getSalary() {
        return this.salary;
    }

    @Override
    public void increaseSalary(int value) {
        this.salary += value;
    }

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getFullName() {
        return this.getFirstName() + ' ' + this.getLastName();
    }

    @Override
    public void setManager(Employee manager) {
        this.manager = manager;
    }

    @Override
    public String getManagerName() {
        if (this.manager == null) {
            return "No manager";
        } else {
            return this.manager.getFullName();
        }
    }

    @Override
    public Employee getTopManager() {
        if (this.manager == null) {
            return this;
        } else {
            return this.manager.getTopManager();
        }
    }
}
