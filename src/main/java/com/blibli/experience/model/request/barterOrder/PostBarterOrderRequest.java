package com.blibli.experience.model.request.barterOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostBarterOrderRequest {

    @NotNull
    private UUID barterSubmissionId;

}
