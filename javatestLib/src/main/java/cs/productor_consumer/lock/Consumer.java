package cs.productor_consumer.lock;

import java.util.LinkedList;

/**
 * Created by dengfa on 2019-11-16.
 * Des:
 */
public class Consumer implements Runnable {
    private long               mInterval;
    private LinkedList<String> mQueue;
    private String             mConsumerName;

    public Consumer(String consumerName, LinkedList<String> queue, long interval) {
        mQueue = queue;
        mConsumerName = consumerName;
        mInterval = interval;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String take = mQueue. poll();
                System.out.println(mConsumerName + " - " + take);
                Thread.sleep(mInterval);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
