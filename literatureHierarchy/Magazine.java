import java.time.LocalDate;
import java.io.Serializable;

public class Magazine extends Literature {
  private String publisher;

  public Magazine(String title, LocalDate publishDate, String publisher) {
    super(title, publishDate, "MAGAZINE");
    this.publisher = trim(publisher, 20);
    if (publisher.length() > super.maxes[PUBLISHER]) {
      super.maxes[PUBLISHER] = publisher.length();
    }
  }

  public String getPublisher() {
    return publisher;
  }

  public String toString(boolean single) { 
    if(!single) {
      String format = "Type: MAGAZINE | Title: %-" + super.maxes[super.TITLE] + "s | Publisher: %-" + super.maxes[super.PUBLISHER] + "s | Publish date: %s";
      return String.format(format, getTitle(), publisher, getPublishDate().toString());
    } else {
      return String.format("Type: MAGAZINE | Title: %s | Publisher: %s | Publish date: %s", getTitle(), publisher, getPublishDate().toString());
    }
      
  }

  public boolean checkMagazine(Magazine second) {
    if(this.getTitle().equals(second.getTitle()) && this.getPublishDate().equals(second.getPublishDate()) && publisher.equals(second.getPublisher())) {
      return true;
    } else {
      return false;
    }
  }

}