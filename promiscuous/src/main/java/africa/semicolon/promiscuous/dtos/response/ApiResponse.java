package africa.semicolon.promiscuous.dtos.response;

import lombok.*;

@Builder
@Setter
@Getter
public class ApiResponse <T>{
    private T data;
}
