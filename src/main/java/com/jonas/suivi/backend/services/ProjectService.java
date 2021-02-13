package com.jonas.suivi.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.jonas.suivi.backend.model.Displayable;
import com.jonas.suivi.backend.model.impl.Project;
import com.jonas.suivi.backend.repository.ProjectRepository;

@Service("Project")
public class ProjectService implements DisplayableService {

	
	@Autowired
	private ProjectRepository projectRepo;
	
	public List<Project> getAll(){
		return (List<Project>) projectRepo.findAll();
	}

	@Override
	public <T extends Displayable> void update(T d) {
		projectRepo.save((Project) d);
	}

	@Override
	public <T extends Displayable> void delete(T d) {
		projectRepo.delete((Project) d);
	}

	@Override
	public <T extends Displayable> JpaRepository<T, Long> getRepo() {
		// TODO Auto-generated method stub
		return (JpaRepository<T, Long>) projectRepo;
	}

	@Override
	public <T extends Displayable> Optional<T> getByIdentifier(String d) {
		
		Project p = new Project();
		p.setName(d);
		
		Example<Project> ex = Example.of(p);
		
		return (Optional<T>) projectRepo.findOne(ex);
		

	}
	
//	protected GenericRepository<Project> getRepository() {
//			return projectRepo;
//	}

}
