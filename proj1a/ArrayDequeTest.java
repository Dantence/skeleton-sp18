///**
// * @author Dantence
// * @description:
// * @date 2023/1/27
// */
//
//import java.util.Random;
//
///**
// * @projectName: proj1a
// * @package: PACKAGE_NAME
// * @className: ArrayDequeTest
// * @author: Dantence
// * @description: TODO
// * @date: 2023/1/27 16:15
// * @version: 1.0
// */
//public class ArrayDequeTest {
//    public static void addRemoveTest(){
//        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
//        arrayDeque.addFirst(0);
//        arrayDeque.addFirst(1);
//        arrayDeque.removeLast();
//        arrayDeque.removeLast();
//        arrayDeque.addFirst(4);
//        arrayDeque.get(0);
//        arrayDeque.removeLast();
//
//        arrayDeque.addFirst(7);
//        arrayDeque.removeLast();
//
//
//    }
//    public static void addPrintTest(){
//        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
//        arrayDeque.addLast(10);
//        arrayDeque.addLast(20);
//        arrayDeque.addLast(30);
//        arrayDeque.addLast(40);
//        arrayDeque.addFirst(50);
//        arrayDeque.removeFirst();
//        arrayDeque.printDeque();
//        System.out.println("size: " + arrayDeque.size());
//        System.out.println("index 1: " + arrayDeque.get(1));
//        System.out.println("index 0: " + arrayDeque.get(0));
//    }
//
//    public static void resizeTest(){
//        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
//        for(int i = 1; i<= 28;i++){
//            arrayDeque.addFirst(i);
//        }
//        for(int i = 1; i<= 28;i++){
//            arrayDeque.addLast(i);
//        }
//        for(int i = 1; i <= 52;i++){
//            arrayDeque.removeFirst();
//        }
//
//        arrayDeque.printDeque();
//        System.out.println("size: " + arrayDeque.size());
//        System.out.println("index 1: " + arrayDeque.get(1));
//    }
//
//    public static void randomTest(){
//        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
//        Random r = new Random(1);
//        for(int i = 0; i < 50; i++){
//
//            System.out.println("the " + i + "th operation:");
//            double rate = r.nextDouble();
//            if(rate < 0.25){
//                int item = r.nextInt(100);
//                System.out.println("addLast: " + item);
//                arrayDeque.addLast(item);
//            } else if(rate < 0.5){
//                int item = r.nextInt(100);
//                System.out.println("addFirst: " + item);
//                arrayDeque.addFirst(item);
//            } else if(rate < 0.75){
//                System.out.println("removeLast");
//                arrayDeque.removeLast();
//            } else {
//                System.out.println("removeFirst");
//                arrayDeque.removeFirst();
//            }
//            System.out.print("now: ");
//            arrayDeque.printDeque();
//        }
//
//    }
//
//    public static void main(String[] args) {
//        System.out.println("Running tests.\n");
//        //addPrintTest();
//        //resizeTest();
//        randomTest();
//        //addRemoveTest();
//    }
//}
