package com.x404.admin.manage.sys.tools;

import com.x404.admin.core.util.SpringContextHolder;
import com.x404.admin.manage.sys.entity.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class TaskManager {
    private final static Log LOG = LogFactory.getLog(TaskManager.class);
    private SchedulerFactoryBean schedulerFactoryBean;

    private static TaskManager instance = null;

    private TaskManager() {
        this.schedulerFactoryBean = SpringContextHolder
                .getBean(SchedulerFactoryBean.class);
    }

    public synchronized static TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    public void addJob(Task job) throws SchedulerException {

        if (job == null || "1".equals(job.getStatus())) {
            return;
        }

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        LOG.debug(scheduler
                + ".......................................................................................add");
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getId(), null);

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        // 不存在，创建一个
        if (null == trigger) {
            // Class clazz = QuartzJobFactory

            JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class)
                    .withIdentity(job.getId(), null).build();

            jobDetail.getJobDataMap().put("scheduleJob", job);

            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                    .cronSchedule(job.getCronExpression());

            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(job.getId(), null)
                    .withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            // Trigger已存在，那么更新相应的定时设置
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                    .cronSchedule(job.getCronExpression());

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

    public void startJob(Task job) throws SchedulerException {

        if (job == null || "1".equals(job.getStatus())) {
            return;
        }

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        LOG.debug(scheduler
                + ".......................................................................................add");
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getId(), null);

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        // 不存在，创建一个
        if (null == trigger) {

            JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class)
                    .withIdentity(job.getId(), null).build();

            jobDetail.getJobDataMap().put("scheduleJob", job);

            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                    .cronSchedule(job.getCronExpression());

            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(job.getId(), null)
                    .withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            JobKey jobKey = JobKey.jobKey(job.getId(), null);
            scheduler.resumeJob(jobKey);
        }
    }


    public static class QuartzJobFactory implements Job {
        public void execute(JobExecutionContext context)
                throws JobExecutionException {
            Task scheduleJob = (Task) context.getMergedJobDataMap().get(
                    "scheduleJob");
            invokMethod(scheduleJob);
        }

        /**
         * 通过反射调用scheduleJob中定义的方法
         *
         * @param scheduleJob
         */
        @SuppressWarnings({"rawtypes", "unchecked"})
        public void invokMethod(Task scheduleJob) {

            Object object = null;
            Class clazz = null;
            // springId不为空先按springId查找bean
            if (StringUtils.isNotBlank(scheduleJob.getBeanName())) {
                try {
                    object = SpringContextHolder.getBean(scheduleJob.getBeanName());
                } catch (Exception e) {
                    LOG.debug("no bean name " + scheduleJob.getBeanName() + " found");
                    try {
                        clazz = Class.forName(scheduleJob.getBeanName());
                        try {
                            object = SpringContextHolder.getBean(clazz);
                        } catch (Exception e1) {
                            LOG.debug("no bean class " + scheduleJob.getBeanName() + " found");
                            object = clazz.newInstance();
                        }
                    } catch (Exception e1) {
                        LOG.error(e);
                    }
                }
            }
            if (object == null) {
                LOG.error("任务名称 = [" + scheduleJob.getName()
                        + "]---------------未启动成功，请检查是否配置正确！！！");
                return;
            }
            clazz = object.getClass();
            Method method = null;
            try {
                method = clazz.getDeclaredMethod(scheduleJob.getMethodName());
            } catch (NoSuchMethodException e) {
                LOG.error("任务名称 = [" + scheduleJob.getName()
                        + "]---------------未启动成功，方法名设置错误！！！");
            } catch (SecurityException e) {
                LOG.error("任务名称 = [" + scheduleJob.getName()
                        + "]---------------未启动成功，请检查是否配置正确！！！");
            }
            if (method != null) {
                try {
                    Long start = System.currentTimeMillis();
                    LOG.info("任务名称 = [" + scheduleJob.getName()
                            + "]---------------启动成功");
                    method.invoke(object);
                    Long seconds = (System.currentTimeMillis() - start) / 1000;
                    LOG.info("任务名称 = [" + scheduleJob.getName()
                            + "]---------------结束,耗时" + seconds + "秒");
                } catch (IllegalAccessException e) {
                    LOG.error("任务名称 = [" + scheduleJob.getName()
                            + "]---------------执行失败，请检查是否配置正确！！！", e);
                } catch (IllegalArgumentException e) {
                    LOG.error("任务名称 = [" + scheduleJob.getName()
                            + "]---------------执行失败，请检查是否配置正确！！！", e);
                } catch (InvocationTargetException e) {
                    LOG.error("任务名称 = [" + scheduleJob.getName()
                            + "]---------------执行失败，请检查是否配置正确！！！", e);
                }
            }

        }
    }


    /**
     * 获取所有计划中的任务列表
     *
     * @return
     * @throws SchedulerException
     */
    public List<Task> getAllJob() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<Task> jobList = new ArrayList<Task>();
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggers = (List<? extends Trigger>) scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                Task job = new Task();
                job.setId(jobKey.getName());
//				job.se(jobKey.getGroup());
//				job.setDescription("触发器:" + trigger.getKey());
//				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
//				job.setJobStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCronExpression(cronExpression);
                }
                jobList.add(job);
            }
        }
        return jobList;
    }

    /**
     * 所有正在运行的job
     *
     * @return
     * @throws SchedulerException
     */
    public List<Task> getRunningJob() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        List<Task> jobList = new ArrayList<Task>(executingJobs.size());
        for (JobExecutionContext executingJob : executingJobs) {
            Task job = new Task();
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            job.setId(jobKey.getName());
//			job.setJobGroup(jobKey.getGroup());
//			job.setDescription("触发器:" + trigger.getKey());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
//			job.setJobStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                job.setCronExpression(cronExpression);
            }
            jobList.add(job);
        }
        return jobList;
    }

    /**
     * 暂停一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void pauseJob(Task scheduleJob) throws SchedulerException {
        this.deleteJob(scheduleJob);
    }

    /**
     * 恢复一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void resumeJob(Task scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getId(), null);
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void deleteJob(Task scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getId(), null);
        scheduler.deleteJob(jobKey);

    }

    /**
     * 立即执行job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void runAJobNow(Task scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getId(), null);
        scheduler.triggerJob(jobKey);
    }

    /**
     * 更新job时间表达式
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void updateJobCron(Task scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getId(), null);

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());

        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

        scheduler.rescheduleJob(triggerKey, trigger);
    }


}
