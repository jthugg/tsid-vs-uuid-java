package org.example.benchmark;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UuidSetInsertTest {

    @ParameterizedTest
    @ValueSource(ints = {256, 512, 1_024, 4_096, 8_192, 100_000, 300_000, 500_000})
    void test(int numbersOfRequests) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        List<String> uuids = getIds(numbersOfRequests);
        Set<String> idSet = new TreeSet<>();
        List<Callable<Boolean>> tasks = getTasks(uuids, idSet);

        long start = System.currentTimeMillis();
        service.invokeAll(tasks);

        ResultWriter.write("set-insert", System.currentTimeMillis() - start);
    }

    private List<String> getIds(int numbersOfRequests) {
        List<String> uuids = new ArrayList<>();
        while (uuids.size() < numbersOfRequests) {
            uuids.add(UUID.randomUUID().toString());
        }
        return uuids;
    }

    private List<Callable<Boolean>> getTasks(List<String> ids, Set<String> idSet) {
        List<Callable<Boolean>> tasks = new ArrayList<>();
        ids.forEach(id -> tasks.add(() -> {
            synchronized (idSet) {
                return idSet.add(id);
            }
        }));
        return tasks;
    }

}
