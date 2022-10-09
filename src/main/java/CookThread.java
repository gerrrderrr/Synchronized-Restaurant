import java.util.LinkedList;
import java.util.Queue;

public class CookThread extends Thread {
    private static final int COOKING_TIME = 1000;
    private static final int MAX_ORDERS = 10;
    public static final Queue<Integer> orders = new LinkedList<>();

    public CookThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " на работе");
        for (int i = 0; i < MAX_ORDERS; i++) {
            synchronized (orders) {
                if (orders.isEmpty()) {
                    try {
                        orders.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                System.out.println(Thread.currentThread().getName() + " получил заказ и начал готовить");
                try {
                    Thread.sleep(COOKING_TIME);
                } catch (InterruptedException e) {
                    return;
                }
            }
            synchronized (CookThread.class) {
                System.out.println(Thread.currentThread().getName() + " закончил готовить");
                orders.poll();
                CookThread.class.notify();
            }
        }
    }
}
