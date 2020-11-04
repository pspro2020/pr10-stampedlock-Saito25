package Main;

import reader.ProductConsultor;
import store.WareHouse;
import write.ProductAdder;

import java.util.concurrent.TimeUnit;

public class Main {

    private final Thread[] readerThreads = new Thread[4];
    private final Thread writeThread;
    private final WareHouse wareHouse = new WareHouse();

    Main() {
        writeThread = new Thread(new ProductAdder(wareHouse));
        initReaderThread();

        writeThread.start();

        sleepThread(4);

        startReaderThreads();
    }

    private void initReaderThread() {
        for (int i = 0; i < readerThreads.length; i++) {
            readerThreads[i] = new Thread(new ProductConsultor(i + 1, wareHouse));
        }
    }

    private void sleepThread(int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startReaderThreads() {
        for (int i = 0; i < readerThreads.length; i++) {
            readerThreads[i].start();
            System.out.println("Iniciado el hilo lector del producto" + (i + 1));
            sleepThread(1);
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
