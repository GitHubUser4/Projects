public class TwoThreadsDemo {
    public static void main(String[] args) {
        new ExampleThread("Thread 1").start();
        new ExampleThread("Thread 2").start();
        new ExampleThread("Thread 3").start();
        new ExampleThread("Thread 4").start();
    }
}