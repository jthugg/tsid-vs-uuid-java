package org.example.benchmark;

import com.github.f4b6a3.tsid.Tsid;
import com.github.f4b6a3.tsid.TsidFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TsidGenerateTest {

    @ParameterizedTest
    @ValueSource(ints = {256, 512, 1_024, 4_096, 8_192, 100_000, 300_000, 500_000})
    void test(int numbersOfRequests) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        List<Callable<Tsid>> tasks = getTsidCallables(numbersOfRequests);

        long start = System.currentTimeMillis();
        service.invokeAll(tasks);

        ResultWriter.write("generate", System.currentTimeMillis() - start);
    }

    private List<Callable<Tsid>> getTsidCallables(int numbersOfRequests) {
        List<Callable<Tsid>> callables = new ArrayList<>();
        TsidFactory factory = TsidFactory.newInstance256();
        while (callables.size() < numbersOfRequests) {
            callables.add(factory::create);
        }
        return callables;
    }

}
