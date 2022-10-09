import java.util.ArrayList;
import java.util.List;

public class PartyThread extends Thread {
    private static final int TIME_TO_EAT = 700;
    private static final int ORDER = 1;
    public static final List<Integer> order = new ArrayList<>();
    public PartyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()
                + " занял столик " + Thread.currentThread().getName().charAt(Thread.currentThread().getName().length() - 1));
            synchronized (PartyThread.class) {
                try {
                    PartyThread.class.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
        synchronized (order) {
            order.add(ORDER);
            order.notify();
        }
        synchronized (ServerThread.class) {
            try {
                ServerThread.class.wait();
            } catch (InterruptedException e) {
                return;
            }
            System.out.println(Thread.currentThread().getName() + " получил еду");
            try {
                Thread.sleep(TIME_TO_EAT);
            } catch (InterruptedException e) {
                return;
            }
            System.out.println(Thread.currentThread().getName() + " покинул ресторан");
        }
    }
}
