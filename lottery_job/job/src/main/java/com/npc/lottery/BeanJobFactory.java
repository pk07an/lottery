package com.npc.lottery;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * 
 * @类名： DemoBeanJobFactory.java 
 * @描述：自定义的FactoryBean，Spring3.0的bean自动实现了ApplicationContextAware接口，允许任何通过Spring配置的bean都可以自动注入它所属的上下文
 * @作者： mxyanx
 * @修改日期： 2014年10月23日
 */
public class BeanJobFactory extends  SpringBeanJobFactory
{
    
    @Autowired
    private AutowireCapableBeanFactory beanFactory;
    
    /**
     * 这里我们覆盖了super的createJobInstance方法，对其创建出来的类再进行autowire。
     */
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object jobInstance = super.createJobInstance(bundle);
        beanFactory.autowireBean(jobInstance);
        return jobInstance;
    }

}
