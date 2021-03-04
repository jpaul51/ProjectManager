package com.jonas.suivi.views.helloworld;

import org.springframework.beans.factory.annotation.Autowired;

import com.jonas.suivi.backend.services.PersonService;
import com.jonas.suivi.backend.services.UserAccountService;
import com.jonas.suivi.views.main.MainView;
import com.jonas.suivi.views.prompts.PasswordPrompt;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
 
@Route(value = "", layout = MainView.class)
@PageTitle("Hello World")
@CssImport("./styles/views/helloworld/hello-world-view.css")
@RouteAlias("home")
public class HomeView extends Div  implements AfterNavigationObserver{

   
    @Autowired
    UserAccountService userService;
    

    public HomeView(@Autowired PersonService personService) {
    	
        addClassName("hello-world-view");

    
    }

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		if(userService.getUsrLoggedAccount().isResetPassword()) {
			new PasswordPrompt(userService).open();
		}
        UI.getCurrent().getPage().getHistory().pushState(null, "home");

		
		
	}
}
