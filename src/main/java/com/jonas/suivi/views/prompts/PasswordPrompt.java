package com.jonas.suivi.views.prompts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jonas.suivi.backend.services.UserAccountService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.function.SerializableBiPredicate;

@Component
public class PasswordPrompt extends Dialog {
	
	
	
	public PasswordPrompt(@Autowired
			UserAccountService userAccountService) {

		
		this.setCloseOnOutsideClick(false);
		
		
		VerticalLayout dialogLayout = new VerticalLayout();
		
		H2 dialogTitle = new H2("Reset password");
		
		Div formDiv = new Div();
		
		
		PasswordField txtNewPassword = new PasswordField();
		PasswordField txtConfirmPassword = new PasswordField();
		
		
		
		
		formDiv.add(txtNewPassword, txtConfirmPassword);
		dialogLayout.add(dialogTitle, formDiv);
		
		Button btnSubmit = new Button("Submit");
		dialogLayout.add(btnSubmit);
		
		btnSubmit.addClickListener(c -> {
			
			if(txtNewPassword.getValue().equals(txtConfirmPassword.getValue())) {
				userAccountService.updatePassword(txtConfirmPassword.getValue());
				PasswordPrompt.this.close();

			}else{
				txtConfirmPassword.setInvalid(true);
			}
			
		});
		
		
		this.add(dialogLayout);
		
//		passwordForm.
		
//		passwordForm.add(txtNewPassword,txtConfirmPassword );
		
		
	}

	private SerializableBiPredicate<String,String> validate() {

		SerializableBiPredicate predicate = new SerializableBiPredicate<String,String>() {

			@Override
			public boolean test(String arg0, String arg1) {
				// TODO Auto-generated method stub
				return arg0.equals(arg1);
			}
		};
		return predicate;
//		return predicate.test(txtNewPassword.getValue(), txtConfirmPassword.getValue());
//		return txtNewPassword.getValue().equals(txtConfirmPassword.getValue());
		
	}

}
