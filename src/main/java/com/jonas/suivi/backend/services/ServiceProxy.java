package com.jonas.suivi.backend.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.jonas.suivi.backend.model.impl.Intervention;
import com.jonas.suivi.backend.model.impl.Person;
import com.jonas.suivi.backend.model.impl.Project;
import com.jonas.suivi.backend.model.impl.Translation;
import com.jonas.suivi.backend.model.impl.UserAccount;

@Component
public class ServiceProxy implements ApplicationContextAware{
	
    private final Map<String, DisplayableService> myServices = new HashMap<String, DisplayableService>();
	
	@Autowired 
	@Qualifier("Project")
	private DisplayableService projectService;

	@Autowired 
	@Qualifier("Intervention")
	private DisplayableService interService;

	@Autowired 
	@Qualifier("Person")
	private DisplayableService personService;
	
	@Autowired 
	@Qualifier("Translation")
	private TranslationService translationService;

	@Autowired 
	@Qualifier("Account")
	private UserAccountService userAccountService;

	
	
	
	public enum ServiceStore {
		PROJECT("Project", Project.class),
	    INTERVENTION("Intervention", Intervention.class),
	    PERSON("Person", Person.class),
	    ACCOUNT("Account", UserAccount.class),
	    TRANSLATION("Translation", Translation.class);

	    private String serviceName;
	    private Class<?> clazz;

	    private static final Map<Class<?>, String> mapOfClassTypes = new HashMap<Class<?>, String>();

	    static {
	        //This little bit of black magic, basically sets up your 
	        //static map and allows you to get an enum value based on a classtype
	        ServiceStore[] namesArray = ServiceStore.values();
	        for(ServiceStore name : namesArray){
	            mapOfClassTypes.put(name.clazz, name.serviceName);
	        }
	    }

	    private ServiceStore(String serviceName, Class<?> clazz){
	        this.serviceName = serviceName;
	        this.clazz = clazz;
	    }

	    public String getServiceBeanName() {
	        return serviceName;
	    }

	    public static <T> String getOrdinalFromValue(Class<?> clazz) {
	    	 Map<Class<?>, String> m = mapOfClassTypes;
	    	
	        return mapOfClassTypes.get(clazz);
	    }
	}

	public DisplayableService getInstance(Class<?> clazz) {
        return myServices.get(ServiceStore.getOrdinalFromValue(clazz));
    }

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
        myServices.putAll(applicationContext.getBeansOfType(DisplayableService.class,false,true));
        
	}
	
}
