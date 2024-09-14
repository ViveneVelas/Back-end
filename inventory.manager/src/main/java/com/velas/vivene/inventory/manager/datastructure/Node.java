package com.velas.vivene.inventory.manager.datastructure;

public class Node<T>{
    private T name;
    private Node<T> next;

    public Node(T name) {
        this.name = name;
        this.next = null;
    }

    public T getName() {
        return name;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}
