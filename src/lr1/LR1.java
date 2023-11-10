package lr1;

import java.util.Scanner;

/**
 *
 * @author Valchevskyi
 */
public class LR1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { // Виконання програми через головну функцію проекту.
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                double x1 = inputDouble("x1 (дробове число)", scanner);
                double x2 = inputDouble("x2 (дробове число)", scanner);
                double x3 = inputDouble("x3 (дробове число)", scanner);
                int x1n = inputInt("x1 к-сть значущих цифр (ціле число)", scanner);
                int x2n = inputInt("x2 к-сть значущих цифр (ціле число)", scanner);
                int x3n = inputInt("x3 к-сть значущих цифр (ціле число)", scanner);
                TaskA taskA = new TaskA(x1, x2, x3, x1n, x2n, x3n);
                System.out.println(taskA.toString());
                TaskB taskB = new TaskB(x1, x2, x3);
                System.out.println(taskB.toString());
                if (taskA.getCorrectData() && taskB.getCorrectData()) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("\tПомилка: Уведіть числа згідно повідомлень!");
            }
        }
    }
    
    public static double inputDouble(String nameVar, Scanner scanner) {
        System.out.print(nameVar + ": ");
        return scanner.nextDouble();
    }
    
    public static int inputInt(String nameVar, Scanner scanner) {
        System.out.print(nameVar + ": ");
        return scanner.nextInt();
    }
}
