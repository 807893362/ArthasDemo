package com.example.demo.task;

import com.example.demo.model.FullGCBean;
import com.example.demo.utils.ThreadPoolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.LongAdder;

//@Service
public class FullGCTask {

    private static final Logger log = LoggerFactory.getLogger(FullGCTask.class);

    // 1s 执行一次
    public static final long CHECK_INTERVAL_TIME = 1 * 1000;

    @Qualifier("taskExecutor")
    @Autowired
	private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 自动处理游戏任务,每隔30s 检查一次是否有到时间的任务
     */
    ScheduledExecutorService scheduledExecutorService;

    @PostConstruct
    public void start() {
        scheduledExecutorService = ThreadPoolUtils.scheduleAtFixedRate(() -> process(), CHECK_INTERVAL_TIME);
    }

    @PreDestroy
    public void destroy(){
        scheduledExecutorService.shutdown();
    }

    final static HashMap<Long, Object> memer = new HashMap();

    final static LongAdder key = new LongAdder();

    /**
     * 每秒创建1W个对象进入堆栈
     */
    private void process() {
		taskExecutor.execute(() -> {
            for (int i = 0; i < 10000; i++) {
                key.add(1L);
                memer.put(key.longValue(), new FullGCBean());
            }
		});
	}

}
