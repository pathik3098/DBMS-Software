package CreateOperation;

public class LinkedList {
    Node head;

    static class Node{
        int intData;
        String stringData;
        float floatData;
        Node next;

        Node(int i){
            intData = i;
        }

        Node(String s){
            stringData = s;
        }

        Node(float f){
            floatData = f;
        }
    }

    public static void insert(LinkedList list,int data){

        Node newNode = new Node(data);
        newNode.next = null;

        if (list.head == null) {
            list.head = newNode;
        }
        else {
            Node last = list.head;
            while (last.next != null) {
                last = last.next;
            }
            last.next = newNode;
        }
    }

    public static void insert(LinkedList list,String data){

        Node newNode = new Node(data);
        newNode.next = null;

        if (list.head == null) {
            list.head = newNode;
        }
        else {
            Node last = list.head;
            while (last.next != null) {
                last = last.next;
            }
            last.next = newNode;
        }
    }

    public static void insert(LinkedList list,Float data){

        Node newNode = new Node(data);
        newNode.next = null;

        if (list.head == null) {
            list.head = newNode;
        }
        else {
            Node last = list.head;
            while (last.next != null) {
                last = last.next;
            }
            last.next = newNode;
        }
    }
}
