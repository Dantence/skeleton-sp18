/**
 * @author Dantence
 * @description:
 * @date 2023/1/27
 */

/**
 * @projectName: proj1a
 * @package: PACKAGE_NAME
 * @className: ArrayDequeTest
 * @author: Dantence
 * @description: TODO
 * @date: 2023/1/27 16:15
 * @version: 1.0
 */
public class ArrayDequeTest {

    public static void addPrintTest(){
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
        arrayDeque.addLast(10);
        arrayDeque.addLast(20);
        arrayDeque.addLast(30);
        arrayDeque.addLast(40);
        arrayDeque.addFirst(50);
        arrayDeque.removeFirst();
        arrayDeque.printDeque();
        System.out.println("size: " + arrayDeque.size());
        System.out.println("index 1: " + arrayDeque.get(1));
        System.out.println("index 0: " + arrayDeque.get(0));
    }

    public static void resizeTest(){
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
        for(int i = 1; i<= 28;i++){
            arrayDeque.addFirst(i);
        }
        for(int i = 1; i<= 28;i++){
            arrayDeque.addLast(i);
        }
        for(int i = 1; i <= 52;i++){
            arrayDeque.removeFirst();
        }

        arrayDeque.printDeque();
        System.out.println("size: " + arrayDeque.size());
        System.out.println("index 1: " + arrayDeque.get(1));
    }
    public static void main(String[] args) {
        System.out.println("Running tests.\n");
        //addPrintTest();
        resizeTest();
    }
}
