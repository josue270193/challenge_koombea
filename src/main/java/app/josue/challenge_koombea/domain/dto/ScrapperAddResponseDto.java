package app.josue.challenge_koombea.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScrapperAddResponseDto {

  private String id;
  private String url;
  private Boolean isDone;

}
