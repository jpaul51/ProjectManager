package com.jonas.suivi.views.components;

import com.jonas.suivi.views.components.container.PushyView;
import com.jonas.suivi.views.model.FieldDetail;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.shared.Registration;
import com.wontlost.ckeditor.VaadinCKEditor;

public class RichTextEditorComponent extends AbstractSimpleSuperComponent<String>
{
	
//	TextField data;
	VaadinCKEditor ckEditor; 
	PushyView p ;
	
	public RichTextEditorComponent(FieldDetail field) {
		
		p = new PushyView(field);
		p.addClassName("richEditorConainer");
		
//		ckEditor = RichTextEditorBuilder.richTextEditor("");
//		p.add(ckEditor);
//		this.add(p);
//	
//		ckEditor.addValueChangeListener(v ->{
//			data.setValue(ckEditor.getValue());
//		});
		
	}
	

	

	
	@Override
	public boolean isReadOnly() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isRequiredIndicatorVisible() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public FieldDetail getFieldDetail() {
		// TODO Auto-generated method stub
		return field;
	}

	@Override
	public void setFieldDetail(FieldDetail field) {
		this.field = field;
	}

	@Override
	public Component getComponent() {
		// TODO Auto-generated method stub
		return p;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return p.getValue();
	}

	@Override
	public Registration addValueChangeListener(ValueChangeListener<? super ValueChangeEvent<String>> listener) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		// TODO Auto-generated method stub
//		p.setReadOnly(true);
	}

	@Override
	public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValue(String value) {
		 p.setValue(value);
		
	}

	@Override
	public void initialize() {
//		p.setValue(getValue());
		
	}


	
	
}
