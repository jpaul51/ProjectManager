package com.jonas.suivi.views.main;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.jonas.suivi.UserContextFactory;
import com.jonas.suivi.backend.services.DisplayableService;
import com.jonas.suivi.backend.services.ServiceProxy;
import com.jonas.suivi.views.descriptors.InvalidActionDescriptorException;
import com.jonas.suivi.views.descriptors.InvalidFieldDescriptorException;
import com.jonas.suivi.views.model.Application;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;

@Route(value = "app", layout = MainView.class)
public class SplitView extends AbstractView implements HasUrlParameter<String>, PropertyChangeListener {


	@Autowired
	UserContextFactory userContextFactory;

	@Autowired
	ServiceProxy serviceProxy;


	DisplayableService displayableService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 222222L;

	EAPP_CONTEXT appCtx = EAPP_CONTEXT.ADD;

	

	SplitLayout splitLayout;


	
	List<SingleView> viewHistory = new ArrayList<>();
	
	public SplitView() throws InvalidFieldDescriptorException, InvalidActionDescriptorException {
		super();
	}
	
	private void generateView() throws InvalidFieldDescriptorException, InvalidActionDescriptorException {
		
		Class<?> cl = UserContextFactory.getCurrentUserContext().getCurrentClass();
		SingleView gridView = new GridView(cl, serviceProxy);
	
		if(cl == null) {
			UI.getCurrent().navigate("");
			return;
		}
	
		viewHistory.add(gridView);
		
		UI.getCurrent().getPage().getHistory().pushState(null, "app");
		gridView.propertyChangeSupport.addPropertyChangeListener(this);
		
		splitLayout = new SplitLayout();
		splitLayout.addToPrimary(gridView);
		splitLayout.setSizeFull();
		this.add(splitLayout);
		
		viewHistory.forEach(s -> s.reload());

	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		reloadView();
		
	}
	
	@PostConstruct
	public void reloadView() {
		this.removeAll();
		try {
			viewHistory = new ArrayList<SingleView>();
			generateView();
		} catch (InvalidFieldDescriptorException | InvalidActionDescriptorException e) {
			e.printStackTrace();
		}
		
	}


	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		if (parameter != null) {

		}
	}

	@Override
	public String getPageTitle() {
		return "";
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		ViewState oldViewState = (ViewState) evt.getOldValue();
		ViewState newViewState = (ViewState) evt.getNewValue();
		
		Class<? extends Application> viewClazz = newViewState.getContext();
		
		SingleView sv = switch (newViewState.getEventType()){
		
		case OPEN_EDIT, OPEN_NEW ->{
				DetailView dv ;
				SingleView lastSv = this.viewHistory.get(viewHistory.size()-1);
				if(lastSv.getCurrentViewState().getContext().equals(newViewState.getContext()) &&
						lastSv.getCurrentViewState().getEventType().equals(newViewState.getEventType())) {
					dv = (DetailView) this.viewHistory.get(viewHistory.size()-1);
				}else {
					if(lastSv.getCurrentViewState().getContext().equals(newViewState.getContext()) && lastSv.getClass().equals(DetailView.class)) {
						this.viewHistory.remove(lastSv);
					}
					lastSv = this.viewHistory.get(viewHistory.size()-1);
					dv =  new DetailView(viewClazz, serviceProxy, newViewState);
					dv.setParentView(lastSv);
					dv.propertyChangeSupport.addPropertyChangeListener(this);
					this.viewHistory.add(dv);	
				}
				dv.populateForm(newViewState.getCurrentDisplayable());
				yield dv;
			}
		case  CLOSE ->{
			SingleView lastSv = this.viewHistory.get(viewHistory.size()-1);
			lastSv.propertyChangeSupport.removePropertyChangeListener(this);
			this.viewHistory.remove(lastSv);			
			GridView gridView = (GridView) lastSv.getParentView();
			gridView.getGrid().asSingleSelect().clear();
			gridView.getGrid().focus();
			
			yield null;
		}
		case DELETE ->{
			SingleView lastSv = this.viewHistory.get(viewHistory.size()-1);
			lastSv.propertyChangeSupport.removePropertyChangeListener(this);
			this.viewHistory.remove(lastSv);			
			GridView gridView = (GridView) lastSv.getParentView();
			gridView.getGrid().asSingleSelect().clear();
			gridView.getGrid().focus();
			gridView.getDisplayables().remove(oldViewState.getCurrentDisplayable());
			gridView.getFilteredDisplayables().remove(oldViewState.getCurrentDisplayable());
			gridView.refreshGrid();
			yield null;
		}
		case SAVE -> {
			DetailView dv ;
			dv = (DetailView) this.viewHistory.get(viewHistory.size()-1);
			GridView gridView = (GridView) dv.getParentView();;
			int indexOfSavedDisplayable = gridView.getDisplayables().indexOf(newViewState.getCurrentDisplayable()); 
			int indexOfSavedDisplayableFiltered = gridView.getFilteredDisplayables().indexOf(newViewState.getCurrentDisplayable()); 
			if(indexOfSavedDisplayable > -1) {
				gridView.getDisplayables().set(indexOfSavedDisplayable, newViewState.getCurrentDisplayable());
				if(indexOfSavedDisplayableFiltered != -1) {
					gridView.getFilteredDisplayables().set(indexOfSavedDisplayableFiltered, newViewState.getCurrentDisplayable());
				}
			}else {
				gridView.getDisplayables().add(0, newViewState.getCurrentDisplayable());
				gridView.getFilteredDisplayables().add(0, newViewState.getCurrentDisplayable());
			}
			gridView.refreshGrid();
			gridView.selectItem(newViewState.getCurrentDisplayable());
			yield dv;
		}
		
		default -> null;
		
		};
		
		if(sv == null) {
			this.splitLayout.getSecondaryComponent().setVisible(false);
		}else {
			this.splitLayout.addToSecondary(sv);	
		}
		
	}
}
