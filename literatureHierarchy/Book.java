import java.time.LocalDate;
import java.io.Serializable;
import java.util.ArrayList;

public class Book extends Literature {

  private String authorFirstName;
  private String authorLastName;
  private int numberOfPages;
  
  public Book(String title, LocalDate publishDate, String authorFirstName, String authorLastName, int numberOfPages) {
    super(title, publishDate, "BOOK");
    this.authorFirstName = trim(authorFirstName, 20);
    this.authorLastName = trim(authorLastName, 20);
    this.numberOfPages = numberOfPages;
    if(authorFirstName.length() > super.maxes[super.AUTHOR_FIRST]) {
      super.maxes[super.AUTHOR_FIRST] = authorFirstName.length();
    } if(authorLastName.length() > super.maxes[super.AUTHOR_LAST]) {
      super.maxes[super.AUTHOR_LAST] = authorLastName.length();
    } if(numberOfDigits(numberOfPages) > super.maxes[super.PAGES]) {
      super.maxes[super.PAGES] = numberOfDigits(numberOfPages);
    }
  }

  public Book(String title, LocalDate publishDate, String type, String authorFirstName, String authorLastName, int numberOfPages) {
    super(title, publishDate, type);
    this.authorFirstName = trim(authorFirstName, 20);
    this.authorLastName = trim(authorLastName, 20);
    this.numberOfPages = numberOfPages;
  }

  public String getAuthorFirstName() {
    return authorFirstName;
  }

  public String getAuthorLastName() {
    return authorLastName;
  }

  public int getNumberOfPages() {
    return numberOfPages;
  }

  public String toString(boolean single) {
    if(!single) {
      String format = "Type: %-" + super.maxes[super.TYPE] + "s | Title: %-" + super.maxes[super.TITLE] + "s | Author: %-" + super.maxes[super.AUTHOR_FIRST] + "s %-" + super.maxes[super.AUTHOR_LAST] + "s | Publish date: %s | Number of pages: %-" + super.maxes[super.PAGES] + "d";
      return String.format(format, getType(), getTitle(), authorFirstName, authorLastName, getPublishDate().toString(), numberOfPages);
    } else {
      return String.format("Type: %s | Title: %s | Author: %s %s | Publish date: %s | Number of pages: %d", getType(), getTitle(), authorFirstName, authorLastName, getPublishDate().toString(), numberOfPages); 
    }
  }

  public int compareByAuthor(Book other) {
    return authorLastName.compareTo(other.getAuthorLastName());
  }

  public boolean checkBook(Book second) {
    if(this.getTitle().equals(second.getTitle()) && this.getPublishDate().equals(second.getPublishDate()) && authorFirstName.equals(second.getAuthorFirstName()) && authorLastName.equals(second.getAuthorLastName()) && (numberOfPages == second.getNumberOfPages())) {
      return true;
    } else {
      return false;
    }
  }

  public static int numberOfDigits(int numberOfPages) {
    return Integer.toString(numberOfPages).length();
  }

}