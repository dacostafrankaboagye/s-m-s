# Problem statement
- A mid-sized university needs a reliable in-memory Student Management System 
- to manage student records, course catalog, enrollments, timetables, grades, 
- attendance, and reporting. The system must support fast searches, complex queries 
- (e.g., find students by partial name, students in a course, top N by GPA), 
- concurrent access (multiple services/threads), background jobs (notifications & reports), 
- and provide export/import to files. The core implementation must 
- demonstrate use of the Java Collections Framework for storage, indexing, caches and job queues while 
- being modular so persistence or a real DB can be added later.