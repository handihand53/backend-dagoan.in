package in.dagoan.entity.document;

import in.dagoan.entity.form.KanbanForm;
import in.dagoan.entity.form.ProjectForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = Kanban.COLLECTION_NAME)
public class Kanban {
    public static final String COLLECTION_NAME = "kanban";
    public static final String ID = "id";
    public static final String USER_ID = "userId";
    public static final String KANBAN_NAME = "kanbanName";

    @Id
    @Field(value = ID)
    private UUID projectId;

    @Field(value = USER_ID)
    private UUID userId;

    @Field(value = KANBAN_NAME)
    private List<KanbanForm> kanbanForms;
}
