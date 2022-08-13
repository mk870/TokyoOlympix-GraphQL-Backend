package graphql.api.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTOInput {
  private String comment;
  private String event;
  private String videoId;
  private String email;
}
