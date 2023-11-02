package app.josue.challenge_koombea.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ScrapperResponseDto {

  private String id;
  private String url;
  private List<ScrapperLinkDto> linksFound;
  private Boolean isDone;
  private Boolean isError;
  private String error;

}
