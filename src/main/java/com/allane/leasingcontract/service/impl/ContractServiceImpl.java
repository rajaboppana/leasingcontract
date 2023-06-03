package com.allane.leasingcontract.service.impl;

import com.allane.leasingcontract.entity.ContractEntity;
import com.allane.leasingcontract.model.ContractDTO;
import com.allane.leasingcontract.repository.ContractRepository;
import com.allane.leasingcontract.service.ContractService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContractServiceImpl implements ContractService {

    private ContractRepository contractRepository;
    private ModelMapper modelMapper;

    public ContractServiceImpl(ContractRepository contractRepository, ModelMapper modelMapper) {
        this.contractRepository = contractRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ContractDTO> getContracts() {
       List<ContractEntity> contracts = contractRepository.findAll();
        List<ContractDTO> contractDTOList = new ArrayList<ContractDTO>();
       if(!contracts.isEmpty()){
           for (ContractEntity contract:contracts) {
               contractDTOList.add(modelMapper.map(contract, ContractDTO.class));
           }
       }

       if(contractDTOList.isEmpty()){
           throw new RuntimeException("There are no contracts available at this moment");
       }

        return contractDTOList;
    }
}
