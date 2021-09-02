package loadbalancing;

import com.mairuis.distribute.loadbalancing.ConsistentHashRouter;
import com.mairuis.distribute.loadbalancing.Node;
import lombok.Data;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Mairuis
 * @since 2020/12/21
 */
public class TestConsistentHashRouter {

    @Test
    public void test() {
        ConsistentHashRouter<ServiceNode> router = new ConsistentHashRouter<>(32);
        router.put(new ServiceNode("10.1.1.1"));
        router.put(new ServiceNode("10.1.1.2"));
        router.put(new ServiceNode("10.1.1.3"));
        router.put(new ServiceNode("10.1.1.4"));
        router.put(new ServiceNode("10.1.1.5"));
        router.put(new ServiceNode("10.1.1.6"));
        router.put(new ServiceNode("10.1.1.7"));
        router.put(new ServiceNode("10.1.1.8"));

        for (int i = 0; i < 100000000; i++) {
            router.route(String.valueOf(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE))).service();
        }

        for (ServiceNode serviceNode : router) {
            System.out.println(serviceNode.getUniqueId() + ": " + serviceNode.getCount());
        }
    }
}

@Data
class ServiceNode implements Node {

    private final String id;
    private boolean available;
    private final AtomicInteger count = new AtomicInteger(0);

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

    @Override
    public boolean isAvailable() {
        return available;
    }
}

