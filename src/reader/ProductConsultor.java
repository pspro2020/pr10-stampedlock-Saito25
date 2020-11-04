package reader;

import store.WareHouse;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ProductConsultor implements Runnable {

    private final int productoToConsult;
    private final WareHouse wareHouse;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public ProductConsultor(int productoToConsult, WareHouse wareHouse) {
        this.productoToConsult = productoToConsult;
        this.wareHouse = wareHouse;
    }

    @Override
    public void run() {
        int countOfProduct = 0;

        while (!Thread.currentThread().isInterrupted()) {
            startConsult();

            countOfProduct = wareHouse.consulStockOfProduct(productoToConsult);
            endConsult(countOfProduct);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private void startConsult() {
        System.out.printf("%s -> %s está consultando el producto %d\n",
                LocalTime.now().format(dateTimeFormatter),
                Thread.currentThread().getName(),
                productoToConsult);
    }

    private void endConsult(int countOfProduct) {
        System.out.printf("%s -> %s el conteo del producto %d es %d\n",
                LocalTime.now().format(dateTimeFormatter),
                Thread.currentThread().getName(),
                productoToConsult,
                countOfProduct);
    }
}
