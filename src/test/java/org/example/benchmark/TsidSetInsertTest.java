package org.example.benchmark;

import com.github.f4b6a3.tsid.TsidFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TsidSetInsertTest {

    @ParameterizedTest
    @ValueSource(ints = {256, 512, 1_024, 4_096, 8_192, 100_000, 300_000, 500_000})
    void test(int numbersOfRequests) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        List<Long> tsids = getIds(numbersOfRequests);
        Set<Long> idSet = new TreeSet<>();
        List<Callable<Boolean>> tasks = getTasks(tsids, idSet);

        long start = System.currentTimeMillis();
        service.invokeAll(tasks);

        ResultWriter.write("set-insert", System.currentTimeMillis() - start);
    }

    private List<Long> getIds(int numbersOfRequests) {
        List<Long> tsids = new ArrayList<>();
        TsidFactory factory = TsidFactory.newInstance256();
        while (tsids.size() < numbersOfRequests) {
            tsids.add(factory.create().toLong());
        }
        return tsids;
    }

    private List<Callable<Boolean>> getTasks(List<Long> ids, Set<Long> idSet) {
        List<Callable<Boolean>> tasks = new ArrayList<>();
        ids.forEach(id -> tasks.add(() -> {
            synchronized (idSet) {
                return idSet.add(id);
            }
        }));
        return tasks;
    }

}
