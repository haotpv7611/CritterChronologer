CREATE TABLE IF NOT EXISTS CUSTOMERS (
  id BIGINT NOT NULL auto_increment,
  name VARCHAR(255),
  phone_number VARCHAR(255),
  notes VARCHAR(500),
  PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS PETS (
   id BIGINT NOT NULL auto_increment,
   type VARCHAR(255),
   name VARCHAR(255),
   birth_date DATE,
   notes VARCHAR(500),
   owner_id BIGINT,
   PRIMARY KEY(id),
   CONSTRAINT FK_PetOwner FOREIGN KEY (owner_id) REFERENCES CUSTOMERS(id)
);

CREATE TABLE IF NOT EXISTS EMPLOYEES (
  id BIGINT NOT NULL auto_increment,
  name VARCHAR(255),
  PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS SCHEDULES (
  id BIGINT NOT NULL auto_increment,
  employee_id BIGINT,
  pet_id BIGINT,
  event_date DATE,
  activities VARCHAR(255),
  PRIMARY KEY(id),
  CONSTRAINT FK_EmployeeSchedule FOREIGN KEY (employee_id) REFERENCES EMPLOYEES(id),
  CONSTRAINT FK_PetSchedule FOREIGN KEY (pet_id) REFERENCES PETS(id)
);

CREATE TABLE IF NOT EXISTS SKILLS (
  id BIGINT NOT NULL auto_increment,
  employee_id BIGINT,
  skill VARCHAR(255),
  PRIMARY KEY(id),
  CONSTRAINT FK_EmployeeSkills FOREIGN KEY (employee_id) REFERENCES EMPLOYEES(id)
);

CREATE TABLE IF NOT EXISTS DAYS_AVAILABLE (
  id BIGINT NOT NULL auto_increment,
  employee_id BIGINT,
  day_of_week VARCHAR(255),
  PRIMARY KEY(id),
  CONSTRAINT FK_DaysAvailable FOREIGN KEY (employee_id) REFERENCES EMPLOYEES(id)
);