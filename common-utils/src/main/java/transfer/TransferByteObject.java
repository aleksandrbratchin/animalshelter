package transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * для передачи файлов через rabbit
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransferByteObject {

    @JsonProperty("chat_id")
    private String chatId;

    @JsonProperty("byte_array")
    private byte[] data;

}
