package africa.semicolon.promiscuous.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
public class ApiResponse <T>{
    private T data;
}
