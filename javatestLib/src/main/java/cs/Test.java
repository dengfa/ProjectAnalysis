package cs;

import cs.productor_consumer.blockingqueue.ProductorAndConsumer;

/**
 * Created by dengfa on 2019-11-16.
 * Des:
 */
public class Test {

    public static void main(String[] args) {
        ProductorAndConsumer.getInstance().start();
    }
}
