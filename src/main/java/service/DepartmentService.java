package service;

import model.Department;

import java.util.List;

/**
 * Service interface for managing academic departments.
 *
 * Responsibilities:
 * - Handles department-related operations.
 * - Delegates persistence to the repository layer.
 */
public interface DepartmentService {

    /**
     * Creates a new department.
     *
     * @param department the Department object to add.
     */
    void createDepartment(Department department);

    /**
     * Retrieves a department by its ID.
     *
     * @param id the department ID (e.g., "CS").
     * @return the Department object, or null if not found.
     */
    Department getDepartmentById(String id);

    /**
     * Lists all departments in the system.
     *
     * @return a list of Department objects.
     */
    List<Department> listAllDepartments();
}
