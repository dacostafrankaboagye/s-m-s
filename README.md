### 1) Problem statement 

A mid-sized university needs a reliable in-memory Student Management System to manage 
- student records, 
- course catalog, 
- enrollments, 
- timetables, 
- grades, 
- attendance, and 
- reporting. 

The system must support 
- fast searches, 
- complex queries (e.g., find students by partial name, students in a course, top N by GPA), 
- concurrent access (multiple services/threads), 
- background jobs (notifications & reports), and 
- provide export/import to files. 

The core implementation must **demonstrate use of the Java Collections Framework** for 
- storage, 
- indexing, 
- caches and 
- job queues 

- while being modular so persistence or a real DB can be added later.

---

### 2) High-level goals & scope

- Maintain student, course, instructor, department, semester and enrollment data in-memory.
- Support CRUD, bulk import/export, complex searches, ranked queries (top students), timetable conflict detection, prerequisite/graph checks, and attendance analytics.
- Use Collections extensively: Lists, Sets, Queues/Deques, Maps (various implementations), PriorityQueue, BitSet, and concurrent collections.
- Build a clean separation: model → repository (in-memory) → services → controllers/CLI.
- Provide testable APIs and thorough unit & performance tests.

---

### 3) Functional requirements

- Create/read/update/delete Students, Courses, Instructors, Departments, Semesters.
- Enroll/unenroll students in courses (per semester).
- Search students by id/name/email/attribute (fast).
- Indexes for fast lookup: by id, by name tokens, by course, by department.
- Grade recording and GPA calculation.
- Attendance tracking per student-per-course.
- Timetable generation & conflict detection.
- Reports: top N students per semester, course roster, attendance summary, grade distribution.
- Background tasks: email notifications queue, nightly analytics job.
- Import/export CSV or JSON.

---

### 4) Non-functional requirements

- Support up to ~50k students, ~2k courses (example target).
- Read-heavy operations must be optimized; some write concurrency expected.
- Expose interfaces for future persistence (DB) swap-in.
- Thread-safety for repositories used by multiple threads.

---

## Solution

- model classes:
    - Student, Course, Enrollment, Instructor, Department, TimeSlot.


- Reasons for certain decisions:
  - `TreeSet<TimeSlot>` -> uses compareTo to maintain natural ordering
      - so Ordering logic:
        - Sort by DayOfWeek (MONDAY → SUNDAY).
        - If same day, sort by startTime.
        - If same start, sort by endTime.
  - `HashSet<String>` -> ensures no duplicate course assignments + constant-time lookups.

- repository design
  - `StudentRepository`
    - responsibilities that I have identified
      - Create and store students. 
      - Fetch by ID. 
      - Search by name tokens. 
      - Delete students with index cleanup
  - `EnrollmentRepository`
    - ConcurrentHashMap -> thread-safe access to main maps.
    - CopyOnWriteArrayList -> safe iteration for read-heavy operations (enrollments per student).
    - ConcurrentHashMap.newKeySet() -> lock-free thread-safe set for quick student membership per course.
    - EnumMap inside Enrollment -> efficient for GradeType enum keys.
  - `DepartmentRepository`
    - ConcurrentHashMap -> Fast concurrent reads/writes for departmentsById. 
    - TreeSet ->  Maintains sorted department IDs for listAll() without extra sorting. 
    - SynchronizedSet wrapper ->  Ensures thread safety for departmentIds.
  - `InstructorRepository`
    - ConcurrentHashMap -> instructorsById (thread-safe ID lookups)
    - HashMap + HashSet -> nameTokenIndex (for search)
    - HashMap + HashSet -> courseToInstructorIds (reverse mapping)

### Directory Structure

```text

/sms-project
  /src/main/java/
    /model
      Student.java
      Course.java
      Enrollment.java
      Instructor.java
      Department.java
      TimeSlot.java
      Notification.java
    /repository
      StudentRepository.java
      InMemoryStudentRepository.java
      CourseRepository.java
      InMemoryCourseRepository.java
      EnrollmentRepository.java
      InMemoryEnrollmentRepository.java
      DepartmentRepository.java
      InMemoryDepartmentRepository.java
      InstructorRepository.java
      InMemoryInstructorRepository.java
      NotificationRepository.java
      InMemoryNotificationRepository.java
    /service
      StudentService.java
      CourseService.java
      EnrollmentService.java
      ReportService.java
      SchedulerService.java
    /api
      RestController (or CLI) classes
    /util
      CsvImporter.java
      CsvExporter.java
      GPAUtils.java
      SearchUtils.java
    /jobs
      Job.java
      JobWorker.java
  /src/test/java/...
  pom.xml
```