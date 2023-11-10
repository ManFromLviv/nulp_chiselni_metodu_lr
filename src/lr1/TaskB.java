package lr1;

import java.text.DecimalFormat; // Для створення відповідного формату.

/**
 *
 * @author Valchevskyi
 */
public class TaskB {
    private boolean correctData; // Змінна, яка показує коректність обчислень.
    private int variant; // Змінна мого варіанту.
    private double valueFx; // Значення функції.
    private double deltaFx; // Абсолютна похибка функції.
    private double dFx; // Відносна похибка функції.
    private double x1, x2, x3; // Значення змінних.
    private int x1m, x2m, x3m; // Значення першої значущої цифри змінних.
    private int x1mPower, x2mPower, x3mPower; // Значення степені першої значущої цифри.
    private int x1n, x2n, x3n; // Кількість значущих цифр змінних.
    private double x1d, x2d, x3d; // Відносні похибки змінних
    private double x1delta, x2delta, x3delta; // Абсолютні похибки змінних.
    
    public TaskB(double x1, double x2, double x3) {
        try {
            variant = 3;
            setX(x1, x2, x3);
            setDelta();
            setMpower();
            setN();
            setM();
            setD();
            setFx();
            setDeltaFx();
            setDFx();
            correctData = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            correctData = false;
        }
    }
    
    // Повернення стану коректності даних.
    public boolean getCorrectData() {
        return correctData;
    }
    
    // Встановлення змінних.
    public void setX(double x1, double x2, double x3) throws Exception {
        if (x1 != 0 && x2 != 0 && x3 != 0) {
            this.x1 = x1;
            this.x2 = x2;
            this.x3 = x3;
        } else {
            throw new Exception("\tПомилка: Змінна не може бути нулем!");
        }
    }
    
    // Встановлення кількості значущих цифр, якщо одна змінна має 0 значущих цифр - виключення.
    public void setN() throws Exception {
        x1n = getN(x1mPower, x1delta);
        x2n = getN(x2mPower, x2delta);
        x3n = getN(x3mPower, x3delta);
        if (!(x1n > 0 && x2n > 0 && x3n > 0)) {
            throw new Exception("\tПомилка: Значущі цифри мають бути більші за нуль!");
        }
    }
    
    // Отримання кількості значущих цифр.
    private int getN(double xM, double xDelta) {
        return (int) Math.abs(xM + 1 - Math.log10(2 * xDelta));
    }
    
    // Встановлення першої значущої цифри, якщо одна змінна має 0 значущих цифр - виключення.
    public void setM() throws Exception {
        x1m = getXm(x1);
        x2m = getXm(x2);
        x3m = getXm(x3);
        if (!(x1m > 0 && x2m > 0 && x3m > 0)) {
            throw new Exception("\tПомилка: Перша значуща цифра має бути більша за нуль!");
        }

    }
    
    // Отримання першої значущої цифри з числа, якщо немає взаглі значущих цифр - повернення нуля.
    private int getXm(double x) {
        String xStr = String.valueOf(x);
        for (int i = 0; i < xStr.length(); i++) {
            int value = Character.getNumericValue(xStr.charAt(i));
            if (value != 0 && value != -1) { // Значення -1 - кома або знак мінус, 0 - 0.
                return value;
            }
        }
        return 0;
    }
    
    // Встановлення першої степені першої значущої цифри числа
    public void setMpower() throws Exception {
        x1mPower = getXmPower(x1);
        x2mPower = getXmPower(x2);
        x3mPower = getXmPower(x3);
    }
    
    // Отримання степені першої значущої цифри числа
    private int getXmPower(double x) {
        DecimalFormat decimalFormat = new DecimalFormat("#.################"); // Для зміни формату числа пре перетворенні в рядок.
        String xStr = decimalFormat.format(x);
        int indexMinusInStr = xStr.indexOf('-');
        int indexCommaInText = xStr.indexOf(',');
        int index = -1;
        
        if (indexCommaInText != -1) {
            String positivePowerInNumber = xStr.substring(indexMinusInStr + 1, indexCommaInText);
            String negativePowerInNumber = xStr.substring(indexCommaInText + 1, xStr.length());

            boolean searchingIndex = true;
            for (int i = 0, j = positivePowerInNumber.length() - 1; i < positivePowerInNumber.length(); i++, j--) { // Прохід позитивного степеня числа.
                int value = Character.getNumericValue(positivePowerInNumber.charAt(i));
                if (value != 0) {
                    index = j;
                    searchingIndex = false;
                    break;
                }
            }
            if (searchingIndex) { // Прохід від'ємного степеня числа.
                for (int i = 0, j = -1; i < negativePowerInNumber.length(); i++, j--) {
                    int value = Character.getNumericValue(negativePowerInNumber.charAt(i));
                    if (value != 0) {
                        index = j;
                        break;
                    }
                }
            }
        } else {
            String positivePowerInNumber = xStr.substring(indexMinusInStr + 1, xStr.length());

            for (int i = 0, j = positivePowerInNumber.length() - 1; i < positivePowerInNumber.length(); i++, j--) { // Прохід позитивного степеня числа.
                int value = Character.getNumericValue(positivePowerInNumber.charAt(i));
                if (value != 0) {
                    index = j;
                    break;
                }
            }
        }
        
        return index;
    } 
    
