public class Main {
    public static int TIME_BEFORE_ANOTHER_VISITOR = 600;
    public static void main(String[] args) throws InterruptedException {
        ServerThread server1 = new ServerThread("Официант 1");
        ServerThread server2 = new ServerThread("Официант 2");
        ServerThread server3 = new ServerThread("Официант 3");
        CookThread cook = new CookThread("Повар");
        PartyThread visitor1 = new PartyThread("Посетитель 1");
        PartyThread visitor2 = new PartyThread("Посетитель 2");
        PartyThread visitor3 = new PartyThread("Посетитель 3");
        PartyThread visitor4 = new PartyThread("Посетитель 4");
        PartyThread visitor5 = new PartyThread("Посетитель 5");


        server1.start();
        server2.start();
        server3.start();
        cook.start();
        visitor1.start();
        Thread.sleep(TIME_BEFORE_ANOTHER_VISITOR);
        visitor2.start();
        Thread.sleep(TIME_BEFORE_ANOTHER_VISITOR);
        visitor3.start();
        Thread.sleep(TIME_BEFORE_ANOTHER_VISITOR);
        visitor4.start();
        Thread.sleep(TIME_BEFORE_ANOTHER_VISITOR);
        visitor5.start();
    }
}
