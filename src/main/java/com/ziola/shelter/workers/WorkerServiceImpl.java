package com.ziola.shelter.workers;

import com.ziola.shelter.exceptions.EmailExistsException;
import com.ziola.shelter.role.Role;
import com.ziola.shelter.role.RoleRepository;
import com.ziola.shelter.util.ConvertWorkerDTOEditingEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConvertWorkerDTOEditingEntity converterDtoWorkerEntity;

    @Override
    public Worker registerNewUserAccount(WorkerDTO workerDTO) throws EmailExistsException {
        if (emailExists(workerDTO.getEmail())) {
            throw new EmailExistsException("Już istnieje konto przypisanego do tego adresu email: " + workerDTO.getEmail());
        }
        Worker worker = new Worker();
        worker.setName(workerDTO.getName());
        worker.setLastName(workerDTO.getLastName());
        worker.setEmail(workerDTO.getEmail());
        worker.setPassword(setEncodePasswordNewWorker(workerDTO));
        worker.setRole(roleRepository.findByRole("User"));
        return workerRepository.save(worker);
    }

    @Override
    public void saverRegisteredWorker(Worker worker) {
        worker.setActive(true);
        workerRepository.save(worker);
    }

    @Override
    public Worker createWorkerAccount(WorkerDTO workerDTO) {
        Worker registered;
        try {
            registered = registerNewUserAccount(workerDTO);
        } catch (EmailExistsException emailExistsException) {
            return null;
        }
        return registered;
    }

    public void saveWorker(Worker worker, String role) {
        Role workerRole = roleRepository.findByRole(role);
        worker.setRole(workerRole);
        workerRepository.save(worker);
    }

    public boolean checkIfExistsThenSave(Worker worker) {
        if (emailExists(worker.getEmail())) {
            return false;
        }else{
            workerRepository.save(worker);
            return true;
        }
    }

    @Override
    public void deleteById(int id) {
        workerRepository.deleteById(id);
    }

    @Override
    public Object findAll() {
        return workerRepository.findAll();
    }

    @Override
    public WorkerDTOEditing findByIdAndConvertToDTO(int workerId) {
        Worker workerFound = workerRepository.findById(workerId);
        return converterDtoWorkerEntity.convertToDto(workerFound);
    }

    private boolean emailExists(String email) {
        Worker worker = workerRepository.findByEmail(email);
        return worker != null;
    }


    private String setEncodePasswordNewWorker(WorkerDTO workerDTO) {
        return bCryptPasswordEncoder.encode(workerDTO.getPassword());
    }
}
