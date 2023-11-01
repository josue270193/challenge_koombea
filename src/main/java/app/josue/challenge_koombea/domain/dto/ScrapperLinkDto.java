package app.josue.challenge_koombea.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScrapperLinkDto {

  private String href;
  private String text;

}
