package com.blibli.experience.entity.form;

import com.blibli.experience.enums.ProductAvailableStatus;
import com.blibli.experience.enums.ProductBarterCondition;
import com.blibli.experience.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductBarterDataForm {

    private UUID productBarterId;
    private UserDataForm userData;
    private String productBarterName;
    private String productBarterBrand;
    private String productBarterDescription;
    private Double productBarterWeight;
    private String productBarterVolume;
    private ProductCategory productCategory;
    private String productBarterPreference;
    private String productBarterPackage;
    private ProductBarterCondition productBarterCondition;
    private List<String> productBarterImagePaths;
    private ProductAvailableStatus availableStatus;
    private LocalDateTime productBarterCreatedAt;
}
