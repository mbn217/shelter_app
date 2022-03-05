package com.ziola.shelter.util;

import com.ziola.shelter.workers.domain.Worker;
import com.ziola.shelter.workers.dto.WorkerDTO;
import com.ziola.shelter.workers.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConverterDtoWorkerEntity {

  private ModelMapper modelMapper;
  private RoleRepository roleRepository;

  @Autowired
  public ConverterDtoWorkerEntity(ModelMapper modelMapper, RoleRepository roleRepository) {
    this.modelMapper = modelMapper;
    this.roleRepository = roleRepository;
  }

  public Worker convertToEntity(WorkerDTO workerDTO) {
    return modelMapper.map(workerDTO, Worker.class);
  }

  public WorkerDTO convertToDto(Worker worker) {
    return modelMapper.map(worker, WorkerDTO.class);
  }
}
