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
@Document(collection = Member.COLLECTION_NAME)
public class Member {
    public static final String COLLECTION_NAME = "member";
    public static final String ID = "memberId";
    public static final String USER_ID = "userId";
    public static final String LIST_PROJECT = "listProject";
    public static final String PENDING_PROJECT = "pendingProject";

    @Id
    @Field(value = ID)
    private UUID memberId;

    @Field(value = USER_ID)
    private UUID userId;

    @Field(value = LIST_PROJECT)
    private List<UUID> listProjectId;

    @Field(value = PENDING_PROJECT)
    private List<UUID> pendingProjectId;
}
