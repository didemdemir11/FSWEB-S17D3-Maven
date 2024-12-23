package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/koalas")
public class KoalaController {
    private final Map<Integer, Koala> koalas;

    public KoalaController() {
        this.koalas = new HashMap<>();
    }
    @GetMapping
    public ResponseEntity<List<Koala>> getAllKoalas() {
        List<Koala> koalaList = new ArrayList<>(koalas.values());
        return ResponseEntity.ok(koalaList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Koala> getKoalaById(@PathVariable Integer id) {
        Koala koala = koalas.get(id);
        if (koala == null) {
            throw new ZooException("Koala with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(koala);
    }
    @PostMapping
    public ResponseEntity<Koala> addKoala(@RequestBody Koala koala) {
        koalas.put(koala.getId(), koala);
        return ResponseEntity.ok(koala);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Koala> updateKoala(@PathVariable Integer id, @RequestBody Koala koala) {
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }
        koalas.put(id, koala);
        return ResponseEntity.ok(koala);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKoala(@PathVariable Integer id) {
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }
        koalas.remove(id);
        return ResponseEntity.ok().build();
    }
}
