package com.gstlite.mobilestore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gstlite.mobilestore.entites.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}