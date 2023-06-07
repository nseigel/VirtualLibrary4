import java.time.LocalDate;
import java.io.Serializable;

public class Poetry extends Book {

  public Poetry(String title, LocalDate publishDate, String authorFirstName, String authorLastName, int numberOfPages) {
    super(title, publishDate, "POETRY", authorFirstName, authorLastName, numberOfPages);
    if(authorFirstName.length() > Literature.maxes[Literature.AUTHOR_FIRST]) {
      Literature.maxes[Literature.AUTHOR_FIRST] = authorFirstName.length();
    } if(authorLastName.length() > Literature.maxes[Literature.AUTHOR_LAST]) {
      Literature.maxes[Literature.AUTHOR_LAST] = authorLastName.length();
    } if(numberOfDigits(numberOfPages) > Literature.maxes[Literature.PAGES]) {
      Literature.maxes[Literature.PAGES] = numberOfDigits(numberOfPages);
    }
  }

  public boolean checkPoetry(Poetry second) {
    if(this.getTitle().equals(second.getTitle()) && this.getPublishDate().equals(second.getPublishDate()) && this.getAuthorFirstName().equals(second.getAuthorFirstName()) && this.getAuthorLastName().equals(second.getAuthorLastName()) && (this.getNumberOfPages() == second.getNumberOfPages())) {
      return true;
    } else {
      return false;
    }
  }

}