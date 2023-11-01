package app.josue.challenge_koombea.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
@Data
@AllArgsConstructor
public class UserEntity {

  @Id
  private String id;
  private String username;
  private String name;
  private String lastname;
  private String password;
  private boolean isEnabled;

  public UserEntity copyForEncryption(String password) {
    return new UserEntity(null, this.username, this.name, this.lastname, password, this.isEnabled);
  }

}
