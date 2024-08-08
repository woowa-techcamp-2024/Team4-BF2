package woowa.team4.bff.review;

public class ReviewThis {

    private static int value = 0;
    private static boolean flag = false;

    public static void main(String[] args) {
        new Thread(() -> {
            while (!flag) {
            }
            System.out.println("value: " + value);
        }).start();

        new Thread(() -> {
            value = 42;
            flag = true;
        }).start();
    }
}
