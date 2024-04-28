package transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * для передачи файлов через rabbit
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransferByteObject {

    @JsonProperty("chat_id")
    private String chatId;

    @JsonProperty("byte_array")
    private byte[] data;

}
