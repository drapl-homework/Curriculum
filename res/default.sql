CREATE TABLE user(
  id VARCHAR(36) PRIMARY KEY,
  name VARCHAR(20)
);

INSERT INTO user(id, name) VALUES("000", "xxx");

CREATE TABLE semester(
  id VARCHAR(36) PRIMARY KEY,
  name VARCHAR(20),
  start DATE,
  end DATE
);

INSERT INTO semester(id, name, start, end) VALUES("s00", "000", "1992-01-01", "1993-06-01");

CREATE TABLE course(
  id VARCHAR(36) PRIMARY KEY,
  semester_id VARCHAR(36) NOT NULL,
  name VARCHAR(100),
  credit REAL,
  score REAL,
  location VARCHAR(200),
  type VARCHAR(10)
);

INSERT INTO course(id, semester_id, name, credit, score, location, type) VALUES("aaa",
                                                                          "s00", "course-s00", 0, 0, "xxx", "A");
CREATE TABLE course_time(
  course_id VARCHAR(36),
  day_of_week INTEGER,
  alternate_week INTEGER,
  start_hour INTEGER,
  start_minute INTEGER,
  end_hour INTEGER,
  end_minute INTEGER,
  PRIMARY KEY(course_id, day_of_week, alternate_week, start_hour, start_minute, end_hour, end_minute)
);

INSERT INTO course_time(course_id, day_of_week, alternate_week, start_hour, start_minute, end_hour, end_minute) VALUES("aaa", 1, 0, 22, 00, 23, 00);

CREATE TABLE teacher(
  id varchar(36) PRIMARY KEY NOT NULL,
  name VARCHAR(50) NOT NULL,
  mail VARCHAR(100),
  tel VARCHAR(50),
  office VARCHAR(200)
);

INSERT INTO teacher(id, name, mail, tel, office) VALUES("0000", "wang", "aa@bb.com", "000-00000", "abcdefg");

CREATE TABLE course_teacher(
  course_id varchar(36) NOT NULL,
  teacher_id varchar(36) NOT NULL,
  PRIMARY KEY (course_id, teacher_id)
);

INSERT INTO course_teacher(course_id, teacher_id) VALUES("aaa", "0000");

SELECT teacher_id AS id, name, mail, tel, office from course_teacher INNER JOIN teacher ON course_teacher.teacher_id = teacher.id;

CREATE TABLE task (
  id        VARCHAR(36) PRIMARY KEY NOT NULL,
  course_id VARCHAR(36)             NOT NULL,
  name      VARCHAR(100),
  content   TEXT,
  end_time  INTEGER,
  status    INTEGER
);

INSERT INTO task(id, course_id, name, content, end_time, status) VALUES("t01", "aaa", "today", "todo", 1497001934, 0);
