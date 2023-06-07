import java.io.Serializable;
import java.util.ArrayList;

public class BinaryTreeAuthor implements Serializable {
  class Node implements Serializable {

    ArrayList<Book> value = new ArrayList<>();
    Node left;
    Node right;

    Node(Book book) {
      this.value.add(book);
      right = null;
      left = null;
    }

    void add(Book book) {
      value.add(book);
    }
  }

  Node root;

  public BinaryTreeAuthor() {
    root = null;
  }

  public void add(Book value) {
    root = add(root, value);
  }

  private Node add(Node current, Book value) {
    if (current == null) {
      return new Node(value);
    }
    if (value.compareByAuthor(current.value.get(0)) < 0) {
      current.left = add(current.left, value);
    } else if (value.compareByAuthor(current.value.get(0)) > 0) {
      current.right = add(current.right, value);
    } else {
      current.add(value);
    }
    return current;
  }

  public void traverse() {
    traverse(root);
  }

  private void traverse(Node current) {
    if (current != null) {
      traverse(current.left);
      for (Book book : current.value) {
        System.out.println(book.toString(false));
        System.out.println();
      }
      traverse(current.right);
    }
  }

  /*public ArrayList<Book> arrayTraverse() {
    return arrayTraverse(root);
  }

  private ArrayList<Book> arrayTraverse(Node current) {
    ArrayList<Book> entireTree = new ArrayList<>();
    if (current != null) {
      traverse(current.left);
      for (Book literature : current.value) {
        entireTree.add(literature);
      }
      traverse(current.right);
    }
    return entireTree;
  }*/

  public void remove(Book value) {
    root = remove(root, value);
  }

  private Node remove(Node current, Book value) {
    if (current == null) {
      return null;
    } else if (value.compareByAuthor(current.value.get(0)) == 0) {
      if (current.value.size() > 1) {
        current.value.remove(value);     
      } else {
        if (current.right == null) {
          return current.left;
        } else if (current.left == null) {
          return current.right;
        } else {
          ArrayList<Book> smallestValue = findSmallestValue(current.right);
          current.value = smallestValue;
          for(int index = 0; index < smallestValue.size(); index++) {
            current.right = remove(current.right, smallestValue.get(index));
          }
        }
      }
    } else if (value.compareByAuthor(current.value.get(0)) < 0) {
      current.left = remove(current.left, value);
      return current;
    } else if (value.compareByAuthor(current.value.get(0)) > 0) {
      current.right = remove(current.right, value);
      return current;
    }
    return current;
  }

  public ArrayList<Book> findSmallestValue(Node root) {
    if (root.left == null) {
      return root.value;
    } else {
      return findSmallestValue(root.left);
    }
  }

  public ArrayList<Book> find(String name) {
    Node current = find(root, name.toUpperCase());
    try {
      return current.value;
    } catch (Exception e) {
      return new ArrayList<Book>();
    }
  }

  private Node find(Node current, String name) {
    if (current == null) {
      return null;
    } else if (name.compareTo(current.value.get(0).getAuthorLastName()) == 0) {
      return current;
    } else if (name.compareTo(current.value.get(0).getAuthorLastName()) < 0) {
      return find(current.left, name);
    } else {
      return find(current.right, name);
    }
  }

}