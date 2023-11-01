package app.josue.challenge_koombea.domain.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("scrappers")
@Data
@AllArgsConstructor
public class ScrapperEntity {

  @Id
  private String id;
  private String username;
  private String url;
  private List<ScrapperLinkEntity> linksFound;
  private Boolean isDone;
  private Boolean isError;
  private String error;

}
