package in.dagoan.entity.document;

import in.dagoan.entity.form.MemberForm;
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
@Document(collection = Project.COLLECTION_NAME)
public class Project {
    public static final String COLLECTION_NAME = "project";
    public static final String ID = "id";
    public static final String USER_ID = "userId";
    public static final String PROJECTS = "projects";
    public static final String CREATED_AT = "createdAt";

    @Id
    @Field(value = ID)
    private UUID projectId;

    @Field(value = USER_ID)
    private UUID userId;

    @Field(value = PROJECTS)
    private ProjectForm projects;

    @Field(value = CREATED_AT)
    private LocalDateTime createdAt;
}
