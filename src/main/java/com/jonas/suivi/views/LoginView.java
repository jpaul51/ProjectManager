package com.jonas.suivi.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route("login") 
@RouteAlias("logout")
@RouteAlias("index.html")
@PageTitle("Login | Project manager")
public class LoginView extends VerticalLayout implements BeforeEnterObserver, AfterNavigationObserver	{

	private LoginForm login = new LoginForm(); 

	@Autowired
	AuthenticationManager authenticationManager; 
//    CustomRequestCache requestCache;
    
	public LoginView(){
		addClassName("login-view");
		setSizeFull();
		setAlignItems(Alignment.CENTER); 
		setJustifyContentMode(JustifyContentMode.CENTER);

//		login.setAction("login");
		login.addLoginListener(c ->{
			final Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(c.getUsername(), c.getPassword())); 

                // if authentication was successful we will update the security context and redirect to the page requested first
                SecurityContextHolder.getContext().setAuthentication(authentication); 
                UI.getCurrent().navigate("");  
		});
		

		add(new H1("Project manager"), login);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		// inform the user about an authentication error
		if(beforeEnterEvent.getLocation() 
        .getQueryParameters()
        .getParameters()
        .containsKey("error")) {
            login.setError(true);
        }
			
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		UI.getCurrent().getPage().executeJs("document.getElementById(\"vaadinLoginUsername\").focus();", "");

	}
}
