package com.ziola.shelter.util;

import com.ziola.shelter.workers.Worker;
import com.ziola.shelter.workers.WorkerDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConverterDtoWorkerEntity {

  private final ModelMapper modelMapper;


  public Worker convertToEntity(WorkerDTO workerDTO) {
    return modelMapper.map(workerDTO, Worker.class);
  }

  public WorkerDTO convertToDto(Worker worker) {
    return modelMapper.map(worker, WorkerDTO.class);
  }
}