    // Встановлення відносних похибок змінних.
    public void setD() {
        x1d = getXd(x1n, x1m);
        x2d = getXd(x2n, x2m);
        x3d = getXd(x3n, x3m);
    }
    
    // Розрахунок відносної похибки змінної.
    private double getXd(int n, int m) {
        double value = 1.0 / m * Math.pow(1.0 / 10, n - 1);
        return (n >= 2) ? 0.5 * value : value;
    }
    
    // Встановлення абсолютних похибок.
    public void setDelta() {
        x1delta = getXdelta();
        x2delta = getXdelta();
        x3delta = getXdelta();

    }
    
    // Розрахунок абсолютних похибок зміної.
    private double getXdelta() {
        return variant * Math.pow(10, -3);
    }
    
    // Встановлення значення функції.
    public void setFx() {
        valueFx = 3 * Math.pow(x1, 2) + 2 * Math.pow(x2, 2) + 4 * Math.pow(x3, 2) + 3 * x1 * x2 - 2 * x2 + Math.sin(x1 - x3 * x2);
    }
    
    // Встановлення абсолютної похибки функції.
    public void setDeltaFx() {
        double partX1 = 6 * x1 + 3 * x2 + Math.cos(x1 - x2 * x3);
        double partX2 = 4 * x2 + 3 * x1 - 2 + Math.cos(x1 - x2 * x3) * -x3;
        double partX3 = 8 * x3 + Math.cos(x1 - x2 * x3) * -x2;
        deltaFx = Math.abs(partX1) * x1delta + Math.abs(partX2) * x2delta + Math.abs(partX3) * x3delta;
    }
    
    // Встановлення відносної похибки фукнції.
    public void setDFx() throws Exception {
        if (valueFx == 0) {
            throw new Exception("Ділення на нуль. Ви ввели такі значення за яких функція приймає значення 0!");
        }
        dFx = Math.abs(deltaFx / valueFx);
    }
    
    // Вивід усіх результатів.
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Результати пункту Б (група ОІ-11 сп; Вальчевський Павло Володимирович; ЛР № 1; варіант № 3):\n");
        int countNumberInPercent = 2;
        int countNumberInExpression = 5;
        if (correctData) {
            stringBuilder.append("\tx1: ").append(x1).append("\n");
            stringBuilder.append("\tx2: ").append(x2).append("\n");
            stringBuilder.append("\tx3: ").append(x3).append("\n");
            stringBuilder.append("\tx1 кількість значущих цифр (йшли розрахунки): ").append(x1n).append("\n");
            stringBuilder.append("\tx2 кількість значущих цифр (йшли розрахунки): ").append(x2n).append("\n");
            stringBuilder.append("\tx3 кількість значущих цифр (йшли розрахунки): ").append(x3n).append("\n");
            stringBuilder.append("\tx1 перша значуща цифра: ").append(x1m).append("\n");
            stringBuilder.append("\tx2 перша значуща цифра: ").append(x2m).append("\n");
            stringBuilder.append("\tx3 перша значуща цифра: ").append(x3m).append("\n");
            stringBuilder.append("\tx1 степінь першої значущої: ").append(x1mPower).append("\n");
            stringBuilder.append("\tx2 степінь першої значущої: ").append(x2mPower).append("\n");
            stringBuilder.append("\tx3 степінь першої значущої: ").append(x3mPower).append("\n");
            stringBuilder.append("\tx1 відносна похибка у %: ").append(getPercent(x1d, countNumberInPercent)).append("\n");
            stringBuilder.append("\tx2 відносна похибка у %: ").append(getPercent(x2d, countNumberInPercent)).append("\n");
            stringBuilder.append("\tx3 відносна похибка у %: ").append(getPercent(x3d, countNumberInPercent)).append("\n");
            stringBuilder.append("\tx1 абсолютна похибка (задане через варіант): ").append(getRound(x1delta, countNumberInExpression)).append("\n");
            stringBuilder.append("\tx2 абсолютна похибка (задане через варіант): ").append(getRound(x2delta, countNumberInExpression)).append("\n");
            stringBuilder.append("\tx3 абсолютна похибка (задане через варіант): ").append(getRound(x3delta, countNumberInExpression)).append("\n");
            stringBuilder.append("\tЗначення функції: ").append(getRound(valueFx, countNumberInExpression)).append("\n");
            stringBuilder.append("\tАбсолютна похибка функції: ").append(getRound(deltaFx, countNumberInExpression)).append("\n");
            stringBuilder.append("\tВідносна похибка функції у %: ").append(getPercent(dFx, countNumberInPercent)).append("\n");
        } else {
            stringBuilder.append("\tЧерез погано введені дані, обчислення неможливе!");
        }
        return stringBuilder.toString();
    }
    
    // Повернення дробового числа у відсотковому форматі
    private double getPercent(double value, int f) {
        return Math.round(value * Math.pow(10, f));
    }
    
    // Повернення округленого числа до f-знаків після коми
    private double getRound(double value, int f) {
        return Math.round(value * Math.pow(10, f)) / Math.pow(10, f);
    }
}
