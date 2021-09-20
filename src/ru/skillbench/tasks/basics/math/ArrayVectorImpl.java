package ru.skillbench.tasks.basics.math;

public class ArrayVectorImpl implements ArrayVector {
    private double[] data;

    public ArrayVectorImpl() {

    }

    @Override
    public void set(double... elements) {
        this.data = new double[elements.length];
        System.arraycopy(elements, 0, this.data, 0, elements.length);
    }

    @Override
    public double[] get() {
        return this.data;
    }

    @Override
    public ArrayVector clone() {
        ArrayVectorImpl cloneThis = new ArrayVectorImpl();
        cloneThis.set(this.data);
        return cloneThis;
    }

    @Override
    public int getSize() {
        return this.data.length;
    }

    @Override
    public void set(int index, double value) {
        if (index < 0) {
            return;
        } else if (index < this.getSize()) {
            this.data[index] = value;
        } else {
            double[] newData = new double[index + 1];
            System.arraycopy(this.data, 0, newData, 0, this.data.length);
            newData[index] = value;
            this.data = newData;
        }
    }

    @Override
    public double get(int index) throws ArrayIndexOutOfBoundsException {
        return this.data[index];
    }

    @Override
    public double getMax() {
        double element = this.data[0];
        for (double item : this.data) {
            if (element < item) {
                element = item;
            }
        }
        return element;
    }

    @Override
    public double getMin() {
        double element = this.data[0];
        for (double item : this.data) {
            if (element > item) {
                element = item;
            }
        }
        return element;
    }

    @Override
    public void sortAscending() {
        java.util.Arrays.sort(this.data);
    }

    @Override
    public void mult(double factor) {
        for (int i = 0; i < this.getSize(); i++) {
            this.data[i] *= factor;
        }
    }

    @Override
    public ArrayVector sum(ArrayVector anotherVector) {
        int size;
        if (this.getSize() < anotherVector.getSize()) {
            size = this.getSize();
        } else {
            size = anotherVector.getSize();
        }
        for (int i = 0; i < size; i++) {
            this.data[i] += anotherVector.get(i);
        }
        return this;
    }

    @Override
    public double scalarMult(ArrayVector anotherVector) {
        double scalar = 0;
        int size;
        if (this.getSize() < anotherVector.getSize()) {
            size = this.getSize();
        } else {
            size = anotherVector.getSize();
        }
        for (int i = 0; i < size; i++) {
            scalar += this.data[i] * anotherVector.get(i);
        }
        return scalar;
    }

    @Override
    public double getNorm() {
        return Math.sqrt(scalarMult(this));
    }
}
