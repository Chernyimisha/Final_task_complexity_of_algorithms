package complexity_of_algorithms.RedBlackTree;


import complexity_of_algorithms.RedBlackTree.Node;

public class RedBlackTree {
    //Создаем класс красно - черного дерева, в котором полем будет рутовый нод и прописываем методы
    //добавления нового элемента и ребалансировки со вспомогательными методами левого и правого поворотов
    //и смены цвета.
    public Node root;

    public boolean add(int value){
        //Метод, изначально запускаемый при создании нового нода.
        //В начале проверяем есть ли root.
        //Если нет, то согласно коду в блоке else создаем новый нод-root, с черным цветом в соответствии с требованиями
        //к структуре красно - черного дерева.
        //Если root есть, то запускается метод addNode относительно узла root.
        if(root != null){
            boolean result = addNode(root, value);
            root = rebalanced(root);//после каждого создания узла и прошедших на уровне простых узлов ребалансировок
                                    //делаем ребалансировку root
            root.color = Color.BLACK; //устанавливаем всегда черный цвет после ребалансировки root-узла
            return result;
        } else {
            root = new Node();
            root.color = Color.BLACK;
            root.value = value;
            return true;
        }
    }

    private boolean addNode(Node node, int value){
        //Перед реализацией основного кода в методе делаем проверку на уникальность создаваемого в дереве value.
        //Если число не уникальное, выводим сообщение.
        if(node.value == value){
            System.out.println("Такое value уже есть, вставка невозможна!");
            return false;
        } else {
            //Проверяем, если value текущего узла больше создаваемого, то "идем" по левой стороне вызывая рекурсивно
            //этот же метод. Как только доходим до leftNode == null создаем узел, присваиваем value, окрашиваем в красный
            //цвет.
            if(node.value > value){
                if(node.leftNode != null){
                    boolean result = addNode(node.leftNode, value);
                    node.leftNode = rebalanced(node.leftNode);
                    return result;
                } else {
                    node.leftNode = new Node();
                    node.leftNode.color = Color.RED;
                    node.leftNode.value = value;
                    return true;
                }
            } else {
                //Если value текущего узла меньше создаваемого, то "идем" по правой стороне.
                //Как только доходим до rightNode == null создаем узел, присваиваем value, окрашиваем в красный
                //цвет.
                if (node.rightNode != null){
                    boolean result = addNode(node.rightNode, value);
                    node.rightNode = rebalanced(node.rightNode);//после вставки нового узла в процессе выхода из
                                                                //рекурсивного вызова проверяем по пути каждый нод
                                                                //на предмет необходимости ребалансировки
                    return result;
                } else {
                    node.rightNode = new Node();
                    node.rightNode.color = Color.RED;
                    node.rightNode.value = value;
                    return true;
                }
            }
        }
    }

    private Node rebalanced(Node node){
        //По сути своей метод ребалансировки содержит цикл do - while с условиями, необходимыми для осуществления левосторонних и
        //правосторонних поворотов, а также для смены цветов. Метод принимает в себя ноду и, если она соответствует указанным
        //в теле "do{}" условиям, то осуществляется замена этой ноды на ту, которую вернет соответствующий метод *Swap).

        Node result = node; //Создаем переменную для ноды

        boolean needRebalanced;//Создаем флаг с типом bool, который на входе в цикл будет иметь значение false. Далее
                               //при выполнении условий для поворотов он будет сменять на true для возобновления цикла, т.к.
                               //для полноценной ребалансировки может понадобиться несколько действий.
        do{
            needRebalanced = false;
            //условие для случая когда красный ребенок справа
            if(result.rightNode != null && result.rightNode.color == Color.RED &&
                    (result.leftNode == null || result.leftNode.color == Color.BLACK)) {
                needRebalanced = true;
                result = rightSwap(result);
                //условие для случая когда два подряд красных ребенка слева
            } if (result.leftNode != null && result.leftNode.color == Color.RED &&
                    result.leftNode.leftNode != null && result.leftNode.leftNode.color == Color.RED) {
                needRebalanced = true;
                result = leftSwap(result);
                //условие для случая когда оба ребенка красные
            } if (result.leftNode != null && result.leftNode.color == Color.RED &&
                    result.rightNode != null && result.rightNode.color == Color.RED){
                needRebalanced = true;
                colorSwap(result);
            }
        }
        while (needRebalanced);
        return result;
    }

    private Node leftSwap(Node node){
        //Описываем метод левостороннего поворота, при котором левый ребенок входящей ноды становится родителем,
        //родитель - правым ребенком, а правый ребенок левого ребенка меняет родителя и становится левым ребенком
        //входящей ноды. Соответственно, меняем цвета.
        Node leftNode = node.leftNode; //создаем переменную leftNode для левого ребенка
        Node betweenNode = leftNode.rightNode;//создаем переменную betweenNode для правого ребенка leftNode
        leftNode.rightNode = node;//перезаписываем правого ребенка у leftNode
        node.leftNode = betweenNode;//перезаписываем левого ребенка у входящей ноды
        leftNode.color = node.color;//меняем цвета, поменявшихся местами узлов на противоположный
        node.color = Color.RED;
        return leftNode;//возвращаем в результате преобразований новую ноду, на место старой
    }

    private Node rightSwap(Node node){
        //Описываем метод правостороннего поворота аналогично методы левостороннего поворота.
        Node rightNode = node.rightNode;
        Node betweenNode = rightNode.leftNode;
        rightNode.leftNode = node;
        node.rightNode = betweenNode;
        rightNode.color = node.color;
        node.color = Color.RED;
        return rightNode;
    }

    private void colorSwap(Node node){
        //Метод смены цвета вызывается в случаях когда у ноды оба ребенка красного цвета. в таком случае их цвета
        //должны быть заменены на черный, а цвет родителя меняется на красный.
        node.rightNode.color = Color.BLACK;//присваиваем черный цвет правому ребенку
        node.leftNode.color = Color.BLACK;//присваиваем черный цвет левому ребенку
        node.color = Color.RED;//присваиваем красный цвет текущему ноду
    }

}
