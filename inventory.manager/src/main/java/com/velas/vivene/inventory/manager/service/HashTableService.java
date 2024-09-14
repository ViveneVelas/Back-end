package com.velas.vivene.inventory.manager.service;

import com.velas.vivene.inventory.manager.datastructure.HashTable;
import com.velas.vivene.inventory.manager.datastructure.Node;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HashTableService {
    private HashTable<String> hashTable;

    public HashTableService() {
        this.hashTable = new HashTable<>(26);
    }

    public void addName(String name) {
        hashTable.insert(name);
    }

    public List<String> searchNames(String name) {
        List<String> namesList = new ArrayList<>();
        Node<String> result = hashTable.search(name);

        while (result != null) {
            namesList.add(result.getName());
            result = result.getNext();
        }

        return namesList;
    }
}
