package com.jonas.suivi.views.components;


import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.textfield.TextArea;

@CssImport("./main.css")
public  class RichTextEditorBuilder extends TextArea  {
	
	

//	public static VaadinCKEditor richTextEditor(String data) {
//		
//		VaadinCKEditor classicEditor = new VaadinCKEditorBuilder().with(builder -> {
//			builder.editorData = "<p>This is a classic editor sample.</p>";
//			builder.editorType = EditorType.CLASSIC;
//			builder.uiLanguage = Language.fr;
////			builder.config = new Config();
////			builder.config.setUILanguage(com.wontlost.ckeditor.Constants.Language.fr);
////			
////			builder.config.switchResizeEnabled(true);
////		    builder.uiLanguage = Language.fr; //language defaulted to 'en'
//		    builder.theme = ThemeType.DARK;
////		    builder.toolbar = builder.toolbar=new Toolbar[]{Toolbar.fontFamily, Toolbar.fontSize, Toolbar.fontColor,
////		    		Toolbar.codeBlock, Toolbar.insertTable};
//		}).createVaadinCKEditor();
////		classicEditor.getElement().setProperty("config", "");
//		classicEditor.setValue(data);
//		
////		CKEditorConfig config = new CKEditorConfig();
////		config.useCompactTags();
////		config.disableElementsPath();
////		config.setResizeDir(CKEditorConfig.RESIZE_DIR.HORIZONTAL);
////		config.disableSpellChecker();
////		config.setWidth("100%");
//		
//		classicEditor.setValue(data);
//		
//		return classicEditor;
//	}

//	public static VaadinCKEditor richTextEditor(String data, boolean readOnly) {
//		VaadinCKEditor richEditor =  richTextEditor(data);
//		richEditor.setReadOnly(readOnly);
//		richEditor.setEnabled(!readOnly);
////		richEditor.set
//		richEditor.getElement().getStyle().set("overflow-x", "hidden");
//		richEditor.setMaxWidth("90%");
//		return richEditor;
//	}
	
}
