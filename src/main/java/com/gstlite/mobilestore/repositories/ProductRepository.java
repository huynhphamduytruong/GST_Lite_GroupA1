package com.gstlite.mobilestore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gstlite.mobilestore.entites.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}