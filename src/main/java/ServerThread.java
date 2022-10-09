public class ServerThread extends Thread {
    private static final int ORDER = 1;
    private static final int MAX_ORDERS = 10;
    private static final int TIME_TO_TAKE_AN_ORDER = 300;
    private static final int TIME_TO_GO_TO_THE_TABLE = 300;
    private boolean isAvailable;

    public ServerThread(String name) {
        super(name);
        this.isAvailable = true;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " на работе");
        for (int i = 0; i < MAX_ORDERS; i++) {
            if (isAvailable) {
                try {
                    Thread.sleep(TIME_TO_GO_TO_THE_TABLE);
                } catch (InterruptedException e) {
                    return;
                }
                synchronized (PartyThread.class) {
                    this.isAvailable = false;
                    PartyThread.class.notify();
                }
            }
            synchronized (PartyThread.order) {
                try {
                    PartyThread.order.wait();
                } catch (InterruptedException e) {
                    return;
                }
                System.out.println(Thread.currentThread().getName() + " подошёл к столику");
                try {
                    Thread.sleep(TIME_TO_TAKE_AN_ORDER);
                } catch (InterruptedException e) {
                    return;
                }
                System.out.println(Thread.currentThread().getName() + " принял заказ");
            }
            synchronized (CookThread.orders) {
                CookThread.orders.add(ORDER);
                CookThread.orders.notify();
            }
            synchronized (CookThread.class) {
                try {
                    CookThread.class.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println(Thread.currentThread().getName() + " забрал еду и понёс за столик");
            synchronized (ServerThread.class) {
                this.isAvailable = true;
                ServerThread.class.notify();
            }
        }
    }
}
