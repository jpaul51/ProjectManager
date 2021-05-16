package com.jonas.suivi.views.main;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.jonas.suivi.UserContextFactory;
import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.backend.services.SearchInterface;
import com.jonas.suivi.backend.services.ServiceProxy;
import com.jonas.suivi.views.components.AbstractSuperDisplayableComponent;
import com.jonas.suivi.views.descriptors.InvalidActionDescriptorException;
import com.jonas.suivi.views.descriptors.InvalidFieldDescriptorException;
import com.jonas.suivi.views.model.Application;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.Shortcuts;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.Command;

@Route(value = "app", layout = MainView.class)
@CssImport("./styles/views/main/main-view.css")
public class SplitView extends AbstractView implements HasUrlParameter<String>, PropertyChangeListener {

	@Autowired
	UserContextFactory userContextFactory;

	@Autowired
	ServiceProxy serviceProxy;

	SearchInterface displayableService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 222222L;

	EAPP_CONTEXT appCtx = EAPP_CONTEXT.ADD;

	SplitLayout splitLayout;

	List<ViewHistory> viewHistory = new ArrayList<>();

	public SplitView() throws InvalidFieldDescriptorException, InvalidActionDescriptorException {
		super();

		Command c = new Command() {

			@Override
			public void execute() {
				switchSplit(true);
			}
		};
		Command cDetail = new Command() {

			@Override
			public void execute() {
				switchSplit(false);
			}
		};

		Shortcuts.addShortcutListener(this, c, Key.KEY_A, KeyModifier.ALT).listenOn(this);
		Shortcuts.addShortcutListener(this, cDetail, Key.KEY_E, KeyModifier.ALT).listenOn(this);

	}

	private void generateView() throws InvalidFieldDescriptorException, InvalidActionDescriptorException {

		Class<?> cl = UserContextFactory.getCurrentUserContext().getCurrentClass();
		SingleView gridView = new GridView(cl, serviceProxy);

		if (cl == null) {
			UI.getCurrent().navigate("");
			return;
		}
		ViewHistory vh = new ViewHistory();
		vh.setCurrent(gridView);
		vh.setRight(false);
		vh.setParent(null);
		viewHistory.add(vh);

		UI.getCurrent().getPage().getHistory().pushState(null, "app");
		gridView.propertyChangeSupport.addPropertyChangeListener(this);

		splitLayout = new SplitLayout();
		splitLayout.addToPrimary(gridView);
		splitLayout.setSizeFull();
		this.add(splitLayout);

		viewHistory.forEach(s -> s.getCurrent().reload());

	}

	private void switchSplit(boolean left) {
		if (left)
			this.splitLayout.setSplitterPosition(100);
		else
			this.splitLayout.setSplitterPosition(0);

	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		reloadView();

	}

