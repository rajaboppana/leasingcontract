package com.allane.leasingcontract.service.impl;

import com.allane.leasingcontract.entity.ContractEntity;
import com.allane.leasingcontract.model.ContractDTO;
import com.allane.leasingcontract.repository.ContractRepository;
import com.allane.leasingcontract.service.ContractService;
import com.allane.leasingcontract.service.exception.ResourceNotFoundException;
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

    @Override
    public ContractDTO createContract(ContractDTO contractDTO) {
        ContractEntity contractEntity = modelMapper.map(contractDTO, ContractEntity.class);
        ContractEntity savedContract = contractRepository.save(contractEntity);
        return modelMapper.map(savedContract, ContractDTO.class);
    }

    @Override
    public ContractDTO updateContract(int id, ContractDTO contractDTO) throws ResourceNotFoundException {
        ContractEntity existingContract = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + id));

        modelMapper.map(contractDTO, existingContract);
        ContractEntity updatedContract = contractRepository.save(existingContract);

        return modelMapper.map(updatedContract, ContractDTO.class);
    }

    @Override
    public ContractDTO getContract(int id) throws ResourceNotFoundException {
        ContractEntity contractEntity = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + id));

        return modelMapper.map(contractEntity, ContractDTO.class);
    }

    @Override
    public void deleteContract(int id) throws ResourceNotFoundException {
        ContractEntity contractEntity = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + id));
        contractRepository.delete(contractEntity);
    }
}
