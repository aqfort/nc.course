package ru.skillbench.tasks.basics.control;

public class ControlFlowStatements1Impl implements ControlFlowStatements1 {
    public ControlFlowStatements1Impl() {

    }

    @Override
    public float getFunctionValue(float x) {
        if (x > 0) {
            return (float) (2 * Math.sin(x));
        } else {
            return 6 - x;
        }
    }

    @Override
    public String decodeWeekday(int weekday) {
        return switch (weekday) {
            case 1 -> "Monday";
            case 2 -> "Tuesday";
            case 3 -> "Wednesday";
            case 4 -> "Thursday";
            case 5 -> "Friday";
            case 6 -> "Saturday";
            case 7 -> "Sunday";
            default -> "Error";
        };
    }

    @Override
    public int[][] initArray() {
        int[][] array = new int[8][5];

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = i * j;
            }
        }

        return array;
    }

    @Override
    public int getMinValue(int[][] array) {
        int element = array[0][0];

        for (int[] line : array) {
            for (int item : line) {
                if (item < element) {
                    element = item;
                }
            }
        }

        return element;
    }

    @Override
    public BankDeposit calculateBankDeposit(double P) {
        BankDeposit object = new BankDeposit();

        object.amount = 1000;
        object.years = 0;

        while (object.amount <= 5000) {
            object.amount *= ((double) 1 + P * 0.01);
            object.years++;
        }

        return object;
    }
}