	@PostConstruct
	public void reloadView() {
		this.removeAll();
		try {
			viewHistory = new ArrayList<>();
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

		if (evt.getPropertyName().equals(ESplitViewEvents.VIEW_STATE.getValue())) {
			viewStateChanged(evt);
		} else {

			SingleView view = (SingleView) evt.getNewValue();
			Optional<ViewHistory> optVh = findViewHistoryOfSingleView(view);

			if (optVh.isPresent()) {
				ViewHistory vh = optVh.get();
				if (vh.isRight() && this.splitLayout.getSecondaryComponent().isVisible()) {
					this.splitLayout.setSplitterPosition(0);
				} else {
					this.splitLayout.setSplitterPosition(100);
				}
			}

		}

	}

	private void viewStateChanged(PropertyChangeEvent evt) {
		ViewState oldViewState = (ViewState) evt.getOldValue();
		ViewState newViewState = (ViewState) evt.getNewValue();

		Class<? extends Application> viewClazz = newViewState.getContext();
		ViewHistory vh;

		Optional<ViewHistory> latestVh = findViewHistoryOfSingleView(newViewState.getSender());

		if (latestVh.isPresent()) {
			vh = latestVh.get();
		} else {
			vh = findParentViewOfCtx(oldViewState, newViewState, getLastOpenedViewHistory(), null);
		}

//		ViewHistory lastViewHistory = new ViewHistory(vh);// findParentViewOfCtx(oldViewState, newViewState, vh, null);
		ViewHistory returnedVh = switch (newViewState.getEventType()) {

		case OPEN_EDIT -> {

			DetailView dv = new DetailView(viewClazz, serviceProxy, newViewState);
			ViewHistory newvh = new ViewHistory();
			newvh.setParent(vh);
			newvh.setCurrent(dv);
			newvh.setRight(vh.isGridView() && newViewState.isSenderSingleView());

			if (vh.isRight()) {
				this.splitLayout.addToSecondary(dv);
			} else {
				if (vh.getCurrent().getCtx().equals(dv.getCtx()) || (!vh.isGridView() && ((DetailView) vh.getCurrent())
						.findGridComponentOfContext(vh.getCurrent().getCtx()) != null)) {
					this.splitLayout.addToSecondary(dv);
				} else {
					this.splitLayout.addToPrimary(dv);
				}
			}
			this.splitLayout.getSecondaryComponent().setVisible(true);

			newvh.getCurrent().propertyChangeSupport.addPropertyChangeListener(this);

			if (!viewHistory.stream().anyMatch(vvh -> {// Si vh deja dans history en mode detail (ajout oumodif) on
														// n'ajoute pas

				return (vvh.getClass().equals(newvh.getClass()) && vvh.isGridView() == newvh.isGridView()
						&& newViewState.getCurrentDisplayable()
								.equals(((DetailView) vvh.getCurrent()).getCurrentDisplayable()));
			})) {
				this.viewHistory.add(newvh);
				vh.getChildren().add(newvh);

			}

//			}
			((DetailView) newvh.getCurrent()).populateForm(newViewState.getCurrentDisplayable());

			if (newvh.getParent().isGridView()) {
				((GridView) newvh.getParent().getCurrent()).currentDisplayable = newViewState.getCurrentDisplayable();
			}

			yield null;
		}
		case OPEN_NEW -> {

			DetailView dv = new DetailView(viewClazz, serviceProxy, newViewState);
			ViewHistory newvh = new ViewHistory();
			newvh.setParent(vh);
			newvh.setCurrent(dv);
			newvh.setRight(vh.isGridView() && newViewState.isSenderSingleView());

			// Allows multiple editing but only adding once is permited
			if (!vh.getChildren().isEmpty()) {
				Optional<ViewHistory> optNewHistory = vh.getChildren().stream().filter(filteredvh -> {
					return filteredvh.getCurrent().getCurrentViewState().getEventType().equals(EViewEventType.OPEN_NEW);
				}).findAny();
				if (optNewHistory.isPresent()) {
					vh.getChildren().remove(optNewHistory.get());
				}

			}
			vh.getChildren().add(newvh);

			if (newvh.isRight()) {
				this.splitLayout.addToSecondary(dv);
			} else {
				this.splitLayout.addToPrimary(dv);
			}
			this.splitLayout.getSecondaryComponent().setVisible(true);

			newvh.getCurrent().propertyChangeSupport.addPropertyChangeListener(this);
			this.viewHistory.add(newvh);

			((DetailView) newvh.getCurrent()).populateForm(newViewState.getCurrentDisplayable());

			yield newvh;
		}
		case CLOSE -> {
//			SingleView sender = newViewState.sender;
			ViewHistory nextViewToOpen = closeView(oldViewState, newViewState, false);

			if (nextViewToOpen.isGridView()) {
//				reloadSplitOnClose(nextViewToOpen);
				this.splitLayout.getSecondaryComponent().setVisible(false);
				((GridView) nextViewToOpen.getCurrent()).selectItem(null);
			} else {
				ViewHistory parentView = findParentViewOfCtx(oldViewState, newViewState, nextViewToOpen, null);
				if (nextViewToOpen.getChildren().isEmpty()
						&& !vh.getCurrent().getCtx().equals(nextViewToOpen.getCurrent().getCtx())) {

					if (oldViewState.isSenderSingleView()) {
						((DetailView) parentView.getCurrent()).findGridComponentOfContext(vh.getCurrent().getCtx())
								.selectItem(null);
					}
					this.splitLayout.addToPrimary(
							findLastGridLeftViewOfCtx(nextViewToOpen, nextViewToOpen.getCurrent().getCtx())
									.getCurrent());
				} else {
					ViewHistory viewToOpen = parentView.getChildren().get(parentView.getChildren().size() - 1);
//					((DetailView)nextViewToOpen.getCurrent()).findGridComponentOfContext(sender.getCtx()).selectItem(((DetailView)viewToOpen.current.);
					this.splitLayout.addToSecondary(viewToOpen.getCurrent());
				}

//				 nextViewToOpen.getParent().
//				((GridView)parentView).selectItem(null);
			}

//			

			yield null;
		}
		case DELETE -> {
			ViewHistory lastViewHistoryClosed = closeView(oldViewState, newViewState, true);

			if (lastViewHistoryClosed.getParent().isGridView()) {
				GridView gridView = (GridView) lastViewHistoryClosed.getParent().getCurrent();
				gridView.getDisplayables().remove(oldViewState.getCurrentDisplayable());
				gridView.refreshGrid();
			} else {
				ViewHistory parentViewHistory = lastViewHistoryClosed.getParent();
				((DetailView) parentViewHistory.getCurrent()).refreshGridComponentOfContext(newViewState, true);
			}

//			reloadSplitOnClose(lastViewHistoryClosed);

			yield null;
		}
		case SAVE -> {
//			ViewHistory vh;
			vh = findLastViewOfCtx();
//			ViewHistory parentViewHistory = vh;
//			

			ViewHistory parentView = vh.getParent();

			if (oldViewState.isSenderSingleView()) {

				if (parentView.isGridView()) {
					GridView gridView = (GridView) vh.getParent().getCurrent();
					updateOrAddGridView(newViewState, gridView);

				} else {
					ViewHistory parentViewHistory = vh.getParent();
					((DetailView) parentViewHistory.getCurrent()).refreshGridComponentOfContext(newViewState, false);
					ViewHistory initialVh = findLastGridLeftViewOfCtx(vh, vh.getCurrent().getCtx());
					if (initialVh != null) {
						updateOrAddGridView(newViewState, (GridView) initialVh.getCurrent());
					}

				}
				((DetailView) vh.getCurrent()).refreshGridComponentOfContext(newViewState, false);
			} else {
				AbstractSuperDisplayableComponent superComponent = (AbstractSuperDisplayableComponent) oldViewState
						.getSender();
				if (oldViewState.getEventType().equals(EViewEventType.OPEN_EDIT)) {
					superComponent.editValue(newViewState.getCurrentDisplayable());

				}else {
					superComponent.addValue(newViewState.getCurrentDisplayable());

				}
			}
			if (parentView.getParent() == null) {// no persistence until main entity saved
				((DetailView) vh.getCurrent()).persitDisplayable();
			} else {
				((DetailView) vh.getParent().getCurrent()).getChildrenChangedToPersist()
						.add(newViewState.getCurrentDisplayable());
				ViewHistory lastVh = closeView(oldViewState, newViewState, false);
//				reloadSplitOnClose(lastVh);
			}

			((DetailView) vh.getCurrent()).setLabel();
			yield vh;
		}

		default -> null;

		};
	}

	private ViewHistory findLastViewOfCtx() {

		return this.viewHistory.get(viewHistory.size() - 1);
	}

	/**
	 * find parent detail view of ctx (Initial gridview or detailView containing
	 * gridView)
	 * 
	 * @param old
	 * @param newState
	 * @param currentViewHistory
	 * @param parentViewHistory
	 * @return
	 */
	private ViewHistory findParentViewOfCtx(ViewState old, ViewState newState, ViewHistory currentViewHistory,
			ViewHistory parentViewHistory) {

		ViewHistory viewToInspect = parentViewHistory == null ? currentViewHistory : parentViewHistory;

		if ((viewToInspect.getParent() != null)
				&& ((!old.getContext().equals(newState.getContext()) && !viewToInspect.equals(currentViewHistory))
						|| (!viewToInspect.isGridView() && ((DetailView) viewToInspect.getCurrent())
								.findGridComponentOfContext(newState.getContext()) == null))) {
			return findParentViewOfCtx(old, newState, currentViewHistory, viewToInspect.getParent());
		} else {
			return viewToInspect;
		}

	}

	private Optional<ViewHistory> findViewHistoryOfSingleView(Component view) {
		return this.viewHistory.stream().filter(vh -> vh.getCurrent() == view).findFirst();

	}

	private void removeLastOpenedHistory() {
		viewHistory.remove(viewHistory.size() - 1);
	}

	private ViewHistory getLastOpenedViewHistory() {
		return this.viewHistory.get(viewHistory.size() - 1);
	}

	private void updateOrAddGridView(ViewState newViewState, GridView gridView) {
		int indexOfSavedDisplayable = gridView.getDisplayables().indexOf(newViewState.getCurrentDisplayable());
//		int indexOfSavedDisplayableFiltered = gridView.getDisplayables()
//				.indexOf(newViewState.getCurrentDisplayable());
		if (indexOfSavedDisplayable > -1 || newViewState.getCurrentDisplayable().getId() == null) {
			List<Displayable> newList = new ArrayList<>(gridView.getDisplayables());

			int index = indexOfSavedDisplayable > -1 ? indexOfSavedDisplayable : 0;

			newList.set(index, newViewState.getCurrentDisplayable());
			gridView.getGrid().setItems(newList);

		}

//		gridView.refreshGrid();
		gridView.selectItem(newViewState.getCurrentDisplayable());
	}

	private ViewHistory findLastLeftView(ViewHistory fromView) {
//		this.viewHistory.stream().filter(vh -> vh.equals(fromView)).
		if (!fromView.getParent().isRight()) {
			return fromView.getParent();
		} else {
			return findLastLeftView(fromView.getParent());
		}

	}

	private ViewHistory findLastGridLeftViewOfCtx(ViewHistory fromView, Class<? extends Application> context) {
		if (fromView.getParent() != null) {
			if (!fromView.getParent().isRight() && fromView.getParent().isGridView()
					&& fromView.getParent().getCurrent().getCtx().equals(context)) {
				return fromView.getParent();
			} else {
				return findLastGridLeftViewOfCtx(fromView.getParent(), context);
			}
		} else {
			return null;
		}

	}

	private ViewHistory findLastViewOfCtx(ViewHistory fromView, Class<? extends Application> context) {
		if (fromView.getParent() != null) {
			if ((!fromView.getParent().isGridView() && fromView.getParent().getChildren().stream()
					.anyMatch(vh -> ((DetailView) vh.getCurrent()).findGridComponentOfContext(context) != null))
					|| (fromView.getParent().isGridView() && !fromView.getParent().isRight())) {
				return fromView.getParent();
			} else {
				return findLastGridLeftViewOfCtx(fromView.getParent(), context);
			}
		} else {
			return null;
		}

	}

//	public ViewHistory findViewHistoryWithOldState(ViewState oldState) {
//		
//		Optional<ViewHistory> optClosed = this.viewHistory.stream().filter(vh ->{
//			
//			return oldState.getSender() == vh.getCurrent();// same item not equals
//			
//		}).findAny();
//		
//		return null;
//	}

	private ViewHistory closeView(ViewState oldViewState, ViewState newViewState, boolean delete) {
//		ViewHistory closedVh = findViewHistoryWithOldState(oldViewState);
		ViewHistory parentViewHistory = findParentViewOfCtx(oldViewState, newViewState, findLastViewOfCtx(), null);// todo:
																													// beurk

		Optional<ViewHistory> optClosedViewHistory = parentViewHistory.getChildren().stream().filter(vhChild -> {
			return ((DetailView) vhChild.getCurrent()).getCurrentDisplayable()
					.equals(newViewState.getCurrentDisplayable());
		}).findFirst();

		if (optClosedViewHistory.isPresent()) {
			ViewHistory closedViewHistory = optClosedViewHistory.get();
			closedViewHistory.getCurrent().propertyChangeSupport.removePropertyChangeListener(this);
			this.viewHistory.remove(closedViewHistory);
			parentViewHistory.getChildren().remove(closedViewHistory);
		}

		if (!parentViewHistory.getChildren().isEmpty()) {
			return parentViewHistory.getChildren().get(parentViewHistory.getChildren().size() - 1);
		} else {
			return parentViewHistory;
		}

//		if (lastViewHistory.isGridView()) {
//			GridView gridView = (GridView) lastViewHistory.getParent().getCurrent();
//			gridView.getGrid().asSingleSelect().clear();
//			gridView.getGrid().focus();
//
//		} else {
//			ViewHistory parentViewHistory = lastViewHistory.getParent();
//			if (parentViewHistory.isGridView()) {
//				GridView gridView = (GridView) lastViewHistory.getParent().getCurrent();
//				gridView.getGrid().asSingleSelect().clear();
//				gridView.getGrid().focus();
//			} else {
//				((DetailView) parentViewHistory.getCurrent()).refreshGridComponentOfContext(newViewState, delete);
//
//			}
//		}

//		return parentViewHistory;

	}

}
