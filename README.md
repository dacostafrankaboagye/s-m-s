# Student Management System (SMS)

## Overview

An in-memory Student Management System demonstrating extensive use of Java Collections Framework for managing student records, courses, enrollments, and departments in a university setting.

## Current Implementation Status

### Implemented Components

#### Models
- **Student** - Student records with enrollments and attributes
- **Course** - Course catalog with prerequisites and time slots
- **Enrollment** - Student-course enrollments with grades and attendance
- **Department** - Academic departments
- **Instructor** - Faculty information
- **Notification** - System notifications
- **TimeSlot** - Scheduled class times
- **Enums**: EnrollmentStatus, GradeType
- **DTOs**: StudentGpa

#### Repositories (In-Memory)
- **StudentRepository** & **InMemoryStudentRepository** - Student data management
- **CourseRepository** & **InMemoryCourseRepository** - Course catalog management
- **EnrollmentRepository** & **InMemoryEnrollmentRepository** - Enrollment tracking
- **DepartmentRepository** & **InMemoryDepartmentRepository** - Department management
- **InstructorRepository** & **InMemoryInstructorRepository** - Instructor management
- **NotificationRepository** & **InMemoryNotificationRepository** - Notification handling

#### Services
- **StudentService** & **StudentServiceImpl** - Student business logic
- **CourseService** & **CourseServiceImpl** - Course operations
- **EnrollmentService** & **EnrollmentServiceImpl** - Enrollment management
- **DepartmentService** & **DepartmentServiceImpl** - Department operations

#### Utilities
- **GPAUtils** - GPA calculation and grade conversion
- **StringUtils** - String tokenization for search

## Architecture Layers

```mermaid
graph TB
    subgraph "Service Layer"
        SS[StudentService]
        CS[CourseService]
        ES[EnrollmentService]
        DS[DepartmentService]
    end
    
    subgraph "Repository Layer"
        SR[StudentRepository]
        CR[CourseRepository]
        ER[EnrollmentRepository]
        DR[DepartmentRepository]
        NR[NotificationRepository]
        IR[InstructorRepository]
    end
    
    subgraph "Model Layer"
        STUDENT[Student]
        COURSE[Course]
        ENROLLMENT[Enrollment]
        DEPARTMENT[Department]
        INSTRUCTOR[Instructor]
        NOTIFICATION[Notification]
        TIMESLOT[TimeSlot]
    end
    
    subgraph "Utility Layer"
        GPAUTILS[GPAUtils]
        STRINGUTILS[StringUtils]
    end
    
    SS --> SR
    CS --> CR
    ES --> ER
    DS --> DR
    
    SR --> STUDENT
    CR --> COURSE
    ER --> ENROLLMENT
    DR --> DEPARTMENT
    NR --> NOTIFICATION
    IR --> INSTRUCTOR
    
    SS --> GPAUTILS
    SS --> STRINGUTILS
    ES --> GPAUTILS
```



## Detailed Entity Relationships

```mermaid
erDiagram
    STUDENT ||--o{ ENROLLMENT : "enrolls in"
    COURSE ||--o{ ENROLLMENT : "has students"
    DEPARTMENT ||--o{ COURSE : "offers"
    INSTRUCTOR ||--o{ COURSE : "teaches"
    COURSE ||--o{ TIMESLOT : "scheduled at"
    COURSE ||--o{ COURSE : "has prerequisites"
    NOTIFICATION }o--|| STUDENT : "sent to"
    NOTIFICATION }o--|| INSTRUCTOR : "sent to"
    
    STUDENT {
        string id PK
        string fullName
        string email
        string phone
        map attributes
    }
    
    COURSE {
        string code PK
        string title
        int credits
        string department FK
        set prerequisites
    }
    
    ENROLLMENT {
        string studentId FK
        string courseCode FK
        string semester
        EnrollmentStatus status
        map grades
        BitSet attendance
    }
    
    DEPARTMENT {
        string id PK
        string name
        set courses
    }
    
    INSTRUCTOR {
        string id PK
        string name
        set coursesTaught
    }
    
    NOTIFICATION {
        string id PK
        string recipientId FK
        string message
        LocalDateTime scheduledTime
        boolean sent
    }
    
    TIMESLOT {
        DayOfWeek dayOfWeek
        LocalTime startTime
        LocalTime endTime
    }
```

### 🔧 Key Collections Framework Usage

- **ConcurrentHashMap** - Thread-safe primary storage in repositories
- **TreeSet** - Sorted time slots and department IDs
- **LinkedHashSet** - Ordered student enrollments
- **HashSet** - Course prerequisites and fast lookups
- **EnumMap** - Efficient grade storage by GradeType
- **BitSet** - Compact attendance tracking
- **CopyOnWriteArrayList** - Read-heavy enrollment lists
- **Collections.synchronizedSet** - Thread-safe department ID sets

### Project Structure

```
src/main/java/
├── api/            # API layer (placeholder)
├── jobs/           # Background jobs (placeholder)
├── model/          # Domain models and DTOs
│   └── dto/        # Data Transfer Objects
├── repository/     # Data access layer interfaces and implementations
├── service/        # Business logic layer interfaces and implementations
└── util/           # Utility classes (GPAUtils, StringUtils)

src/test/java/
├── model/          # Model unit tests
├── repository/     # Repository unit tests 
├── service/        # Service unit tests 
└── util/           # Utility unit tests 
```

### 🎯 Core Features

- **Student Management**: Registration, search by name tokens, contact updates
- **Course Catalog**: Course creation, department-based listing
- **Enrollment System**: Student-course enrollment/dropping with semester tracking
- **Department Management**: Department CRUD operations
- **Grade Tracking**: Multiple grade types with GPA calculation
- **Attendance Tracking**: BitSet-based efficient attendance records
- **Thread Safety**: Concurrent access support across all repositories

## Technology Stack

- **Java 21**
- **Maven** - Build management
- **Lombok** - Boilerplate code reduction
- **JUnit 5** - Unit testing
- **AssertJ** - Fluent assertions