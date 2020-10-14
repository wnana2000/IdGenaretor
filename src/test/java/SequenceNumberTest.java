import com.wnana.snowflake.SequenceNumber;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SequenceNumberTest {


    @Test
    public void test1() {
        SequenceNumber sequenceNumber = sequenceNumber();
        for (int i = 0; i < 10000 ; i++) {
            System.out.println(sequenceNumber.nextId());
        }
    }


    @Test
    public void test2() {
        SequenceNumber sequenceNumber = sequenceNumber();
        for (int i = 0; i < 10000; i++) {
            Thread testThread1 =  new Thread(new TestThread1(sequenceNumber));
            Thread testThread2 = new Thread(new TestThread1(sequenceNumber));
            testThread1.start();
            testThread2.start();
        }
    }

    private SequenceNumber sequenceNumber() {
        int workerId = 1;
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            String[] ips = ip.split("\\.");
            workerId = Integer.parseInt(ips[ips.length - 1]);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return new SequenceNumber(0, workerId, true, 5L, false);
    }
    
    class TestThread1 implements Runnable {
        private SequenceNumber sequenceNumber;

        public TestThread1(SequenceNumber sequenceNumber) {
            this.sequenceNumber = sequenceNumber;
        }
        @Override
        public void run() {
            System.out.println(sequenceNumber.nextId());
        }
    }
}
