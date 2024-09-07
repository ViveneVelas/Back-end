package com.velas.vivene.inventory.manager.service;

import com.velas.vivene.inventory.manager.datastructure.HashTable;
import com.velas.vivene.inventory.manager.datastructure.Node;
import org.springframework.stereotype.Service;

@Service
public class HashTableService {
    private HashTable<String> hashTable;

    public HashTableService() {
        this.hashTable = new HashTable<>(10);
    }

    public void addName(String name) {
        hashTable.insert(name);
    }

    public String searchName(String name) {
        Node<String> result = hashTable.search(name);
        return (result != null) ? "Found: " + result.getName() : "Not Found";
    }
}
