package com.jonas.suivi.backend.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.backend.model.impl.SimpleDisplayable;
import com.jonas.suivi.backend.model.impl.Translation;
import com.jonas.suivi.backend.repository.TranslationRepository;
import com.jonas.suivi.backend.util.TranslationUtils;

@Service("Translation")
public class TranslationService implements DisplayableService{

	
	@Autowired
	TranslationRepository translationRepo;
	


	public List<Translation> getAll(){
		return new ArrayList<Translation>((Collection<Translation>) translationRepo.findAll());
	}



	@Override
	public <T extends Displayable> void update(T d) {
		translationRepo.save((Translation)d);
	}



	@Override
	public <T extends Displayable> void delete(T d) {
		translationRepo.delete((Translation) d);		
		
	}
	
	@Override
	public void reloadContext() {
		DisplayableService.super.reloadContext();
		TranslationUtils.reload();
	}



	@Override
	public <T extends Displayable> JpaRepository<T, Long> getRepo() {
		// TODO Auto-generated method stub
		return (JpaRepository<T, Long>) translationRepo;
	}



	@Override
	public <T extends Displayable> Optional<T> getByIdentifier(String d) {
		
		
		Translation t = new Translation();
		SimpleDisplayable sd = new SimpleDisplayable(d);
		t.setKey(sd);
		
		return (Optional<T>) translationRepo.findOne(Example.of(t));
		
	}
	
	
	
}
