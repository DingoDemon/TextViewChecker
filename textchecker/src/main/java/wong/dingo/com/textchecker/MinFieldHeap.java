package wong.dingo.com.textchecker;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MinFieldHeap<T extends Field> {

    private ArrayList<Field> list = new ArrayList<>();

    public void insert(T t) {
        int size = list.size();
        list.add(t);
        filterUp(size);
    }


    private void filterUp(int start) {
        int currentHole = start;
        int parent = (start - 1) / 2;
        Field tmp = list.get(currentHole);
        while (currentHole > 0) {
            if (value(list.get(parent)) < value(list.get(currentHole)))
               break;
             else {
                list.set(currentHole, list.get(parent));
                currentHole = parent;
                parent = (parent - 1) / 2;
            }
            list.set(currentHole, tmp);
        }
    }





    public Field poll() {
        if (list.size() == 0)
            return null;
        else {
            int size = list.size();


            Field temp = list.get(0);
            list.set(0, list.get(size - 1));
            list.remove(size - 1);
            if (list.size() > 1)
                filterDown(list.size() - 1);
            return temp;
        }
    }

    private void filterDown(int end) {
        int current = 0;        // 当前(current)节点的位置
        int leftChildIndex = 2 * current + 1;    // 左(left)孩子的位置
        Field tmp = list.get(current);    // 当前(current)节点的大小


        while (leftChildIndex <= end) {
            // "l"是左孩子，"l+1"是右孩子
            if (leftChildIndex < end && value(list.get(leftChildIndex)) > value(list.get(leftChildIndex + 1)))
                leftChildIndex++;        // 左右两孩子中选择较小者，即mHeap[l+1]
            if (value(tmp) <= value(list.get(leftChildIndex)))
                break;        //调整结束
            else {
                list.set(current, list.get(leftChildIndex));
                current = leftChildIndex;
                leftChildIndex = 2 * leftChildIndex + 1;
            }
        }
        list.set(current, tmp);

    }

    @Override
    public String toString() {
        String s = "";
        for (Field field : list) {
            s = s.concat(field.getAnnotation(CheckInfo.class).position() + "\n");
        }
        return s;
    }

    public int size() {
        return list.size();
    }

    public static int value(Field field) {
        return field.getAnnotation(CheckInfo.class).position();
    }
}
