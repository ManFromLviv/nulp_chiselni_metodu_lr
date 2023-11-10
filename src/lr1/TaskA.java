package lr1;

/**
 *
 * @author Valchevskyi
 */
public final class TaskA {
    private boolean correctData; // Змінна, яка показує коректність обчислень.
    private double valueFx; // Значення функції.
    private double deltaFx; // Абсолютна похибка функції.
    private double dFx; // Відносна похибка функції.
    private double x1, x2, x3; // Значення змінних.
    private int x1m, x2m, x3m; // Значення першої значущої цифри змінних.
    private int x1n, x2n, x3n; // Кількість значущих цифр змінних.
    private double x1d, x2d, x3d; // Відносні похибки змінних
    private double x1delta, x2delta, x3delta; // Абсолютні похибки змінних.
    
    public TaskA(double x1, double x2, double x3, int x1n, int x2n, int x3n) {
        try {
            setX(x1, x2, x3);
            setN(x1n, x2n, x3n);
            setM();
            setD();
            setDelta();
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
    public void setN(int x1n, int x2n, int x3n) throws Exception {
        if (x1n > 0 && x2n > 0 && x3n > 0) {
            this.x1n = x1n;
            this.x2n = x2n;
            this.x3n = x3n;
        } else {
            throw new Exception("\tПомилка: Значущі цифри мають бути більші за нуль!");
        }
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
        x1delta = getXdelta(x1d, x1);
        x2delta = getXdelta(x2d, x2);
        x3delta = getXdelta(x3d, x3);

    }
    
    // Розрахунок абсолютних похибок зміної.
    private double getXdelta(double d, double x) {
        return Math.abs(d * x);
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
        StringBuilder stringBuilder = new StringBuilder("Результати пункту А (група ОІ-11 сп; Вальчевський Павло Володимирович; ЛР № 1; варіант № 3):\n");
        int countNumberInPercent = 2;
        int countNumberInExpression = 5;
        if (correctData) {
            stringBuilder.append("\tx1: ").append(x1).append("\n");
            stringBuilder.append("\tx2: ").append(x2).append("\n");
            stringBuilder.append("\tx3: ").append(x3).append("\n");
            stringBuilder.append("\tx1 кількість значущих цифр: ").append(x1n).append("\n");
            stringBuilder.append("\tx2 кількість значущих цифр: ").append(x2n).append("\n");
            stringBuilder.append("\tx3 кількість значущих цифр: ").append(x3n).append("\n");
            stringBuilder.append("\tx1 перша значуща цифра: ").append(x1m).append("\n");
            stringBuilder.append("\tx2 перша значуща цифра: ").append(x2m).append("\n");
            stringBuilder.append("\tx3 перша значуща цифра: ").append(x3m).append("\n");
            stringBuilder.append("\tx1 відносна похибка у %: ").append(getPercent(x1d, countNumberInPercent)).append("\n");
            stringBuilder.append("\tx2 відносна похибка у %: ").append(getPercent(x2d, countNumberInPercent)).append("\n");
            stringBuilder.append("\tx3 відносна похибка у %: ").append(getPercent(x3d, countNumberInPercent)).append("\n");
            stringBuilder.append("\tx1 абсолютна похибка: ").append(getRound(x1delta, countNumberInExpression)).append("\n");
            stringBuilder.append("\tx2 абсолютна похибка: ").append(getRound(x2delta, countNumberInExpression)).append("\n");
            stringBuilder.append("\tx3 абсолютна похибка: ").append(getRound(x3delta, countNumberInExpression)).append("\n");
            stringBuilder.append("\tЗначення функції: ").append(getRound(valueFx, countNumberInExpression)).append("\n");
            stringBuilder.append("\tАбсолютна похибка функції: ").append(getRound(deltaFx, countNumberInExpression)).append("\n");
            stringBuilder.append("\tВідносна похибка функції у %: ").append(getPercent(dFx, 2)).append("\n");
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
