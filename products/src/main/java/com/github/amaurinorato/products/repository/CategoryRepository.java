package com.github.amaurinorato.products.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.github.amaurinorato.products.domain.Category;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

}
