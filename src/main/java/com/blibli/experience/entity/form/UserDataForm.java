package com.blibli.experience.entity.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDataForm {

    private UUID userId;
    private String userEmail;
    private String userName;
    private List<AddressForm> userAddressForms;
    private String userPhoneNumber;
    private String userIdentityId;

}
