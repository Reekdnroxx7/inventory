package com.x404.admin.manage.sys.ctl;

import com.x404.admin.core.controller.BaseController;
import com.x404.admin.core.json.AjaxJson;
import com.x404.admin.core.util.UUIDUtils;
import com.x404.admin.manage.sys.entity.Task;
import com.x404.admin.manage.sys.tools.TaskManager;
import com.xc350.web.base.mybatis.dao.MybatisExample;
import com.xc350.web.base.mybatis.query.MybatisCriteriaHelper;
import com.xc350.web.base.query.PageList;
import com.x404.admin.manage.sys.service.ITaskService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskCtl extends BaseController implements InitializingBean {
    private final static Log LOG = LogFactory.getLog(TaskCtl.class);
    private ITaskService taskService;

    @Autowired
    public void setTaskService(ITaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(params = "method=listTask")
    @ResponseBody
    /**
     * 用mybatis实现页面的分页显示
     */
    public PageList<Task> listTask(HttpServletRequest request) {
        MybatisExample mybatisExample = MybatisCriteriaHelper.generateFromRequest(request);

        PageList<Task> list = taskService.page(mybatisExample);
        return list;
    }


    @RequestMapping(params = "method=saveTask")
    @ResponseBody
    /*
	 * 用mybatis来增加表数据
	 */
    public AjaxJson saveTask(HttpServletRequest request, Task task) throws SchedulerException {

        String saveType = ServletRequestUtils.getStringParameter(request,
                "_saveType", "add");
        if ("add".equals(saveType)) {
            task.setId(UUIDUtils.next());
            task.setStatus("0");
            TaskManager.getInstance().startJob(task);
            taskService.insertSelective(task);
        } else {
            TaskManager.getInstance().addJob(task);
            taskService.updateByKeySelective(task);
        }
        return new AjaxJson(true, task);
    }

//	@RequestMapping(params="method=updateCron")
//	@ResponseBody
//	/*
//	 * 用mybatis来增加表数据
//	 */
//	public AjaxJson updateCron(HttpServletRequest request,Task task) throws SchedulerException {
//		TaskManager.getInstance().startJob(task);
//		taskService.updateByKeySelective(task);
//		return new AjaxJson(true, task);
//	}


    @RequestMapping(params = "method=updateStatus")
    @ResponseBody
	/*
	 * 用mybatis来增加表数据
	 */
    public AjaxJson updateStatus(HttpServletRequest request, Task task) throws SchedulerException {
//		taskService.updateByKeySelective(task);
        if ("0".equals(task.getStatus())) {
            TaskManager.getInstance().addJob(task);
            this.taskService.updateByKeySelective(task);
        } else {
            TaskManager.getInstance().deleteJob(task);
            this.taskService.updateByKeySelective(task);
        }
        return new AjaxJson(true, task);
    }

    @RequestMapping(params = "method=triggerTask")
    @ResponseBody
	/*
	 * 用mybatis来增加表数据
	 */
    public AjaxJson triggerTask(HttpServletRequest request, Task task) throws SchedulerException {
//
        TaskManager.getInstance().runAJobNow(task);
        return new AjaxJson(true, task);
    }

    /**
     * 删除这个是用mybatis的方式
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "method=deleteTask")
    @ResponseBody
    public AjaxJson deleteTask(@RequestParam("id") String id, HttpServletRequest request) {

        try {
            Task task = new Task(id);
            TaskManager.getInstance().deleteJob(task);
            taskService.deleteByKey(id);
            return new AjaxJson(true, "操作成功");
        } catch (Exception e) {
            LOG.error(e);
            return new AjaxJson(false, "操作失败");
        }
    }

    public void afterPropertiesSet() throws Exception {
        List<Task> tasks = taskService.selectAll();
        for (Task task : tasks) {
            if ("0".equals(task.getStatus())) {
                TaskManager.getInstance().addJob(task);
            }
        }
    }


}