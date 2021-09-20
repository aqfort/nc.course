package ru.skillbench.tasks.basics.math;

public class ComplexNumberImpl implements ComplexNumber {
    private double re;
    private double im;

    public ComplexNumberImpl() {

    }

    public ComplexNumberImpl(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public ComplexNumberImpl(String value) {
        this.set(value);
    }

    @Override
    public double getRe() {
        return this.re;
    }

    @Override
    public double getIm() {
        return this.im;
    }

    @Override
    public boolean isReal() {
        if (im == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void set(double re, double im) {
        this.re = re;
        this.im = im;
    }

    @Override
    public void set(String value) throws NumberFormatException {
        String tmpValue = value.replace(" ", "");
        int iIndex = tmpValue.indexOf("i");
        if (iIndex != -1) {
            if (iIndex < tmpValue.length() - 1) {
                throw new NumberFormatException();
            }
            int index;
            if (tmpValue.length() < 3) {
                index = 0;
            } else {
                index = Math.max(Math.max(tmpValue.indexOf("+", 1), tmpValue.indexOf("-", 1)), 0);
            }
            String suff = "";
            this.re = 0;
            if (index != 0) {
                this.re = Double.parseDouble(tmpValue.substring(0, index));
            }
            if (index + 1 >= iIndex) {
                suff = "1";
            }
            this.im = Double.parseDouble(tmpValue.substring(index, iIndex) + suff);
        } else {
            this.im = 0;
            this.re = Double.parseDouble(tmpValue);
        }
    }

    @Override
    public ComplexNumber copy() {
        return new ComplexNumberImpl(this.re, this.im);
    }

    @Override
    public ComplexNumber clone() throws CloneNotSupportedException {
        return (ComplexNumber) super.clone();
    }

    @Override
    public String toString() {
        if (this.re == 0 && this.im == 0) {
            return "0+0i";
        } else if (this.im == 0) {
            return Double.toString(this.re);
        } else if (this.re == 0) {
            return Double.toString(this.im) + 'i';
        } else {
            if (this.im < 0) {
                return Double.toString(this.re) + '-' + Double.toString(this.im) + 'i';
            }
            return Double.toString(this.re) + '+' + Double.toString(this.im) + 'i';
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ComplexNumber) {
            return (this.re == ((ComplexNumber) other).getRe() & this.im == ((ComplexNumber) other).getIm());
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(ComplexNumber other) {
        double squared1 = this.getRe() * this.getRe() + this.getIm() * this.getIm();
        double squared2 = other.getRe() * other.getRe() + other.getIm() * other.getIm();
        return Double.compare(squared1, squared2);
    }

    @Override
    public void sort(ComplexNumber[] array) {
        java.util.Arrays.sort(array, ComplexNumber::compareTo);
    }

    @Override
    public ComplexNumber negate() {
        this.re *= -1;
        this.im *= -1;
        return this;
    }

    @Override
    public ComplexNumber add(ComplexNumber arg2) {
        this.re += arg2.getRe();
        this.im += arg2.getIm();
        return this;
    }

    @Override
    public ComplexNumber multiply(ComplexNumber arg2) {
        double oldRe = this.re;
        double oldIm = this.im;
        this.re = oldRe * arg2.getRe() - oldIm * arg2.getIm();
        this.im = oldIm * arg2.getRe() + oldRe * arg2.getIm();
        return this;
    }
}
