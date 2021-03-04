package com.jonas.suivi.views.descriptors;

import java.util.Arrays;

import com.jonas.suivi.backend.model.impl.UserAccount;
import com.jonas.suivi.backend.util.TranslationUtils;
import com.jonas.suivi.views.model.Action;
import com.jonas.suivi.views.model.ActionType;
import com.jonas.suivi.views.model.Application;
import com.jonas.suivi.views.model.Bloc;
import com.jonas.suivi.views.model.Detail;
import com.jonas.suivi.views.model.Detail.DetailType;
import com.jonas.suivi.views.model.DetailLayoutManager;
import com.jonas.suivi.views.model.FieldDetail;
import com.jonas.suivi.views.model.Input;
import com.jonas.suivi.views.model.Line;
import com.jonas.suivi.views.model.TableLayoutManager;

@MainEntity(UserAccount.class)
public class UserAccountDescriptor extends Application{

	
	public UserAccountDescriptor() {
		super();
		this.setAppLabelKey(TranslationUtils.translate(EAppTranslation.APP_LABEL_USER_ACCOUNT.name()));
		
		FieldDetail loginField = new FieldDetail();
		loginField.setType(Input.TEXT_INPUT);
		loginField.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_LOGIN.name());
		loginField.setName("login");
		
		FieldDetail passwordField = new FieldDetail();
		passwordField.setType(Input.TEXT_INPUT);
		passwordField.setValueHidden(true);
		passwordField.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_PASSWORD.name());
		passwordField.setName("password");
//		passwordField.setReadOnly(true);
		
		
		FieldDetail ckResetPassword = new FieldDetail();
		ckResetPassword.setType(Input.CHECK_INPUT);
		ckResetPassword.setTranslationKey(EAppFieldsTranslation.APP_FIELDS_RESET_PASSWORD.name());
		ckResetPassword.setName("resetPassword");

		
		this.setDlManager(DetailLayoutManager.createSimpleDetail(loginField, ckResetPassword));
		
		
		Detail newDetail = new Detail();
		newDetail.setDetailName("NEW");
		Line l = new Line(loginField, passwordField, ckResetPassword);
//		l.getFields().addAll(Arrays.asList(loginField, passwordField, ckResetPassword));
		
		
		Bloc b = new Bloc();
		b.setLines(Arrays.asList(l));
		newDetail.setBlocs(Arrays.asList(b));
		newDetail.setDetailType(DetailType.NEW);
		this.getDlManager().getDetails().add(newDetail);
//		this.getDlManager().getDetails().add(arg0)
		
		Action onSubmit = new Action();
		onSubmit.setActionType(ActionType.SUBMIT);
		
		this.addAction(onSubmit);
		
		TableLayoutManager tbl = new TableLayoutManager();
		tbl.getDefaultResultView().getColumns().addAll(Arrays.asList(loginField, ckResetPassword));
		tbl.getDefaultResultView().getQuickSearchList().addAll(Arrays.asList(loginField, ckResetPassword));
		
		
		
		
		
	}

	
	
	
}
