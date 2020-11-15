package com.github.amaurinorato.products.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.github.amaurinorato.products.domain.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

}
