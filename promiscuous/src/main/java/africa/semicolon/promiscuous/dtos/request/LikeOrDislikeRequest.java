package africa.semicolon.promiscuous.dtos.request;

import africa.semicolon.promiscuous.models.Reaction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeOrDislikeRequest {
    private Reaction reaction;
}