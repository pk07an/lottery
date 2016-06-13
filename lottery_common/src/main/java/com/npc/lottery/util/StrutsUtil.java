package com.npc.lottery.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.dispatcher.Dispatcher;

import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.entities.ActionConfig;

/**
 * 工具类 - Struts
 */

public class StrutsUtil {

	/**
	 * 获取Struts2配置信息.
	 * 
	 * @return Struts2 Configuration
	 */
	public static Configuration getConfiguration() {
		Dispatcher dispatcher = Dispatcher.getInstance();
		ConfigurationManager configurationManager = dispatcher.getConfigurationManager();
		return configurationManager.getConfiguration();
	}

	/**
	 * 获取所有namespace名称.
	 * 
	 * @return namespace名称的集合
	 */
	public static Set<String> getAllNamespaces() {
		Set<String> namespaces = new HashSet<String>();
		Configuration configuration = getConfiguration();
		Map<String, Map<String, ActionConfig>> actionConfigs = configuration.getRuntimeConfiguration().getActionConfigs();
		for (String namespace : actionConfigs.keySet()) {
			namespaces.add(namespace);
		}
		return namespaces;
	}

	/**
	 * 获取所有Action名称.
	 * 
	 * @return Action名称的集合
	 */
	public static Set<String> getAllActionName() {
		Set<String> actionNames = new HashSet<String>();
		Configuration configuration = getConfiguration();
		Map<String, Map<String, ActionConfig>> actionConfigs = configuration.getRuntimeConfiguration().getActionConfigs();
		for (String namespace : actionConfigs.keySet()) {
			Map<String, ActionConfig> actionConfigMap = actionConfigs.get(namespace);
			for (String actionName : actionConfigMap.keySet()) {
				actionNames.add(actionName);
			}
		}
		return actionNames;
	}

	/**
	 * 获取所有Action类名称(不包含com.opensymphony.xwork2.ActionSupport类).
	 * 
	 * @return Action类名称的集合
	 */
	public static Set<String> getAllActionClassName() {
		Set<String> actionClassNames = new HashSet<String>();
		Configuration configuration = getConfiguration();
		Map<String, Map<String, ActionConfig>> actionConfigs = configuration.getRuntimeConfiguration().getActionConfigs();
		for (String namespace : actionConfigs.keySet()) {
			Map<String, ActionConfig> actionConfigMap = actionConfigs.get(namespace);
			for (String actionName : actionConfigMap.keySet()) {
				String actionClassName = actionConfigMap.get(actionName).getClassName();
				if (!StringUtils.equals(actionClassName, "com.opensymphony.xwork2.ActionSupport")) {
					actionClassNames.add(actionClassName);
				}
			}
		}
		return actionClassNames;
	}

	/**
	 * 获取所有Action类.
	 * 
	 * @return Action类的集合
	 */
	@SuppressWarnings("unchecked")
	public static Set<Class> getAllActionClass() {
		Set<Class> actionClasss = new HashSet<Class>();
		Configuration configuration = getConfiguration();
		Map<String, Map<String, ActionConfig>> actionConfigs = configuration.getRuntimeConfiguration().getActionConfigs();
		for (String namespace : actionConfigs.keySet()) {
			Map<String, ActionConfig> actionConfigMap = actionConfigs.get(namespace);
			for (String actionName : actionConfigMap.keySet()) {
				Class actionClass = actionConfigMap.get(actionName).getClass();
				actionClasss.add(actionClass);
			}
		}
		return actionClasss;
	}
	public static void main(String[] args) {
		StrutsUtil.getAllActionName();
	}
}