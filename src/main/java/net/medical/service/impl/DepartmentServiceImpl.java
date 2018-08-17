package net.medical.service.impl;

import net.medical.model.Department;
import net.medical.repository.DepartmentRepository;
import net.medical.service.DepartmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger LOGGER  = LogManager.getLogger();

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getById(int id) {
        Optional<Department> dep = departmentRepository.findById(id);
        return dep.isPresent() ? dep.get() : null;
    }
}
