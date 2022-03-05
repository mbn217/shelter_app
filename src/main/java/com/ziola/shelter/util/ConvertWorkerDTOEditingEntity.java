package com.ziola.shelter.util;

import com.ziola.shelter.workers.domain.Worker;
import com.ziola.shelter.workers.dto.WorkerDTOEditing;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConvertWorkerDTOEditingEntity {

    private ModelMapper modelMapper;

    @Autowired
    public ConvertWorkerDTOEditingEntity(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public WorkerDTOEditing convertToDto(Worker worker) {
        WorkerDTOEditing workerDTOEditing = modelMapper.map(worker, WorkerDTOEditing.class);
        workerDTOEditing.setRoleName(worker.getRole().getRole());
        return workerDTOEditing;
    }
}
