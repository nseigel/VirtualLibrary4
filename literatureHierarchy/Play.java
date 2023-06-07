import java.time.LocalDate;
import java.io.Serializable;

public class Play extends Book {
  private int numberOfActs;

  public Play(String title, LocalDate publishDate, String authorFirstName, String authorLastName, int numberOfPages, int numberOfActs) {
    super(title, publishDate, "PLAY", authorFirstName, authorLastName, numberOfPages);
    this.numberOfActs = numberOfActs;
    if(Book.numberOfDigits(numberOfActs) > Literature.maxes[Literature.ACTS]) {
      Literature.maxes[Literature.ACTS] = Book.numberOfDigits(numberOfActs);
    } if(authorFirstName.length() > Literature.maxes[Literature.AUTHOR_FIRST]) {
      Literature.maxes[Literature.AUTHOR_FIRST] = authorFirstName.length();
    } if(authorLastName.length() > Literature.maxes[Literature.AUTHOR_LAST]) {
      Literature.maxes[Literature.AUTHOR_LAST] = authorLastName.length();
    } if(Book.numberOfDigits(numberOfPages) > Literature.maxes[Literature.PAGES]) {
      Literature.maxes[Literature.PAGES] = Book.numberOfDigits(numberOfPages);
    }
  }

  public int getNumberOfActs() {
    return numberOfActs;
  }

  public String toString(boolean single) {
    if(!single) {
      String format = super.toString(single) + " | Number of acts: %-" + Literature.maxes[Literature.ACTS] + "d";
      return String.format(format, numberOfActs);
    } else {
      return String.format(super.toString(single) + " | Number of acts: %d", numberOfActs);
    }
      
  }

  public boolean checkPlay(Play second) {
    if(this.checkBook(second) && (numberOfActs == second.getNumberOfActs())) { 
      return true;
    } else {
      return false;
    }
  }
  
}