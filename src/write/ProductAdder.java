package write;

import store.WareHouse;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ProductAdder implements Runnable {

    private final Random random = new Random();
    private final WareHouse wareHouse;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public ProductAdder(WareHouse wareHouse) {
        this.wareHouse = wareHouse;
    }

    @Override
    public void run() {
        int productToAdd = 0;

        while (!Thread.currentThread().isInterrupted()) {
            startConsult();
            productToAdd = random.nextInt(4) + 1;
            wareHouse.addStockProduct(productToAdd);
            endConsult(productToAdd);

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private void startConsult() {
        System.out.printf("%s -> %s está a la espera de poder añadir un producto\n",
                LocalTime.now().format(dateTimeFormatter),
                Thread.currentThread().getName());
    }

    private void endConsult(int productToAdd) {
        System.out.printf("%s -> %s ha añadido el producto %d\n",
                LocalTime.now().format(dateTimeFormatter),
                Thread.currentThread().getName(),
                productToAdd);
    }
}
