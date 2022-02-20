package com.digitalinnovation.crud.services;

import com.digitalinnovation.crud.dto.CategoryDTO;
import com.digitalinnovation.crud.entities.Category;
import com.digitalinnovation.crud.repositories.CategoryRepository;
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
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> list = categoryRepository.findAll();
        return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> obj = categoryRepository.findById(id);
        Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Registro não encontrado!"));
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        Category entity = new Category();
        entity.setNome(dto.getNome());
        entity = categoryRepository.save(entity);
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(CategoryDTO dto, Long id) {
        Category entity = categoryRepository.getOne(id);
        entity.setNome(dto.getNome());
        entity = categoryRepository.save(entity);
        return new CategoryDTO(entity);
    }

    public void delete(Long id) {
        try {
            categoryRepository.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(" Id não encontrado!");
        }catch(DataIntegrityViolationException e){
                throw new DatabaseException("Violação de integridade!");
        }
    }
}
