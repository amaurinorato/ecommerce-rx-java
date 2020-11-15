package com.github.amaurinorato.products.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.amaurinorato.products.domain.Category;
import com.github.amaurinorato.products.service.CategoryService;

import rx.Observable;

@RestController()
@RequestMapping("/v1/categories")
public class CategoryController {

	@Autowired
	private CategoryService service;
	
	@GetMapping
	public Observable<ResponseEntity<List<Category>>> findAll() {
		return service
				.findAllActive()
				.map(result -> ResponseEntity.ok(result))
				.onErrorReturn(ex -> {
					return ResponseEntity.ok(new ArrayList<>());
				});
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Observable<Category> create(@RequestBody Category category) {
		return service.save(category);
	}
	
	@DeleteMapping("/{id}")
	public Observable<ResponseEntity<?>> delete(@PathVariable Long id) {
		return service.delete(id)
				.map(result -> {
					return ResponseEntity.ok(result);
				});
	}
	
	@GetMapping("/{id}")
	public Observable<ResponseEntity<?>> findById(@PathVariable Long id) {
		return service.findById(id)
				.map(result -> {
					return ResponseEntity.ok(result);
				});
	}
	
}
