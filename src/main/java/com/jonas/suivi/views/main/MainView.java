package com.jonas.suivi.views.main;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

import com.jonas.suivi.UserContextFactory;
import com.jonas.suivi.views.about.AboutView;
import com.jonas.suivi.views.descriptors.InterventionDescriptor;
import com.jonas.suivi.views.descriptors.PersonDescriptor;
import com.jonas.suivi.views.descriptors.ProjectDescriptor;
import com.jonas.suivi.views.descriptors.TranslationDescriptor;
import com.jonas.suivi.views.descriptors.UserAccountDescriptor;
import com.jonas.suivi.views.helloworld.HomeView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.HighlightCondition;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.Command;
import com.vaadin.flow.server.VaadinSession;

/**
 * The main view is a top-level placeholder for other views.
 */
@CssImport("./styles/views/main/main-view.css")
@JsModule("./styles/shared-styles.js")
public class MainView extends AppLayout {

    private final Tabs menu;
    private H1 viewTitle;
    
    @Autowired
    private ApplicationContext application;

    public MainView() {
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        menu = createMenu();
        addToDrawer(createDrawerContent(menu));
    }
    
   
    

    private Component createHeaderContent() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.add(new DrawerToggle());
        viewTitle = new H1();
        
        HorizontalLayout btnLayout = new HorizontalLayout();
        btnLayout.getThemeList().set("dark", true);
        btnLayout.setWidthFull();
        btnLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        btnLayout.setAlignItems(Alignment.END);
        
        Button btnLogout = new Button();
        btnLogout.addClickListener(c ->{

        	 
        	UI.getCurrent().accessSynchronously(new Command() {
        		
        		
				@Override
				public void execute() {
					
					UI.getCurrent().getPage().setLocation("logout");
					UI.getCurrent().close();
					VaadinSession.getCurrent().setCurrent(null);
				}
			});
        
        
        });
        btnLogout.setText("Logout");
        
        btnLayout.add(btnLogout);
        
        layout.add(viewTitle);
        layout.add(btnLayout);
        layout.add(new Avatar());
        
        return layout;
    }

    private Component createDrawerContent(Tabs menu) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.add(new Image("images/logo.png", "SuiviInter logo"));
        logoLayout.add(new H1("SuiviInter"));
        layout.add(logoLayout, menu);
        return layout;
    }

    private Tabs createMenu() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(createMenuItems());
        
        RouterLink rt = new RouterLink();
    	Div menuDiv = createMenuDiv("Projet", rt);
    	
    	rt.setHighlightCondition(new HighlightCondition<RouterLink>() {
			
			@Override
			public boolean shouldHighlight(RouterLink t, AfterNavigationEvent event) {
				boolean a = UserContextFactory.getCurrentUserContext().getCurrentClass() != null && 
						UserContextFactory.getCurrentUserContext().getCurrentClass().equals(ProjectDescriptor.class);
				return a;
			}
		});
    	

    	RouterLink rtPerson = new RouterLink();
    	Div menuPersonDiv = createMenuDiv("Users", rtPerson);

    	RouterLink rtInter = new RouterLink();
    	Div menuInterDiv = createMenuDiv("Interventions", rtInter);
    	
    	RouterLink rtTranslation = new RouterLink();
    	Div menuTranslationDiv = createMenuDiv("Translations", rtTranslation);

    	RouterLink rtAccount = new RouterLink();
    	Div menuUserAccountDiv = createMenuDiv("Accounts", rtAccount);

    	menuInterDiv.addClickListener(c -> {
    		SplitViewFactory dvf = new SplitViewFactory(InterventionDescriptor.class);
    		dvf.show();
    		viewTitle.setText(dvf.getAppTitle());
    	});
    	
    	menuPersonDiv.addClickListener(e -> {
    		SplitViewFactory dvf = new SplitViewFactory(PersonDescriptor.class);
    		dvf.show();
    		viewTitle.setText(dvf.getAppTitle());
    	});
    	
    	
    	menuDiv.add(new RouterLink());
    	menuDiv.addClickListener(e -> {
    		SplitViewFactory dvf = new SplitViewFactory(ProjectDescriptor.class);
    		dvf.show();
    		viewTitle.setText(dvf.getAppTitle());
    		
    	});
    	
    	menuTranslationDiv.addClickListener(e -> {
    		SplitViewFactory dvf = new SplitViewFactory(TranslationDescriptor.class);
    		dvf.show();
    		viewTitle.setText(dvf.getAppTitle());
    		
    	});
    	
    	menuUserAccountDiv.addClickListener(e -> {
    		SplitViewFactory dvf = new SplitViewFactory(UserAccountDescriptor.class);
    		dvf.show();
    		viewTitle.setText(dvf.getAppTitle());
    		
    	});
    	
    	tabs.add(createTab(menuDiv), createTab(menuPersonDiv), createTab(menuInterDiv), createTab(menuTranslationDiv),createTab(menuUserAccountDiv));


        return tabs;
    }

	private Div createMenuDiv(String label, RouterLink rt) {
		Div menuDiv = new Div();
    	menuDiv.setWidthFull();
    	menuDiv.setHeightFull();
    	menuDiv.addClassName("menuDiv");
    	
    	rt.setText(label);
//    	rt.getElement().getStyle()
    	
    	menuDiv.add(rt);
//    	menuDiv.add(new RouterLink());
		return menuDiv;
	}

private Component[] createMenuItems() {
    	
    	RouterLink aboutRt = new RouterLink("About", AboutView.class);
//    	RouterLink pushRt = new RouterLink("Push", PushyView.class);
    	
    	
    	
        RouterLink[] links = new RouterLink[] {
            new RouterLink("Hello World", HomeView.class),
            aboutRt,

        };
        return Arrays.stream(links).map(MainView::createTab).toArray(Tab[]::new);
    }

    private static Tab createTab(Component content) {
        final Tab tab = new Tab();
        tab.add(content);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
//        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
        viewTitle.setText(getCurrentPageTitle());
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private String getCurrentPageTitle() {
//    	return "";
    	
    	PageTitle pageTitle = getContent().getClass().getAnnotation(PageTitle.class);
//    	String staticPageTitle = value(); 
        if(pageTitle != null) {
        	return pageTitle.value();
        }
        else {
        	return ((SplitView)getContent()).getPageTitle();
        	
        }
    	 
    }
}
