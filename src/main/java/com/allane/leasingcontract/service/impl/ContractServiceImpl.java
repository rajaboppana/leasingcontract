package com.allane.leasingcontract.service.impl;

import com.allane.leasingcontract.entity.ContractEntity;
import com.allane.leasingcontract.model.ContractDTO;
import com.allane.leasingcontract.repository.ContractRepository;
import com.allane.leasingcontract.service.ContractService;
import com.allane.leasingcontract.service.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class ContractServiceImpl implements ContractService {

    private ContractRepository contractRepository;

    private EntityManager entityManager;

    public ContractServiceImpl(ContractRepository contractRepository, EntityManager entityManager) {
        this.contractRepository = contractRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<ContractDTO> getContracts() throws ResourceNotFoundException {
        List<ContractEntity> contracts = contractRepository.findAll();
        List<ContractDTO> contractDTOList = contracts.stream()
                .map(ContractTransformer::convertToDTO)
                .collect(Collectors.toList());

        if (contractDTOList.isEmpty()) {
            throw new ResourceNotFoundException("There are no contracts available at this moment");
        }

        return contractDTOList;
    }

    @Override
    public ContractDTO createContract(ContractDTO contractDTO) {

        ContractEntity contractEntity = ContractTransformer.convertToEntity(contractDTO);
        ContractEntity savedContract = contractRepository.save(contractEntity);
        entityManager.refresh(savedContract);
        return ContractTransformer.convertToDTO(savedContract);
    }

    @Override
    public ContractDTO updateContract(int id, ContractDTO contractDTO) throws ResourceNotFoundException {
        ContractEntity existingContract = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + id));

        ContractTransformer.updateEntityFromDTO(existingContract, contractDTO);
        if (contractDTO.getVehicle() == null) {
            existingContract.setVehicle(null);
        }
        if (contractDTO.getCustomer() == null) {
            existingContract.setCustomer(null);
        }
        ContractEntity updatedContract = contractRepository.save(existingContract);
        entityManager.refresh(updatedContract);
        return ContractTransformer.convertToDTO(updatedContract);
    }

    @Override
    public ContractDTO getContract(int id) throws ResourceNotFoundException {
        ContractEntity contractEntity = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + id));

        return ContractTransformer.convertToDTO(contractEntity);
    }

    @Override
    public void deleteContract(int id) throws ResourceNotFoundException {
        ContractEntity contractEntity = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + id));
        contractRepository.delete(contractEntity);
    }

}
