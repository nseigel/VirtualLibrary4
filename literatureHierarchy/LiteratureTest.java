import java.time.LocalDate;

public class LiteratureTest {

  private static LocalDate testDate = LocalDate.of(2000, 1, 1);

  public static void testBook() {
    System.out.println("Testing book class:");
    Book testBook = new Book("title", testDate, "first", "last", 300);
    System.out.println(testBook.toString());
    System.out.println();

  }

  public static void testNovel() {
    System.out.println("Testing novel class:");
    Novel testNovel = new Novel("title", testDate, "first", "last", 300, 10);
    System.out.println(testNovel.toString());
    System.out.println();
  }

  public static void testComic() {
    System.out.println("Testing comic class:");
    Comic testBook = new Comic("title", testDate, "first", "last", 300, "anotherFirst", "anotherLast");
    System.out.println(testBook.toString());
    System.out.println();
  }

  public static void testMagazine() {
    System.out.println("Testing magazine class:");
    Magazine testBook = new Magazine("title", testDate, "publisher");
    System.out.println(testBook.toString());
    System.out.println();
  }

  public static void testPoetry() {
    System.out.println("Testing poetry class:");
    Poetry testBook = new Poetry("title", testDate, "first", "last", 300);
    System.out.println(testBook.toString());
    System.out.println();
  }

}