package net.medical.service;

import net.medical.model.Department;
import net.medical.model.User;

import java.util.List;

public interface DepartmentService {

    List<Department> getAll();

    Department getById(int id);
}
