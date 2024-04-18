package org.example.benchmark;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UuidGenerateTest {

    @ParameterizedTest
    @ValueSource(ints = {256, 512, 1_024, 4_096, 8_192, 100_000, 300_000, 500_000})
    void test(int numbersOfRequests) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        List<Callable<UUID>> tasks = getUuidCallables(numbersOfRequests);

        long start = System.currentTimeMillis();
        service.invokeAll(tasks);

        ResultWriter.write("generate", System.currentTimeMillis() - start);
    }

    private List<Callable<UUID>> getUuidCallables(int numbersOfRequests) {
        List<Callable<UUID>> callables = new ArrayList<>();
        while (callables.size() < numbersOfRequests) {
            callables.add(UUID::randomUUID);
        }
        return callables;
    }

}
