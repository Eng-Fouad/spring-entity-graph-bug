package io.fouad.bugs.bug01.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class RequiredByBeanDefinitionPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        for (String beanName : registry.getBeanDefinitionNames()) {
            final BeanDefinition beanDefinition = registry.getBeanDefinition(beanName);
            if (beanDefinition.getBeanClassName() == null) {
                continue;
            }
            try {
                final Class<?> beanClass = Class.forName(beanDefinition.getBeanClassName());
                if (beanClass.isAnnotationPresent(RequiredBy.class)) {
                    final String[] dependantBeanNames = beanClass.getAnnotation(RequiredBy.class).value();
                    for (String dependantBeanName : dependantBeanNames) {
                        BeanDefinition dependantBeanDefinition = registry.getBeanDefinition(dependantBeanName);
                        dependantBeanDefinition.setDependsOn(beanName);
                    }
                }
            }
            catch (ClassNotFoundException e) { throw new RuntimeException(e); }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException { }
}