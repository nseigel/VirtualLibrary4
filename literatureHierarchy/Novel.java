import java.time.LocalDate;
import java.io.Serializable;

public class Novel extends Book {
  private int numberOfChapters;

  public Novel(String title, LocalDate publishDate, String authorFirstName, String authorLastName, int numberOfPages, int numberOfChapters) {
    super(title, publishDate, "NOVEL", authorFirstName, authorLastName, numberOfPages);
    this.numberOfChapters = numberOfChapters;
    if(super.numberOfDigits(numberOfChapters) > Literature.maxes[Literature.CHAPTERS]) {
      Literature.maxes[Literature.CHAPTERS] = super.numberOfDigits(numberOfChapters);
    } if(authorFirstName.length() > Literature.maxes[Literature.AUTHOR_FIRST]) {
      Literature.maxes[Literature.AUTHOR_FIRST] = authorFirstName.length();
    } if(authorLastName.length() > Literature.maxes[Literature.AUTHOR_LAST]) {
      Literature.maxes[Literature.AUTHOR_LAST] = authorLastName.length();
    } if(numberOfDigits(numberOfPages) > Literature.maxes[Literature.PAGES]) {
      Literature.maxes[Literature.PAGES] = numberOfDigits(numberOfPages);
    }
  }

  public int getNumberOfChapters() {
    return numberOfChapters;
  }

  public String toString(boolean single) {
    if(!single) {
      String format = super.toString(single) + " | Number of chapters: %-" + Literature.maxes[Literature.CHAPTERS] + "d";
      return String.format(format, numberOfChapters);
    } else {
      return String.format(super.toString(single) + " | Number of chapters: %d", numberOfChapters);
    }
      
  }

  public boolean checkNovel(Novel second) {
    if(this.checkBook(second) && (numberOfChapters == second.getNumberOfChapters())) { 
      return true;
    } else {
      return false;
    }
  }
  
}