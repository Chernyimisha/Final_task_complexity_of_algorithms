package complexity_of_algorithms.RedBlackTree;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //В майне реализована возможность вставки новых значений в наше дерево.
        //Сам процесс можно отследить с помощью дебагера, установив флажок на стр.19

        Scanner iScannerInt = new Scanner(System.in);
        final RedBlackTree tree = new RedBlackTree();
        int value;
        System.out.println("Поочередно вводите целые числа: ");

        while (true){
            value = iScannerInt.nextInt();
            tree.add(value);
            System.out.println("finish");
        }
    }
}
