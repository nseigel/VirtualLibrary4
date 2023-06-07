import java.time.LocalDate;
import java.io.Serializable;
import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;

public abstract class Literature implements Serializable {
  private String title;
  private LocalDate publishDate;
  private String type;
  private boolean category = false;
  private ArrayList<String> categories = new ArrayList<>();
  public static int[] maxes = new int[10];
  public static final int TYPE = 0;
  public static final int TITLE = 1;
  public static final int AUTHOR_FIRST = 2;
  public static final int AUTHOR_LAST = 3;
  public static final int PAGES = 4;
  public static final int CHAPTERS = 5;
  public static final int ILLUSTRATOR_FIRST = 6;
  public static final int ILLUSTRATOR_LAST = 7;
  public static final int ACTS = 8;
  public static final int PUBLISHER = 9;

  public Literature(String title, LocalDate publishDate, String type) {
    this.title = trim(title, 50);
    this.publishDate = publishDate;
    this.type = type;
    if(title.length() > maxes[TITLE]) {
      maxes[TITLE] = title.length();
    } if(type.length() > maxes[TYPE]) {
      maxes[TYPE] = type.length();
    }
  }

  public String trim(String string, int maxCharacters) {
    if (string.length() > maxCharacters) {
      return string.substring(0, maxCharacters).toUpperCase();
    } else {
      return string.toUpperCase();
    }
  }

  public String getTitle() {
    return title;
  }

  public LocalDate getPublishDate() {
    return publishDate;
  }

  public String getType() {
    return type;
  }

  public int compareByTitle(Literature other) {
    return title.compareTo(other.getTitle());
  }

  public int compareByPublishYear(Literature other) {
    if(publishDate.getYear() > other.getPublishDate().getYear()) {
      return 1;
    } else if (publishDate.getYear() < other.getPublishDate().getYear()) {
      return -1;
    } else {
      return 0;
    }
  }

  public int compareByPublishDate(Literature other) {
    return publishDate.compareTo(other.getPublishDate());
  }

  public ArrayList<String> getCategories() {
    return categories;
  }

  public void addCategory(String category) {
    categories.add(category.toUpperCase());
  }

  public void removeCategory(String category) {
    if(categories.contains(category.toUpperCase())) {
      System.out.println(String.format("BOOK SUCCESSFULLY REMOVED FROM CATEGORY %s", category));
    } else {
      System.out.println("THIS BOOK IS NOT IN THIS CATEGORY");
    }
  }

  public static void initializeMaxes() {
    int[] array = null;
    try {
      ObjectInputStream in = new ObjectInputStream(new FileInputStream("maxes.txt"));
      array = (int[]) in.readObject();
      in.close();
    } catch(Exception e) {
      array = new int[10];
      Arrays.fill(array, 1);
    } 
    maxes = array;
  }

  public static void serializeMaxes() {
    if(ConsoleInterface.clear) {
      try {
        FileOutputStream fout = new FileOutputStream("maxes.txt");
        ObjectOutputStream out = new ObjectOutputStream(fout);
        int[] empty = new int[10];
        out.writeObject(empty);
        out.flush();
        out.close();
      } catch (Exception e) {
      }
    } else {
      try {
        FileOutputStream fout = new FileOutputStream("maxes.txt");
        ObjectOutputStream out = new ObjectOutputStream(fout);
        out.writeObject(maxes);
        out.flush();
        out.close();
      } catch (Exception e) {
        System.out.println(e);
      }
    }
  }

  public abstract String toString(boolean single);

}