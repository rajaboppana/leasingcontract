package com.allane.leasingcontract.service.impl;

import com.allane.leasingcontract.entity.ContractEntity;
import com.allane.leasingcontract.model.ContractDTO;
import com.allane.leasingcontract.repository.ContractRepository;
import com.allane.leasingcontract.service.ContractService;
import com.allane.leasingcontract.service.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class ContractServiceImpl implements ContractService {

    private ContractRepository contractRepository;

    private EntityManager em;

    public ContractServiceImpl(ContractRepository contractRepository, EntityManager em) {
        this.contractRepository = contractRepository;
        this.em = em;
    }

    @Override
    public List<ContractDTO> getContracts() {
        List<ContractEntity> contracts = contractRepository.findAll();
        List<ContractDTO> contractDTOList = new ArrayList<>();
        if (!contracts.isEmpty()) {
            for (ContractEntity contract : contracts) {
                contractDTOList.add(ContractTransformer.convertToDTO(contract));
            }
        }

        if (contractDTOList.isEmpty()) {
            throw new RuntimeException("There are no contracts available at this moment");
        }

        return contractDTOList;
    }

    @Override
    public ContractDTO createContract(ContractDTO contractDTO) {

        ContractEntity contractEntity = ContractTransformer.convertToEntity(contractDTO);
        ContractEntity savedContract = contractRepository.save(contractEntity);
        em.refresh(savedContract);
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
        em.refresh(updatedContract);
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
