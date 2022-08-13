package graphql.api.DTOs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddLikeDTOInput {
  private Integer likeVideo;
  private String videoId;
  private String event;
  private String email;
  private Integer unlikeVideo;
}
