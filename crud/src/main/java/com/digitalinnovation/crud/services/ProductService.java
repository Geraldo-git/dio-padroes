package com.digitalinnovation.crud.services;

import com.digitalinnovation.crud.dto.CategoryDTO;
import com.digitalinnovation.crud.dto.ProductDTO;
import com.digitalinnovation.crud.entities.Category;
import com.digitalinnovation.crud.entities.Product;
import com.digitalinnovation.crud.repositories.CategoryRepository;
import com.digitalinnovation.crud.repositories.ProductRepository;
import com.digitalinnovation.crud.services.exceptions.DatabaseException;
import com.digitalinnovation.crud.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        List<Product> list = productRepository.findAll();
        return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> obj = productRepository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Registro não encontrado!"));
        return new ProductDTO(entity, entity.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        copyDtoToEntity(dto, entity);
        entity = productRepository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(ProductDTO dto, Long id) {
        Product entity = productRepository.getOne(id);
        copyDtoToEntity(dto, entity);
        entity = productRepository.save(entity);
        return new ProductDTO(entity);
    }

    public void delete(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(" Id não encontrado!");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Violação de integridade!");
        }
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setDate(dto.getDate());
        entity.setImgUrl(dto.getImgUrl());
        entity.setPrice(dto.getPrice());

        entity.getCategories().clear();
        for (CategoryDTO catDto : dto.getCategories()){
            Category category = categoryRepository.getOne(catDto.getId());
            entity.getCategories().add(category);
        }
    }
}
