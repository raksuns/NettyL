package com.raks.comet;

import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timeout;
import org.jboss.netty.util.Timer;
import org.jboss.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

public class TimeOutTest {
    public static void main(String[] argv) {
        final Timer timer = new HashedWheelTimer();
        timer.newTimeout(new TimerTask() {
            public void run(Timeout timeout) throws Exception {
                System.out.println("timeout 5");
            }
        }, 5, TimeUnit.SECONDS);
        timer.newTimeout(new TimerTask() {
            public void run(Timeout timeout) throws Exception {
                System.out.println("timeout 10");
            }
        }, 10, TimeUnit.SECONDS);
        System.out.println("xxxx");
    }
}
