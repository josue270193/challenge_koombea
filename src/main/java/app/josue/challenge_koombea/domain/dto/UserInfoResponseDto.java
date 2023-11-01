package app.josue.challenge_koombea.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoResponseDto {

  private String id;
  private String username;
  private String name;
  private String lastname;
  private Boolean isEnabled;

}
