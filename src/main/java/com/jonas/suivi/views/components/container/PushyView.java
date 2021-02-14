package com.jonas.suivi.views.components.container;

import com.jonas.suivi.views.model.FieldDetail;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
//import com.wontlost.ckeditor.VaadinCKEditor;


public class PushyView extends VerticalLayout {

	FieldDetail field;
	public static TextArea c;
	private static String value = "";
	private UI ui;

	public PushyView(FieldDetail field) {
		this.field = field;
	}

	public void perform() {
		if (ui != null) {
			getUI().ifPresent(ui -> ui.access(() -> {

				FeederThread thread = new FeederThread(ui, this);
				thread.start();

			}));
		}

//		if(thread != null && !thread.isInterrupted()) {
//		}else {
//	        thread = new FeederThread(ui, this);

//		}

	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		Div placeHolder = new Div();
		placeHolder.setHeight("340px");
		add(placeHolder);
		ui = attachEvent.getUI();
//		thread = new FeederThread(attachEvent.getUI(), this);
	}

	@Override
	protected void onDetach(DetachEvent detachEvent) {
		// Cleanup
//		thread.interrupt();
//		thread = new FeederThread(detachEvent.getUI(), this);
//        thread = null;
	}

	private static class FeederThread extends Thread {
		private final UI ui;
		private final PushyView view;

		private int count = 0;

		public FeederThread(UI ui, PushyView view) {
			this.ui = ui;
			this.view = view;
		}

		@Override
		public void run() {
			// Update the data for a while
			if (c == null) {
//				c = RichTextEditorBuilder.richTextEditor(value);
				c = new TextArea(value);
			}

			c.getElement().getStyle().clear();
			// Inform that we are done
			ui.access(() -> {
				if (c.getParent().isPresent()) {
					((HasComponents) c.getParent().get()).remove(c);
					;
				}
				view.removeAll();
				c.setValue(value);

				view.add(c);
			});
		}
	}

	public static void setValue(String value) {
		PushyView.value = value;
//		PushyView.c.setValue(value);
	}
}