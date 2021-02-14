package in.dagoan.entity.document;

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
@Document(collection = Invited.COLLECTION_NAME)
public class Invited {
    public static final String COLLECTION_NAME = "invited";
    public static final String ID = "invitedId";
    public static final String PROJECT_ID = "projectId";
    public static final String USER_ID = "userId";

    @Id
    @Field(value = ID)
    private UUID invitedId;

    @Field(value = PROJECT_ID)
    private UUID projectId;

    @Field(value = USER_ID)
    private List<UUID> userId;
}
