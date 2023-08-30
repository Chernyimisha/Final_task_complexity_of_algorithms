package complexity_of_algorithms.RedBlackTree;

public class Node {
    //создаем класс, который предусматривает в каждом узле значение (в нашем случае это целые числа),
    //цвет и ссылки на левого и правого ребенка.
    int value;
    Color color;
    Node leftNode;
    Node rightNode;

    @Override
    //прописываем метод описания нашего класса при выводе в консоль
    public  String toString(){
        return "Node{" +
                "Value=" + value +
                ", color=" + color +
                "}";
    }
}
