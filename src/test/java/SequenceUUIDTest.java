import com.wnana.uuid.SequenceUUID;
import org.junit.Test;

public class SequenceUUIDTest {

    @Test
    public void test1() {

        for (int i = 0; i < 1000; i++) {
            System.out.println(SequenceUUID.generate());
        }
          
    }

    @Test
    public void test2() {
        for (int i = 0; i < 1000; i++) {
            Thread thread1= new Thread(new MyTestThread());
            Thread thread2 = new Thread(new MyTestThread());
            thread1.start();
            thread2.start();
        }
    }

    class MyTestThread implements  Runnable {
        @Override
        public void run() {
            System.out.println(SequenceUUID.generate());
        }
        
    }
    
}
