import java.time.LocalDate;
import java.io.Serializable;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class ConsoleInterface {

  private static LinkedList literatureList = deserializeList();
  private static BinaryTreeTitle titleTree = deserializeTitle();
  private static BinaryTreePublishDate dateTree = deserializeDate();
  private static BinaryTreeAuthor authorTree = deserializeAuthor();
  private static ArrayList<String> categoryList = deserializeCategory();
  private static boolean done = false;
  public static boolean clear = false;
  
  public static void run() {
    Literature.initializeMaxes();
    System.out.println("WELCOME TO YOUR VIRTUAL LIBRARY!");
    while(!done) {
      mainPrompt();
    }
    System.out.println("CLOSING YOUR VIRTUAL LIBRARY...");
    serializeList();
    serializeTitle();
    serializeDate();
    serializeAuthor();
    serializeCategory();
    Literature.serializeMaxes();
  }

  private static void mainPrompt() {
    Scanner s = new Scanner(System.in);
    System.out.println("WHAT DO YOU WANT TO DO? \n'Q' TO QUIT \n'A' TO ADD A BOOK \n'S' TO SORT \n'F' TO FIND \n'R' TO REMOVE \n'C' TO ADD A CATEGORY, ADD A BOOK TO A CATEGORY, REMOVE A BOOK FROM A CATEGORY, REMOVE A CATEGORY, OR TO SEE LIST OF CATEGORIES \n'W' TO WIPE LIBRARY AND CLOSE");
    String next = s.nextLine().toUpperCase(); 
    if(next.equals("Q")) {
      done = true;
    } else if(next.equals("A")) {
      add();
    } else if(next.equals("F")) {
      find();
    } else if(next.equals("S")) {
      sort();
    } else if (next.equals("R")) {
      remove();
    } else if (next.equals("C")) {
      System.out.println("'N' TO CREATE NEW CATEGORY \n'B' TO ADD BOOK TO CATEGORY \n'P' TO SEE LIST OF CATEGORIES \n'R' TO REMOVE A BOOK FROM A CATEGORY \n'C' TO REMOVE A CATEGORY");
      next = s.next().toUpperCase();
      if(next.equals("N")) {
        try {
          System.out.println("ENTER NAME OF NEW CATEGORY:");
          Scanner scanner = new Scanner(System.in);
          String category = scanner.nextLine().toUpperCase();
          if(categoryList.contains(category)) {
            System.out.println("THIS CATEGORY ALREADY EXISTS.");
          } else {
            categoryList.add(category);
            System.out.println("CATEGORY SUCCESSFULLY ADDED.");
          }
        } catch (Exception e) {
          System.out.println("INVALID COMMAND. PLEASE TRY AGAIN.");
        }
      } else if (next.equals("B")) {
        addCategory();
      } else if (next.equals("P")) {
        if(categoryList.isEmpty()) {
          System.out.println("YOU HAVE NOT ADDED ANY CATEGORIES.");
        } else {
          System.out.println(categoryList.toString());
        }
      } else if (next.equals("R")) {
        removeCategory();
      } else if (next.equals("C"))  {
        try {
          System.out.println("ENTER NAME OF CATEGORY TO REMOVE:");
          Scanner scanner = new Scanner(System.in);
          String category = scanner.nextLine().toUpperCase();
          if(categoryList.contains(category)) {
            categoryList.remove(category);
            for (Literature literature : literatureList.toArray()) {
              if (literature.getCategories().contains(category)) {
                literature.removeCategory(category);
              }
            }
            System.out.println("CATEGORY SUCCESSFULLY REMOVED.");
          } else {
            System.out.println("CATEGORY NOT FOUND.");
          }
        } catch (Exception e) {
          System.out.println("INVALID COMMAND. PLEASE TRY AGAIN.");
        }
      } else {
        System.out.println("INVALID COMMAND. PLEASE TRY AGAIN.");
      }   
    } else if (next.equals("W")) {
      clear = true;
      done = true;
      try {
        serializeTitle();
        serializeAuthor();
        serializeDate();
        serializeList();
        serializeCategory();
        System.out.println("LIBRARY CLEAR SUCCESSFUL.");
      } catch (Exception e) {
        System.out.println("LIBRARY CLEAR UNSUCCESSFUL.");
      }
    } else {
      System.out.println("INVALID COMMAND. PLEASE TRY AGAIN.");
    }
  }

  private static void addCategory() {
    Scanner s = new Scanner(System.in);
    System.out.println("FIND BOOK YOU WANT TO ADD:");
    System.out.println("'T' TO SEARCH BY TITLE \n'A' TO SEARCH BY AUTHOR LAST NAME");
    String decision = s.nextLine().toUpperCase();
    if(decision.equals("T")) {
      System.out.println("ENTER TITLE OF BOOK YOU WOULD LIKE TO SELECT:");
      String title = s.nextLine().toUpperCase();
      ArrayList<Literature> books = literatureList.findByTitle(title);
      if(books.isEmpty()) {
        System.out.println("BOOK NOT FOUND.");
      } else {
        if(books.size() == 1) {
          System.out.println("BOOK FOUND:");
          System.out.println(books.get(0).toString(true));
          System.out.println("ENTER NAME OF CATEGORY YOU WOULD LIKE TO ADD THIS BOOK TO:");
          String category = s.nextLine().toUpperCase();
          if(categoryList.contains(category)) {
            books.get(0).addCategory(category);
            System.out.println(String.format("BOOK SUCCESSFULLY ADDED TO CATEGORY: %s.", category));
          } else {
            System.out.println("CATEGORY NOT FOUND.");
          }
        } else {
          int currentIndex = 0;
          for (Literature literature : books) {
            System.out.println(String.format("BOOK INDEX (%d):", currentIndex));
            System.out.println(literature.toString(false));
            System.out.println();
            currentIndex++;
          }
          System.out.println("ENTER INDEX OF BOOK YOU WOULD LIKE TO SELECT:");
          try {
            int indexToAdd = s.nextInt();
            Literature chosen = books.get(indexToAdd);
            System.out.println("ENTER NAME OF CATEGORY YOU WOULD LIKE TO ADD THIS BOOK TO:");
            Scanner scanner = new Scanner(System.in);
            String category = scanner.nextLine().toUpperCase();
            if (categoryList.contains(category)) {
              chosen.addCategory(category);
              System.out.println(String.format("BOOK SUCCESSFULLY ADDED TO CATEGORY: %s.", category));
            } else {
              System.out.println("CATEGORY NOT FOUND.");
            }
          } catch(Exception e) {
            System.out.println("INVALID COMMAND");
          }
        }
      }
    } else if (decision.equals("A")) {
      System.out.println("ENTER AUTHOR LAST NAME:");
      String authorLast = s.nextLine().toUpperCase();
      ArrayList<Book> booksAuthor = literatureList.findByAuthor(authorLast);
      if(booksAuthor.isEmpty()) {
        System.out.println("BOOK NOT FOUND.");
      } else {
        if(booksAuthor.size() == 1) {
          System.out.println("BOOK FOUND:");
          System.out.println(booksAuthor.get(0).toString(true));
          System.out.println("ENTER CATEGORY YOU WOULD LIKE TO ADD THIS BOOK TO:");
          String category = s.nextLine().toUpperCase();
          if (categoryList.contains(category)) {
            booksAuthor.get(0).addCategory(category);
          } else {
            System.out.println("CATEGORY NOT FOUND");
          }
        } else {
          int currentIndex = 0;
          for (Book book : booksAuthor) {
            System.out.println(String.format("BOOK INDEX (%d):", currentIndex));
            System.out.println(book.toString(false));
            System.out.println();
            currentIndex++;
          }
          System.out.println("ENTER INDEX OF BOOK YOU WOULD LIKE TO ADD TO CATEGORY.");
          try {
            int indexToAdd = s.nextInt();
            Literature chosen = booksAuthor.get(indexToAdd);
            System.out.println("ENTER CATEGORY YOU WOULD LIKE TO ADD THIS BOOK TO:");
            Scanner scanner = new Scanner(System.in);
            String category = scanner.nextLine().toUpperCase();
            if (categoryList.contains(category)) {
              chosen.addCategory(category);
            } else {
              System.out.println("CATEGORY NOT FOUND");
            }
          } catch(Exception e) {
            System.out.println("INVALID COMMAND. PLEASE TRY AGAIN.");
          }
        }
      }
    } else {
      System.out.println("INVALID COMMAND. PLEASE TRY AGAIN.");
    }
  }

  private static void removeCategory() {
    Scanner s = new Scanner(System.in);
    System.out.println("FIND BOOK YOU WANT TO REMOVE:");
    System.out.println("'T' TO SEARCH BY TITLE \n'A' TO SEARCH BY AUTHOR LAST NAME");
    String decision = s.nextLine().toUpperCase();
    if(decision.equals("T")) {
      System.out.println("ENTER TITLE OF BOOK YOU WOULD LIKE TO SELECT:");
      String title = s.nextLine();
      ArrayList<Literature> books = literatureList.findByTitle(title);
      if(books.isEmpty()) {
        System.out.println("BOOK NOT FOUND.");
      } else {
        if(books.size() == 1) {
          System.out.println("BOOK FOUND:");
          System.out.println(books.get(0).toString(true));
          System.out.println("ENTER NAME OF CATEGORY YOU WOULD LIKE TO REMOVE THIS BOOK FROM:");
          String category = s.nextLine().toUpperCase();
          if(categoryList.contains(category)) {
            books.get(0).removeCategory(category);
          } else {
            System.out.println("CATEGORY NOT FOUND.");
          }
        } else {
          int currentIndex = 0;
          for (Literature literature : books) {
            System.out.println(String.format("BOOK INDEX (%d):", currentIndex));
            System.out.println(literature.toString(false));
            System.out.println();
            currentIndex++;
          }
          System.out.println("ENTER INDEX OF BOOK YOU WOULD LIKE TO SELECT:");
          try {
            int indexToRemove = s.nextInt();
            Literature chosen = books.get(indexToRemove);
            System.out.println("ENTER NAME OF CATEGORY YOU WOULD LIKE TO REMOVE THIS BOOK FROM:");
            Scanner scanner = new Scanner(System.in);
            String category = scanner.nextLine().toUpperCase();
            if (categoryList.contains(category)) {
              chosen.removeCategory(category);
            } else {
              System.out.println("CATEGORY NOT FOUND.");
            }
          } catch(Exception e) {
            System.out.println("INVALID COMMAND. PLEASE TRY AGAIN.");
          }
        }
      }
    } else if (decision.equals("A")) {
      System.out.println("ENTER AUTHOR LAST NAME:");
      String authorLast = s.nextLine().toUpperCase();
      ArrayList<Book> booksAuthor = literatureList.findByAuthor(authorLast);
      if(booksAuthor.isEmpty()) {
        System.out.println("BOOK NOT FOUND.");
      } else {
        if(booksAuthor.size() == 1) {
          System.out.println("BOOK FOUND:");
          System.out.println(booksAuthor.get(0).toString(true));
          System.out.println("ENTER CATEGORY YOU WOULD LIKE TO REMOVE THIS BOOK FROM:");
          String category = s.nextLine().toUpperCase();
          if (categoryList.contains(category)) {
            booksAuthor.get(0).removeCategory(category);
          } else {
            System.out.println("CATEGORY NOT FOUND");
          }
        } else {
          int currentIndex = 0;
          for (Book book : booksAuthor) {
            System.out.println(String.format("BOOK INDEX (%d):", currentIndex));
            System.out.println(book.toString(false));
            System.out.println();
            currentIndex++;
          }
          System.out.println("ENTER INDEX OF BOOK YOU WOULD LIKE TO REMOVE FROM CATEGORY.");
          try {
            int indexToRemove = s.nextInt();
            Book chosen = booksAuthor.get(indexToRemove);
            System.out.println("ENTER CATEGORY YOU WOULD LIKE TO REMOVE THIS BOOK FROM:");
            Scanner scanner = new Scanner(System.in);
            String category = scanner.nextLine().toUpperCase();
            if (categoryList.contains(category)) {
              chosen.removeCategory(category);
            } else {
              System.out.println("CATEGORY NOT FOUND");
            }
          } catch(Exception e) {
            System.out.println("INVALID COMMAND. PLEASE TRY AGAIN.");
          }
        }
      }
    } else {
      System.out.println("INVALID COMMAND. PLEASE TRY AGAIN.");
    }
  }

  private static void add() {
    Scanner s = new Scanner(System.in);
    try {
      System.out.println("ENTER TYPE (BOOK, NOVEL, COMIC, MAGAZINE, POETRY, PLAY):");
      String type = s.nextLine().toUpperCase();
      if(type.equals("BOOK") || type.equals("NOVEL") || type.equals("PLAY") || type.equals("COMIC") || type.equals("POETRY")) {
        System.out.println("ENTER TITLE:");
        String title = s.nextLine();
        LocalDate publishDate = createDate();
        System.out.println("ENTER AUTHOR FIRST NAME:");
        String authorFirstName = s.next();
        System.out.println("ENTER AUTHOR LAST NAME:");
        String authorLastName = s.next();
        System.out.println("ENTER NUMBER OF PAGES:");
        int numberOfPages = s.nextInt();
        if (type.equals("BOOK")) {
          Book current = new Book(title, publishDate, authorFirstName, authorLastName, numberOfPages);
          boolean bookFound = false;
          for(Literature book : titleTree.arrayTraverse()) {
            if(book instanceof Book) {
              if(current.checkBook((Book) book)) {
                bookFound = true;
              }
            }
          } if(!bookFound) {
            addBook(current);
          } else {
            System.out.println("THIS BOOK HAS ALREADY BEEN ADDED.");
          }
        } else if(type.equals("NOVEL")) {
          System.out.println("ENTER NUMBER OF CHAPTERS:");
          int numberOfChapters = s.nextInt();
          Novel current = new Novel(title, publishDate, authorFirstName, authorLastName, numberOfPages, numberOfChapters);
          boolean bookFound = false;
          for(Literature book : titleTree.arrayTraverse()) {
            if(book instanceof Novel) {
              if(current.checkNovel((Novel) book)) {
                bookFound = true;
              }
            }
          } if(!bookFound) {
            addBook(current);
          } else {
            System.out.println("THIS NOVEL HAS ALREADY BEEN ADDED.");
          }
        } else if(type.equals("COMIC")) {
          System.out.println("ENTER ILLUSTRATOR FIRST NAME:");
          String illustratorFirstName = s.next();
          System.out.println("ENTER ILLUSTRATOR LAST NAME:");
          String illustratorLastName = s.next();
          Comic current = new Comic(title, publishDate, authorFirstName, authorLastName, numberOfPages, illustratorFirstName, illustratorLastName);
          boolean bookFound = false;
          for(Literature book : titleTree.arrayTraverse()) {
            if(book instanceof Comic) {
              if(current.checkComic((Comic) book)) {
                bookFound = true;
              }
            }
          } if(!bookFound) {
            addBook(current);
          } else {
            System.out.println("THIS COMIC HAS ALREADY BEEN ADDED.");
          }
        } else if (type.equals("PLAY")) {
          System.out.println("ENTER NUMBER OF ACTS:");
          int acts = s.nextInt();
          Play current = new Play(title, publishDate, authorFirstName, authorLastName, numberOfPages, acts);
          boolean bookFound = false;
          for(Literature book : titleTree.arrayTraverse()) {
            if(book instanceof Play) {
              if(current.checkPlay((Play) book)) {
                bookFound = true;
              }
            }
          } if(!bookFound) {
            addBook(current);
          } else {
            System.out.println("THIS PLAY HAS ALREADY BEEN ADDED.");
          }
        } else if (type.equals("POETRY")) {
          Poetry current = new Poetry(title, publishDate, authorFirstName, authorLastName, numberOfPages);
          boolean bookFound = false;
          for(Literature book : titleTree.arrayTraverse()) {
            if(book instanceof Poetry) {
              if(current.checkPoetry((Poetry) book)) {
                bookFound = true;
              }
            }
          } if(!bookFound) {
            addBook(current);
          } else {
            System.out.println("THIS POETRY HAS ALREADY BEEN ADDED.");
          }
        }
      } else if (type.equals("MAGAZINE")) {
        System.out.println("ENTER TITLE:");
        String title = s.nextLine();
        LocalDate publishDate = createDate();
        Scanner scanner = new Scanner(System.in);
        System.out.println("ENTER PUBLISHER:");
        String publisher = scanner.nextLine();
        Magazine current = new Magazine(title, publishDate, publisher);
        boolean bookFound = false;
          for(Literature book : titleTree.arrayTraverse()) {
            if(book instanceof Magazine) {
              if(current.checkMagazine((Magazine) book)) {
                bookFound = true;
              }
            }
          } if(!bookFound) {
            literatureList.add(current);
            titleTree.add(current);
            dateTree.add(current);
            System.out.println("LITERATURE ADDED:");
            System.out.println(current.toString(true));
          } else {
            System.out.println("THIS MAGAZINE HAS ALREADY BEEN ADDED.");
          }
      } else {
        System.out.println("INVALID COMMAND. PLEASE TRY AGAIN.");
      }
    } catch(Exception e) {
      System.out.println("INVALID COMMAND. PLEASE TRY AGAIN.");
    }
  }

  private static LocalDate createDate() {
    Scanner s = new Scanner(System.in);
    boolean yearAccepted = false;
    int publishYear = 0;
    while(!yearAccepted) {
      System.out.println("ENTER PUBLISH YEAR:");
      publishYear = s.nextInt();
      if(publishYear <= LocalDate.now().getYear()) {
        yearAccepted = true;
      } else {
        System.out.println("INVALID YEAR. PLEASE TRY AGAIN.");
      }
    }
    System.out.println("ENTER PUBLISH MONTH:");
    int publishMonth = s.nextInt();
    System.out.println("ENTER PUBLISH DAY:");
    int publishDay = s.nextInt();
    return LocalDate.of(publishYear, publishMonth, publishDay);
  }
  
  private static void addBook(Book current) {
    literatureList.add(current);
    titleTree.add(current);
    authorTree.add(current);
    dateTree.add(current);
    System.out.println("LITERATURE ADDED:");
    System.out.println(current.toString(true));
  }

  private static void find() {
    Scanner s = new Scanner(System.in);
    System.out.println("'T' TO SEARCH BY TITLE \n'A' TO SEARCH BY AUTHOR LAST NAME \n'Y' TO SEARCH BY PUBLISH YEAR:");
    String category = s.nextLine().toUpperCase();
    if (category.equals("T")) {
      System.out.println("ENTER TITLE:");
      String title = s.nextLine().toUpperCase();
      ArrayList<Literature> titleList = titleTree.find(title);
      if (titleList.isEmpty()) {
        System.out.println("BOOK NOT FOUND.");
      } else {
        for (Literature book : titleList) {
          System.out.println(book.toString(true));
          System.out.println();
        }
      }
    } else if (category.equals("A")) {
      System.out.println("ENTER AUTHOR LAST NAME:");
      String lastName = s.nextLine().toUpperCase();
      ArrayList<Book> authorList = authorTree.find(lastName);
      if(authorList.isEmpty()) {
        System.out.println("AUTHOR NOT FOUND.");
      } else {
        for(Literature literature : authorList) {
          System.out.println(literature.toString(true));
          System.out.println();
        }
      }
    } else if (category.equals("Y")) {
      System.out.println("ENTER PUBLISH YEAR:");
      int publishYear = s.nextInt();
      ArrayList<Literature> dateList = dateTree.find(publishYear);
      if (dateList.isEmpty()) {
        System.out.println(String.format("NO BOOKS FOUND PUBLISHED IN %d", publishYear));
      } else {
        for(Literature literature : dateList) {
          System.out.println(literature.toString(true));
          System.out.println();
        }
      }
    } else {
      System.out.println("INVALID COMMAND. PLEASE TRY AGAIN.");
    }
  }

  private static void sort() {
    Scanner s = new Scanner(System.in);
    System.out.println("'T' TO SORT BY TITLE \n'A' TO SORT BY AUTHOR LAST NAME \n'P' TO SORT BY PUBLISH DATE \n'N' TO SORT BY DATE ADDED TO LIBRARY (NEWEST TO OLDEST) \n'O' TO SORT BY DATE ADDED (OLDEST TO NEWEST) \n'C' TO SORT BY CATEGORY:");
    String category = s.nextLine().toUpperCase();
    if(category.equals("T")) {
      titleTree.traverse();
    } else if (category.equals("A")) {
      authorTree.traverse();
    } else if (category.equals("P")) {
      dateTree.traverse();
    } else if (category.equals("N")) {
      literatureList.traverseBackwards();
    } else if (category.equals("O")) {
      literatureList.traverseForwards();
    } else if (category.equals("C")) {      
      System.out.println("ENTER CATEGORY:");
      String chosenCategory = s.nextLine().toUpperCase();
      if (categoryList.contains(chosenCategory)) {
        for (Literature literature : literatureList.toArray()) {
          if (literature.getCategories().contains(chosenCategory)) {
            System.out.println(literature.toString(false));
            System.out.println();
          }
        }
      
      } else {
        System.out.println("CATEGORY NOT FOUND.");
      }
    } else {
      System.out.println("INVALID COMMAND. PLEASE TRY AGAIN.");
    }
  }

  private static void remove() {
    Scanner s = new Scanner(System.in);
    System.out.println("'T' TO REMOVE BY TITLE \n'A' TO REMOVE BY AUTHOR LAST NAME:");
    String decision = s.nextLine().toUpperCase();
    if(decision.equals("T")) {
      System.out.println("ENTER TITLE OF BOOK YOU WOULD LIKE TO REMOVE:");
      String title = s.nextLine();
      ArrayList<Literature> books = titleTree.find(title);
      if(books.isEmpty()) {
        System.out.println("BOOK NOT FOUND.");
      } else {
        if(books.size() == 1) {
          System.out.println("BOOK FOUND:");
          System.out.println(books.get(0).toString(true));
          titleTree.remove(books.get(0));
          if(books.get(0) instanceof Book) {
            authorTree.remove((Book) books.get(0));
          }
          dateTree.remove(books.get(0));
          literatureList.remove(books.get(0));
          System.out.println("BOOK SUCCESSFULLY REMOVED:");
          System.out.println(books.get(0).toString(true));
          updateMaxes(books.get(0));
        } else {
          int currentIndex = 0;
          for (Literature literature : books) {
            System.out.println(String.format("BOOK INDEX (%d):", currentIndex));
            System.out.println(literature.toString(false));
            System.out.println();
            currentIndex++;
          }
          System.out.println("ENTER INDEX OF BOOK YOU WOULD LIKE TO REMOVE.");
          try {
            int indexToRemove = s.nextInt();
            Literature toRemove = books.get(indexToRemove);
            titleTree.remove(toRemove);
            literatureList.remove(toRemove);
            dateTree.remove(toRemove);
            if(toRemove instanceof Book) {
              authorTree.remove((Book) toRemove);
            }
            System.out.println("BOOK SUCCESSFULLY REMOVED:");
            updateMaxes(toRemove);
            System.out.println(toRemove.toString(true));
          } catch(Exception e) {
            System.out.println("INVALID COMMAND. PLEASE TRY AGAIN.");
            System.out.println(e.toString());
          }
        }
      }
    } else if (decision.equals("A")) {
      System.out.println("ENTER AUTHOR LAST NAME:");
      String authorLast = s.nextLine().toUpperCase();
      ArrayList<Book> booksAuthor = authorTree.find(authorLast);
      if(booksAuthor.isEmpty()) {
        System.out.println("BOOK NOT FOUND.");
      } else {
        if(booksAuthor.size() == 1) {
          System.out.println("BOOK FOUND:");
          System.out.println(booksAuthor.get(0).toString(true));
          titleTree.remove(booksAuthor.get(0));
          authorTree.remove(booksAuthor.get(0));
          dateTree.remove(booksAuthor.get(0));
          literatureList.remove(booksAuthor.get(0));
          System.out.println("BOOK SUCCESSFULLY REMOVED:");
          updateMaxes(booksAuthor.get(0));
          System.out.println(booksAuthor.get(0).toString(true));
        } else {
          int currentIndex = 0;
          for (Book book : booksAuthor) {
            System.out.println(String.format("BOOK INDEX (%d):", currentIndex));
            System.out.println(book.toString(false));
            System.out.println();
            currentIndex++;
          }
          System.out.println("ENTER INDEX OF BOOK YOU WOULD LIKE TO REMOVE.");
          try {
            int indexToRemove = s.nextInt();
            Book toRemove = booksAuthor.get(indexToRemove);
            titleTree.remove(toRemove);
            literatureList.remove(toRemove);
            dateTree.remove(toRemove);
            authorTree.remove((Book) toRemove);
            System.out.println("BOOK SUCCESSFULLY REMOVED:");
            updateMaxes(toRemove);
            System.out.println(toRemove.toString(true));
          } catch(Exception e) {
            System.out.println("INVALID COMMAND. PLEASE TRY AGAIN.");
            System.out.println(e.toString());
          }
        }
      }
    } else {
      System.out.println("INVALID COMMAND. PLEASE TRY AGAIN.");
    }
      
  }

  private static void updateMaxes(Literature removed) {
    ArrayList<Literature> library = titleTree.arrayTraverse();
    int longestType = 0;
    int longestTitle = 0;
    for(Literature literature : library) {
      if(literature.getType().length() > longestType) {
        longestType = literature.getType().length();
      }
      if(literature.getTitle().length() > longestTitle) {
        longestTitle = literature.getTitle().length();
      }
    }
    Literature.maxes[Literature.TITLE] = longestTitle;
    Literature.maxes[Literature.TYPE] = longestType;
    if(removed instanceof Book) {
      int longestAuthorFirst = 0;
      int longestAuthorLast = 0;
      int longestPages = 0;
      for(Literature book : library) {
        if(book instanceof Book) {
          if(((Book) book).getAuthorFirstName().length() > longestAuthorFirst) {
            longestAuthorFirst = ((Book) book).getAuthorFirstName().length();
          }
          if(((Book) book).getAuthorLastName().length() > longestAuthorLast) {
            longestAuthorLast = ((Book) book).getAuthorLastName().length();
          }
          if(Book.numberOfDigits(((Book) book).getNumberOfPages()) > longestPages) {
            longestPages = Book.numberOfDigits(((Book) book).getNumberOfPages());
          }
        }
      }
      Literature.maxes[Literature.AUTHOR_FIRST] = longestAuthorFirst;
      Literature.maxes[Literature.AUTHOR_LAST] = longestAuthorLast;
      Literature.maxes[Literature.PAGES] = longestPages;
      if(removed instanceof Comic) {
        int longestIllustratorFirst = 0;
        int longestIllustratorLast = 0;
        for(Literature comic : library) {
          if(comic instanceof Comic) {
            if(((Comic) comic).getIllustratorFirstName().length() > longestIllustratorFirst) {              
              longestIllustratorFirst = ((Comic) comic).getIllustratorFirstName().length();
            } if(((Comic) comic).getIllustratorLastName().length() > longestIllustratorLast) {
              longestIllustratorLast = ((Comic) comic).getIllustratorLastName().length();
            }
          }
        }
        Literature.maxes[Literature.ILLUSTRATOR_FIRST] = longestIllustratorFirst;
        Literature.maxes[Literature.ILLUSTRATOR_LAST] = longestIllustratorLast;
      }
    } else if(removed instanceof Magazine) {
      int longestPublisher = 0;
      for(Literature literature : library) {
        if(literature instanceof Magazine) {
          if(((Magazine) literature).getPublisher().length() > longestPublisher) {
            longestPublisher = ((Magazine) literature).getPublisher().length();
          }
        }  
      }
      Literature.maxes[Literature.PUBLISHER] = longestPublisher;
    }
  }
  
  private static BinaryTreeTitle deserializeTitle() {
    try {
      ObjectInputStream in = new ObjectInputStream(new FileInputStream("titleTree.txt"));
      titleTree = (BinaryTreeTitle) in.readObject();
      in.close();
    } catch(Exception e) {
      titleTree = new BinaryTreeTitle();
    }
    return titleTree;
  }

  private static BinaryTreePublishDate deserializeDate() {
    try {
      ObjectInputStream in = new ObjectInputStream(new FileInputStream("dateTree.txt"));
      dateTree = (BinaryTreePublishDate) in.readObject();
      in.close();
    } catch(Exception e) {
      dateTree = new BinaryTreePublishDate();
    }
    return dateTree;
  }

  private static BinaryTreeAuthor deserializeAuthor() {
    try {
      ObjectInputStream in = new ObjectInputStream(new FileInputStream("authorTree.txt"));
      authorTree = (BinaryTreeAuthor) in.readObject();
      in.close();
    } catch(Exception e) {
      authorTree = new BinaryTreeAuthor();
    }
    return authorTree;
  }

  private static LinkedList deserializeList() {
    LinkedList list = null;
    try {
      ObjectInputStream in = new ObjectInputStream(new FileInputStream("linkedList.txt"));
      list = (LinkedList) in.readObject();
      in.close();
    } catch(Exception e) {
      list = new LinkedList();
    } 
    return list;
  }

  private static ArrayList<String> deserializeCategory() {
    ArrayList<String> categories = null;
    try {
      ObjectInputStream in = new ObjectInputStream(new FileInputStream("categoryList.txt"));
      categories = (ArrayList<String>) in.readObject();
      in.close();
    } catch(Exception e) {
      categories = new ArrayList<String>();
    } 
    return categories;
  }

  private static void serializeCategory() {
    if(clear) {
      try {
        FileOutputStream fout = new FileOutputStream("categoryList.txt");
        ObjectOutputStream out = new ObjectOutputStream(fout);
        ArrayList<String> emptyList = new ArrayList<>();
        out.writeObject(emptyList);
        out.flush();
        out.close();
      } catch (Exception e) {
      }
    } else {
      try {
        FileOutputStream fout = new FileOutputStream("categoryList.txt");
        ObjectOutputStream out = new ObjectOutputStream(fout);
        out.writeObject(categoryList);
        out.flush();
        out.close();
      } catch (Exception e) {
        System.out.println(e);
      }
    }
  }
  
  private static void serializeTitle() {
    if(clear) {
      try {
        FileOutputStream fout = new FileOutputStream("titleTree.txt");
        ObjectOutputStream out = new ObjectOutputStream(fout);
        BinaryTreeTitle emptyTree = new BinaryTreeTitle();
        out.writeObject(emptyTree);
        out.flush();
        out.close();
      } catch (Exception e) {
      }
    } else {
      try {
        FileOutputStream fout = new FileOutputStream("titleTree.txt");
        ObjectOutputStream out = new ObjectOutputStream(fout);
        out.writeObject(titleTree);
        out.flush();
        out.close();
      } catch (Exception e) {
        System.out.println(e);
      }
    }
    
  }

  private static void serializeAuthor() {
    if(clear) {
      try {
        FileOutputStream fout = new FileOutputStream("authorTree.txt");
        ObjectOutputStream out = new ObjectOutputStream(fout);
        BinaryTreeAuthor emptyTree = new BinaryTreeAuthor();
        out.writeObject(emptyTree);
        out.flush();
        out.close();
      } catch (Exception e) {
      }
    } else {
      try {
        FileOutputStream fout = new FileOutputStream("authorTree.txt");
        ObjectOutputStream out = new ObjectOutputStream(fout);
        out.writeObject(authorTree);
        out.flush();
        out.close();
      } catch (Exception e) {
        System.out.println(e);
      }
    }
  }

  private static void serializeDate() {
    if(clear) {
      try {
        FileOutputStream fout = new FileOutputStream("dateTree.txt");
        ObjectOutputStream out = new ObjectOutputStream(fout);
        BinaryTreeTitle emptyTree = new BinaryTreeTitle();
        out.writeObject(emptyTree);
        out.flush();
        out.close();
      } catch (Exception e) {
      }
    } else {
      try {
        FileOutputStream fout = new FileOutputStream("dateTree.txt");
        ObjectOutputStream out = new ObjectOutputStream(fout);
        out.writeObject(titleTree);
        out.flush();
        out.close();
      } catch (Exception e) {
        System.out.println(e);
      }
    }
  }
  
  private static void serializeList() {
    if(clear) {
      try {
        FileOutputStream fout = new FileOutputStream("linkedList.txt");
        ObjectOutputStream out = new ObjectOutputStream(fout);
        LinkedList emptyList = new LinkedList();
        out.writeObject(emptyList);
        out.flush();
        out.close();
      } catch (Exception e) {
      }
    } else {
      try {
        FileOutputStream fout = new FileOutputStream("linkedList.txt");
        ObjectOutputStream out = new ObjectOutputStream(fout);
        out.writeObject(literatureList);
        out.flush();
        out.close();
      } catch (Exception e) {
      }
    }

  }
  
}