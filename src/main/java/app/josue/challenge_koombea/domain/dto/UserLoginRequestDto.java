package app.josue.challenge_koombea.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserLoginRequestDto {

  @NotEmpty(message = "username must be not empty.")
  private String username;

  @NotEmpty(message = "password must be not empty.")
  private String password;

}
