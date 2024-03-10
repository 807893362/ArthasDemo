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
public class CodeHotUpdateTask {

    private static final Logger log = LoggerFactory.getLogger(CodeHotUpdateTask.class);

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
     * 热更新
     */
    private void process() {
		taskExecutor.execute(() -> {
            log.info("热更新日志：{}", println("c1", "c2"));
		});
	}

    private boolean println(String c1, String c2){

        boolean flag = true;

        return flag;
    }

}
