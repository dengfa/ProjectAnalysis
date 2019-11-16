package cs.productor_consumer;

import java.util.concurrent.BlockingQueue;

/**
 * Created by dengfa on 2019-11-16.
 * Des:
 */
public class Productor implements Runnable {

    private final long                  mInterval;
    private       BlockingQueue<String> mQueue;
    private       String                mProdectorName;
    private       int                   i;

    public Productor(String prodectorName, BlockingQueue<String> queue, long interval) {
        mQueue = queue;
        mProdectorName = prodectorName;
        mInterval = interval;
    }

    @Override
    public void run() {
        try {
            while (true) {
                mQueue.put(mProdectorName + " - " + i);
                System.out.println(mProdectorName + " - " + i);
                i++;
                Thread.sleep(mInterval);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
