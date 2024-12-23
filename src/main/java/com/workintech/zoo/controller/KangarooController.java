package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {
    private final Map<Integer, Kangaroo> kangaroos;

    public KangarooController() {
        this.kangaroos =new HashMap<>();
    }

    @GetMapping
    public ResponseEntity<List<Kangaroo>> getAllKangaroos() {
        List<Kangaroo> kangarooList = new ArrayList<>(kangaroos.values());
        return ResponseEntity.ok(kangarooList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Kangaroo> getKangarooById(@PathVariable Integer id) {
        Kangaroo kangaroo = kangaroos.get(id);
        if (kangaroo == null) {
            throw new ZooException("Kangaroo with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(kangaroo);
    }
    @PostMapping
    public ResponseEntity<Kangaroo> addKangaroo(@RequestBody Kangaroo kangaroo) {
        validateKangaroo(kangaroo);
        kangaroos.put(kangaroo.getId(), kangaroo);
        return ResponseEntity.ok(kangaroo);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Kangaroo> updateKangaroo(@PathVariable Integer id, @RequestBody Kangaroo kangaroo) {
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }
        kangaroos.put(id, kangaroo);
        return ResponseEntity.ok(kangaroo);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Kangaroo> deleteKangaroo(@PathVariable Integer id) {
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }
        Kangaroo removedKangaroo = kangaroos.remove(id);
        return ResponseEntity.ok(removedKangaroo);
    }

    private void validateKangaroo(Kangaroo kangaroo) {
        if (kangaroo.getId() == null || kangaroo.getId() <= 0) {
            throw new ZooException("ID must be a positive integer", HttpStatus.BAD_REQUEST);
        }
        if (kangaroo.getName() == null || kangaroo.getName().isEmpty()) {
            throw new ZooException("Name cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        if (kangaroo.getHeight() == null || kangaroo.getHeight() <= 0) {
            throw new ZooException("Height must be a positive number", HttpStatus.BAD_REQUEST);
        }
        if (kangaroo.getWeight() == null || kangaroo.getWeight() <= 0) {
            throw new ZooException("Weight must be a positive number", HttpStatus.BAD_REQUEST);
        }
        if (kangaroo.getGender() == null || kangaroo.getGender().isEmpty()) {
            throw new ZooException("Gender cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
    }
}
