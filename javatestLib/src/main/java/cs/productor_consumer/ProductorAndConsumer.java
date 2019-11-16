package cs.productor_consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by dengfa on 2019-11-16.
 * Des:
 */
public class ProductorAndConsumer {

    private static volatile ProductorAndConsumer        mInstance;
    private                 LinkedBlockingQueue<String> cache      = new LinkedBlockingQueue<>(10);
    private                 ExecutorService             threadPool = Executors.newFixedThreadPool(5);

    private ProductorAndConsumer() {

    }

    public static ProductorAndConsumer getInstance() {
        if (mInstance == null) {
            synchronized (ProductorAndConsumer.class) {
                if (mInstance == null) {
                    mInstance = new ProductorAndConsumer();
                }
            }
        }
        return mInstance;
    }

    public void start() {
        threadPool.submit(new Productor("p1", cache, 5000));
        threadPool.submit(new Productor("p2", cache, 2000));
        threadPool.submit(new Consumer("c1", cache, 1000));
        threadPool.submit(new Consumer("c2", cache, 1000));
    }
}
