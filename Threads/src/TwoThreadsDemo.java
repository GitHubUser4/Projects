public class TwoThreadsDemo {
    public static void main (String[] args) {
        new ExampleThread("Do it!").start();
        new ExampleThread("Definitely not!").start();
    }
}