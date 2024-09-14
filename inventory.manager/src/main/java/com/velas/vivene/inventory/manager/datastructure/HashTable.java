package com.velas.vivene.inventory.manager.datastructure;

import java.util.Objects;

public class HashTable<T extends Comparable<T>> {
    private Node<T>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public HashTable(int size) {
        this.size = size;
        this.table = (Node<T>[]) new Node[size];
    }

    private int hash(T key) {
        return Math.abs(key.hashCode() % size);
    }

    public void insert(T name) {
        int index = hash(name);
        Node<T> newNode = new Node<>(name);

        if (table[index] == null) {
            table[index] = newNode;
        } else {
            Node<T> current = table[index];
            Node<T> previous = null;

            while (current != null && current.getName().compareTo(newNode.getName()) < 0) {
                previous = current;
                current = current.getNext();
            }

            if (previous == null) {
                newNode.setNext(table[index]);
                table[index] = newNode;
            } else {
                newNode.setNext(previous.getNext());
                previous.setNext(newNode);
            }
        }
    }

    public Node<T> search(T name) {
        int index = hash(name);
        Node<T> current = table[index];

        while (current != null && !Objects.equals(current.getName(), name)) {
            current = current.getNext();
        }

        return current;
    }
}
