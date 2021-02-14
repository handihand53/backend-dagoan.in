package in.dagoan.entity.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatForm {
    private UUID chatId;
    private UUID userId;
    private int section;
    private String userName;
    private String text;
}
