package loadbalancing;

import com.mairuis.distribute.consistenthash.ConsistentHashRouter;
import com.mairuis.distribute.consistenthash.Node;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Mairuis
 * @since 2020/12/21
 */
public class TestConsistentHashRouter {

    @Test
    public void test() {
        ConsistentHashRouter<ServiceNode> router = new ConsistentHashRouter<>();
        router.put(new ServiceNode("10.1.1.1"));
        router.put(new ServiceNode("10.1.1.2"));
        router.put(new ServiceNode("10.1.1.3"));

        for (int i = 0; i < 100000000; i++) {
            router.route(String.valueOf(i)).service();
        }
        for (ServiceNode serviceNode : router) {
            System.out.println(serviceNode.getUniqueId() + ": " + serviceNode.getCount());
        }
    }
}

class ServiceNode implements Node {

    private final String id;
    private AtomicInteger count = new AtomicInteger(0);

    public ServiceNode(String id) {
        this.id = id;
    }

    public void service() {
        this.count.incrementAndGet();
    }

    public int getCount() {
        return count.get();
    }

    @Override
    public String getUniqueId() {
        return id;
    }
}

