import java.io.Serializable;
import java.util.ArrayList;

public class BinaryTreeTitle implements Serializable {
  class Node implements Serializable {

    ArrayList<Literature> value = new ArrayList<>();
    Node left;
    Node right;

    Node(Literature literature) {
      this.value.add(literature);
      right = null;
      left = null;
    }

    void add(Literature literature) {
      value.add(literature);
    }
  }

  Node root;

  public BinaryTreeTitle() {
    root = null;
  }

  public void add(Literature value) {
    root = add(root, value);
  }

  private Node add(Node current, Literature value) {
    if (current == null) {
      return new Node(value);
    }
    if (value.compareByTitle(current.value.get(0)) < 0) {
      current.left = add(current.left, value);
    } else if (value.compareByTitle(current.value.get(0)) > 0) {
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
      for (Literature literature : current.value) {
        System.out.println(literature.toString(false));
        System.out.println();
      }
      traverse(current.right);
    }
  }

  public ArrayList<Literature> arrayTraverse() {
    ArrayList<Literature> entireTree = new ArrayList<>();
    return arrayTraverse(root, entireTree);
  }

  private ArrayList<Literature> arrayTraverse(Node current, ArrayList<Literature> entireTree) {
    if (current != null) {
      arrayTraverse(current.left, entireTree);
      for (Literature literature : current.value) {
        entireTree.add(literature);
      }
      arrayTraverse(current.right, entireTree);
    }
    return entireTree;
  }

  public void remove(Literature value) {
    root = remove(root, value);
  }

  private Node remove(Node current, Literature value) {
    if (current == null) {
      return null;
    } else if (value.compareByTitle(current.value.get(0)) == 0) {
      if(current.value.size() > 1) {
        current.value.remove(value);
      } else {
        if (current.right == null) {
          return current.left;
        } else if (current.left == null) {
          return current.right;
        } else {
          ArrayList<Literature> smallestValue = findSmallestValue(current.right);
          current.value = smallestValue;
          for(int index = 0; index < smallestValue.size(); index++) {
            current.right = remove(current.right, smallestValue.get(index));
          }
        }
      }        
    } else if (value.compareByTitle(current.value.get(0)) < 0) {
      current.left = remove(current.left, value);
      return current;
    } else if (value.compareByTitle(current.value.get(0)) > 0) {
      current.right = remove(current.right, value);
      return current;
    }
    return current;
  }

  public ArrayList<Literature> findSmallestValue(Node root) {
    if (root.left == null) {
      return root.value;
    } else {
      return findSmallestValue(root.left);
    }
  }

 public ArrayList<Literature> find(String title) {
    Node current = find(root, title.toUpperCase());
    try {
      return current.value;
    } catch (Exception e) {
      return new ArrayList<Literature>();
    }
  }

  private Node find(Node current, String title) {
    if (current == null) {
      return null;
    } else if (title.compareTo(current.value.get(0).getTitle()) == 0) {
      return current;
    } else if (title.compareTo(current.value.get(0).getTitle()) < 0) {
      return find(current.left, title);
    } else {
      return find(current.right, title);
    }
  

  }

}