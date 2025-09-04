package service;

import lombok.AllArgsConstructor;
import model.Department;
import repository.DepartmentRepository;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService{

    private final DepartmentRepository departmentRepository;

    @Override
    public void createDepartment(Department department) {
        if (department == null || department.getId() == null || department.getName() == null) {
            throw new IllegalArgumentException("Department and its ID/Name cannot be null");
        }
        departmentRepository.createDepartment(department);
    }

    @Override
    public Department getDepartmentById(String id) {
        return departmentRepository.getById(id);
    }

    @Override
    public List<Department> listAllDepartments() {
        return new ArrayList<>(departmentRepository.listAll());
    }

    @Override
    public void deleteDepartment(String id) {
        departmentRepository.deleteDepartment(id);

    }
}
