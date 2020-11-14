package in.dagoan.entity.document;

import in.dagoan.entity.form.KanbanForm;
import in.dagoan.entity.form.LabelForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = Label.COLLECTION_NAME)
public class Label {
    public static final String COLLECTION_NAME = "label";
    public static final String ID = "labelId";
    public static final String USER_ID = "userId";
    public static final String PROJECT_ID = "projectId";
    public static final String LABEL_FORM = "labelForm";

    @Id
    @Field(value = ID)
    private UUID labelId;

    @Field(value = USER_ID)
    private UUID userId;

    @Field(value = PROJECT_ID)
    private UUID projectId;

    @Field(value = LABEL_FORM)
    private List<LabelForm> labelForm;

}
