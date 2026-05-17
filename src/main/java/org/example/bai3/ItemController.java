package org.example.bai3;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    private List<Item> inventory = new ArrayList<>();
    private AtomicLong nextId = new AtomicLong(1);

    private ResponseEntity<Item> findById(Long id) {
        return inventory.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .map(i -> ResponseEntity.ok(i))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public List<Item> getAllItems() {
        return inventory;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable Long id) {
        return findById(id);
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        item.setId(nextId.getAndIncrement());
        inventory.add(item);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(
            @PathVariable Long id,
            @RequestBody Item itemUpdates) {

        ResponseEntity<Item> response = findById(id);
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Item existing = response.getBody();
        existing.setName(itemUpdates.getName());
        existing.setQuantity(itemUpdates.getQuantity());
        return ResponseEntity.ok(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        boolean removed = inventory.removeIf(i -> i.getId().equals(id));
        if (removed) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
