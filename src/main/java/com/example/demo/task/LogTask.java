package com.example.demo.task;

import com.example.demo.utils.ThreadPoolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class LogTask {

    private static final Logger log = LoggerFactory.getLogger(LogTask.class);

    // 2s 执行一次
    public static final long CHECK_INTERVAL_TIME = 2 * 1000;

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

    /**
     * 处理核心业务
     */
    private void process() {
		taskExecutor.execute(() -> {
            log.debug("DEBUG。耗时：{}", 0);
            log.info("INFO。耗时：{}", 0);
		});
	}

}
