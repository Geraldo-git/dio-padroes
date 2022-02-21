package com.digitalinnovation.crud.repositories;

import com.digitalinnovation.crud.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


}
