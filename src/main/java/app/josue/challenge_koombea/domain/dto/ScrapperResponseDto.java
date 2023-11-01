package app.josue.challenge_koombea.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ScrapperResponseDto {

  private String id;
  private String url;
  private List<ScrapperLinkDto> linksFound;
  private Boolean isDone;
  private Boolean isError;
  private String error;

}
