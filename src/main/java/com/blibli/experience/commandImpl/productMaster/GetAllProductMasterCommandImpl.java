package com.blibli.experience.commandImpl.productMaster;

import com.blibli.experience.command.productMaster.GetAllProductMasterCommand;
import com.blibli.experience.entity.document.ProductMaster;
import com.blibli.experience.model.response.productMaster.GetAllProductMasterResponse;
import com.blibli.experience.repository.ProductMasterRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class GetAllProductMasterCommandImpl implements GetAllProductMasterCommand {

    private ProductMasterRepository productMasterRepository;

    @Autowired
    public GetAllProductMasterCommandImpl(ProductMasterRepository productMasterRepository) {
        this.productMasterRepository = productMasterRepository;
    }

    @Override
    public Mono<List<GetAllProductMasterResponse>> execute(Integer skipCount) {
        return productMasterRepository.findAll()
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found.")))
                .skip(skipCount)
                .take(20)
                .map(this::toResponse)
                .collectList();
    }

    private GetAllProductMasterResponse toResponse(ProductMaster productMaster) {
        GetAllProductMasterResponse response = new GetAllProductMasterResponse();
        BeanUtils.copyProperties(productMaster, response);
        return response;
    }

}
