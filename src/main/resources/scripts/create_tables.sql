-- Company
CREATE TABLE IF NOT EXISTS company
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Department (Many-to-One with Company)
CREATE TABLE IF NOT EXISTS department
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    company_id BIGINT       NOT NULL,
    CONSTRAINT fk_department_company FOREIGN KEY (company_id) REFERENCES company (id)
);

-- Employee (Many-to-One with Department, Self-referencing Manager)
CREATE TABLE IF NOT EXISTS employee
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100)   NOT NULL,
    department_id BIGINT         NOT NULL,
    manager_id    BIGINT, -- Nullable
    phone         VARCHAR(25)    NOT NULL,
    address       VARCHAR(255)   NOT NULL,
    salary        DECIMAL(10, 2) NOT NULL,
    email         VARCHAR(100)   NOT NULL UNIQUE,
    birth_date     DATE           NOT NULL,
    CONSTRAINT fk_employee_department FOREIGN KEY (department_id) REFERENCES department (id),
    CONSTRAINT fk_employee_manager FOREIGN KEY (manager_id) REFERENCES employee (id)
);

-- Project (Independent)
CREATE TABLE IF NOT EXISTS project
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Employee_Project (Many-to-Many between Employee and Project)
CREATE TABLE IF NOT EXISTS employee_project
(
    employee_id BIGINT,
    project_id  BIGINT,
    PRIMARY KEY (employee_id, project_id),
    CONSTRAINT fk_ep_employee FOREIGN KEY (employee_id) REFERENCES employee (id),
    CONSTRAINT fk_ep_project FOREIGN KEY (project_id) REFERENCES project (id)
);
