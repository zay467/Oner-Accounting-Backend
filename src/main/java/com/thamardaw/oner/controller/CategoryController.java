package com.thamardaw.oner.controller;

import com.thamardaw.oner.entity.Category;
import com.thamardaw.oner.entity.request.CategoryRequest;
import com.thamardaw.oner.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("")
    public ResponseEntity<?> get(){
        List<Category> categoryList = categoryRepository.findAll(Sort.by("id").ascending());
        return ResponseEntity.ok(categoryList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id){
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id,@RequestBody CategoryRequest request){
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Category category = categoryOptional.get();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        categoryRepository.save(category);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        categoryRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CategoryRequest request){
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        category.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        categoryRepository.save(category);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
