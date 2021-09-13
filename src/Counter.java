public class Counter {
    public static int counter = 0;
    // To test: uncomment
//    public static void main(String[] args) {
//        System.out.println("Counter in the beginning " + counter);
//        increase(5);
//        System.out.println("Counter now " + counter);
//        decrease(2);
//        System.out.println("Counter now " + counter);
//
//    }

    public static void increase(int value){
        counter = counter + value;
    }

    public static void decrease(int value){
        counter = counter - value;
    }
}
