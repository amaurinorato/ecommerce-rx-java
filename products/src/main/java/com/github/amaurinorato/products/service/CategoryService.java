package com.github.amaurinorato.products.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.amaurinorato.products.domain.Category;
import com.github.amaurinorato.products.domain.CategoryNotFoundException;
import com.github.amaurinorato.products.repository.CategoryRepository;

import rx.Observable;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repo;

	public Observable<List<Category>> findAllActive() {
		return Observable.defer(() -> 
			Observable
				.just(repo.findAll())
				.map(rawResult -> StreamSupport.stream(rawResult.spliterator(), false).filter(c -> c.getActive()).collect(Collectors.toList()))
		);
	}
	
	public Observable<Category> findById(Long id) {
		return Observable.defer(() -> {
			Optional<Category> opt = repo.findById(id);
			if (opt.isPresent()) {
				return Observable.just(opt.get());
			}
			return Observable.error(new CategoryNotFoundException());
		});
	}
	
	public Observable<Category> save(Category category) {
		return Observable.defer(() -> Observable.just(repo.save(category))); 
	}
	
	public Observable<Category> delete(Long id) {
		return Observable.defer(() ->{
			Optional<Category> opt = repo.findById(id);
			if (opt.isPresent()) {
				Category category = opt.get();
				category.inactive();
				return Observable.just(repo.save(category));
			}
			return Observable.error(new CategoryNotFoundException());
		});
	}
}
