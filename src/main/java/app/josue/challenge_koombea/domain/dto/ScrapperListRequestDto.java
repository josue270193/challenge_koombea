package app.josue.challenge_koombea.domain.dto;

import lombok.Data;

@Data
public class ScrapperListRequestDto {

  private Integer pageNumber = 0;
  private Integer pageSize = 10;

}
