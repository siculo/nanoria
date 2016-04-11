package org.javamac.nanoria.core.utils;

import org.junit.Assert;
import org.junit.Test;

public class CircularQueueTest {

    @Test
    public void scheduler() {
        CircularQueue<String> scheduler = new CircularQueue<String>();
    }

    @Test
    public void schedulerWithData() {
        CircularQueue<String> scheduler = new CircularQueue<String>("uno", "due", "tre");
    }

    @Test
    public void schedulerNext() {
        CircularQueue<String> scheduler = new CircularQueue<String>("uno", "due", "tre");
        String next = scheduler.next();
        Assert.assertEquals("uno", next);
    }

    @Test
    public void schedulerNextNext() {
        CircularQueue<String> scheduler = new CircularQueue<String>("uno", "due", "tre");
        scheduler.next();
        String next = scheduler.next();
        Assert.assertEquals("due", next);
    }

    @Test
    public void schedulerNextLoops() {
        CircularQueue<String> scheduler = new CircularQueue<String>("uno", "due", "tre");
        for (int i = 0; i < 3; i++) {
            scheduler.next();
        }
        String next = scheduler.next();
        Assert.assertEquals("uno", next);
    }

    @Test
    public void schedulerNextWithoutData() {
        CircularQueue<String> scheduler = new CircularQueue<String>();
        Assert.assertNull(scheduler.next());
    }
}
