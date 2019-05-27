package com.swago.eightballapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication(scanBasePackages = {"com.swago.eightballapi"})
public class EightballapiApplication {
	private static ApplicationContext applicationContext;

	public static void main(String[] args) {
		applicationContext = SpringApplication.run(EightballapiApplication.class, args);
	}

	private static void printBeans() {
		System.out.println("Let's inspect the beans provided by Spring Boot:");
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}
	}

	private static void checkBeansPresence(String... beans) {
		for (String beanName : beans) {
			System.out.println("Is " + beanName + " in ApplicationContext: " +
					applicationContext.containsBean(beanName));
		}
	}
}
