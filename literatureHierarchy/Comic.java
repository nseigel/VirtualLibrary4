import java.time.LocalDate;
import java.io.Serializable;

public class Comic extends Book {
  private String illustratorFirstName;
  private String illustratorLastName;

  public Comic(String title, LocalDate publishDate, String authorFirstName, String authorLastName, int numberOfPages, String illustratorFirstName, String illustratorLastName) {
    super(title, publishDate, "COMIC", authorFirstName, authorLastName, numberOfPages);
    this.illustratorFirstName = trim(illustratorFirstName, 20);
    this.illustratorLastName = trim(illustratorLastName, 20);
    if(illustratorFirstName.length() > Literature.maxes[Literature.ILLUSTRATOR_FIRST]) {
      Literature.maxes[Literature.ILLUSTRATOR_FIRST] = illustratorFirstName.length();
    } if(illustratorLastName.length() > Literature.maxes[ILLUSTRATOR_LAST]) {
      Literature.maxes[ILLUSTRATOR_LAST] = illustratorLastName.length();
    } if(authorFirstName.length() > Literature.maxes[Literature.AUTHOR_FIRST]) {
      Literature.maxes[Literature.AUTHOR_FIRST] = authorFirstName.length();
    } if(authorLastName.length() > Literature.maxes[Literature.AUTHOR_LAST]) {
      Literature.maxes[Literature.AUTHOR_LAST] = authorLastName.length();
    } if(numberOfDigits(numberOfPages) > Literature.maxes[Literature.PAGES]) {
      Literature.maxes[Literature.PAGES] = numberOfDigits(numberOfPages);
    }
  }

  public String getIllustratorFirstName() {
    return illustratorFirstName;
  }

  public String getIllustratorLastName() {
    return illustratorLastName;
  }

  public String toString(boolean single) {
    if(!single) {
      String format = super.toString(single) + " | Illustrator: %-" + Literature.maxes[Literature.ILLUSTRATOR_FIRST] + "s %-" + Literature.maxes[Literature.ILLUSTRATOR_LAST] + "s";
      return String.format(format, illustratorFirstName, illustratorLastName);
    } else {
      return String.format(super.toString(single) + " | Illustrator: %s %s", illustratorFirstName, illustratorLastName);
    }
      
  }

  public boolean checkComic(Comic second) {
    if(this.checkBook(second) && illustratorFirstName.equals(second.getIllustratorFirstName()) && illustratorLastName.equals(second.getIllustratorLastName())) { 
      return true;
    } else {
      return false;
    }
  }
  
}