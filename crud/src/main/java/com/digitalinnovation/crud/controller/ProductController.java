package com.digitalinnovation.crud.controller;

import com.digitalinnovation.crud.dto.ProductDTO;
import com.digitalinnovation.crud.services.ProductService;
import com.digitalinnovation.crud.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {
        List<ProductDTO> list = productService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO dto = productService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto) {
        dto = productService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO dto, @PathVariable Long id) {
        try {
            dto = productService.update(dto, id);
            return ResponseEntity.ok().body(dto);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id não encontrado " + id);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}























































