package app.josue.challenge_koombea.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserCreationRequestDto {

  @NotEmpty(message = "username must be not empty.")
  private String username;

  @NotEmpty(message = "name must be not empty.")
  private String name;

  @NotEmpty(message = "lastname must be not empty.")
  private String lastname;

  @NotEmpty(message = "password must be not empty.")
  private String password;

}
