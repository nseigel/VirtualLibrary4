import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.io.Serializable;

public class LinkedList implements Serializable {
  class Node implements Serializable {
    Literature value;
    Node next;
    Node previous;

    public Node(Literature value, Node previous, Node next) {
      this.value = value;
      this.previous = previous;
      this.next = next;
    }

    public Literature getValue() {
      return this.value;
    }
  }
    
    private Node first;
    private Node last;
    private Node next = null;
    
    public LinkedList() {
        first = null;
        last = null;
    }
    
    public void add(Object element) {
            if (first == null) {
                first = new Node((Literature) element, null, null);
            } else if (last == null) {
                last = new Node((Literature) element, first, null);
                first.next = last;
            } else {
                Node newNode = new Node((Literature) element, last, null);
                last.next = newNode;
                last = newNode;
            }            
        }

    
    public int size() {
        if (first == null) {
            return 0;
        } else {
            Node current = first;
            int count = 0;
            while (current.next != null) {
                count++;
                current = current.next;
            }
            count++;
            return count;
        }
    }
    
    public void remove(int index) {
        if (isInBounds(index)) {
            if (size() == 1 && index == 0) {
              first = null;
              last = null;
            } else if (index == 0) {
                Node newFirst = first.next;
                first = newFirst;
                newFirst.previous = null;
            } else if (index == size() - 1) {
                Node newLast = last.previous;
                last = newLast;
                newLast.next = null;
            } else {
                Node toRemove = reach(index);
                Node previous = toRemove.previous;
                Node next = toRemove.next;
                previous.next = next;
                next.previous = previous;
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }
    
    private Node reach(int index) {
        if (isInBounds(index)) {
            Node current = first;
            int currentIndex = 0;
            while (currentIndex < index) {
                current = current.next;
                currentIndex++;
            }
            return current;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }
    
    private boolean isInBounds(int index) {
        if (index < size() && index > -1) {
            return true;
        } else {
            return false;
        }
    }
    
    public void traverseForwards() {
      Node current = first;
      while(current != null) {
        System.out.println(current.value.toString(false));
        System.out.println();
        current = current.next;
      }
    }

    public void traverseBackwards() {
      Node current = last;
      while(current != null) {
        System.out.println(current.value.toString(false));
        System.out.println();
        current = current.previous;
      }
    }

    public void remove(Literature value) {
      ArrayList<Literature> literatureArray = new ArrayList<>();
      Node current = first;
      while (current != null) {
        literatureArray.add(current.value);
        current = current.next;
      }

      int index = literatureArray.indexOf(value);
      if (index != -1) {
        remove(index);
      }
    }

  public ArrayList<Literature> toArray() {
    ArrayList<Literature> books = new ArrayList<>();
    Node current = last;
    while(current != null) {
      books.add(current.value);
      current = current.previous;
    }
    return books;
  }

  public ArrayList<Literature> findByTitle(String title) {
    Node current = first;
    ArrayList<Literature> toReturn = new ArrayList<>();
    while (current != null) {
      if (current.value.getTitle().equals(title.toUpperCase())) {
        toReturn.add(current.value);
      }
      current = current.next;
    }
    return toReturn;
  }

  public ArrayList<Book> findByAuthor(String authorName) {
    Node current = first;
    ArrayList<Book> toReturn = new ArrayList<>();
    while(current != null) {
      if (current.value instanceof Book) {
        if (((Book)current.value).getAuthorLastName().equals(authorName.toUpperCase())) {
          toReturn.add((Book) current.value);
        }
      }
      current = current.next;
    }
    return toReturn;
  }

}