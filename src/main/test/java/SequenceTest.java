
import com.mairuis.concurrent.disruptor.Sequence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SequenceTest
{
    @Test
    void shouldReturnChangedValueAfterAddAndGet()
    {
        final Sequence sequence = new Sequence(0);

        assertEquals(10, sequence.addAndGet(10));
        assertEquals(10, sequence.get());
    }

    @Test
    void shouldReturnIncrementedValueAfterIncrementAndGet()
    {
        

        final Sequence sequence = new Sequence(0);

        assertEquals(1, sequence.incrementAndGet());
        assertEquals(1, sequence.get());
    }
}