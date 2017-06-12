CREATE TABLE user (
  id   VARCHAR(36) PRIMARY KEY,
  name VARCHAR(20)
);

INSERT INTO user (id, name) VALUES ("000", "xxx");

CREATE TABLE semester (
  id    VARCHAR(36) PRIMARY KEY,
  name  VARCHAR(20),
  start DATE,
  end   DATE
);

INSERT INTO semester (id, name, start, end) VALUES ("s00", "2016秋", "2016-09-12", "2017-01-15");

CREATE TABLE course (
  id          VARCHAR(36) PRIMARY KEY,
  semester_id VARCHAR(36) NOT NULL,
  name        VARCHAR(100),
  credit      REAL,
  score       REAL,
  location    VARCHAR(200),
  type        VARCHAR(10)
);

INSERT INTO course (id, semester_id, name, credit, score, location, type)
VALUES ("c-wuli", "s00", "物理", 5, 3, "北楼", "A");

INSERT INTO course (id, semester_id, name, credit, score, location, type)
VALUES ("c-shuxue", "s00", "数学", 5, 3, "南楼", "B");

CREATE TABLE course_time (
  course_id      VARCHAR(36),
  day_of_week    INTEGER,
  alternate_week INTEGER,
  start_hour     INTEGER,
  start_minute   INTEGER,
  end_hour       INTEGER,
  end_minute     INTEGER,
  PRIMARY KEY (course_id, day_of_week, alternate_week, start_hour, start_minute, end_hour, end_minute)
);

INSERT INTO course_time (course_id, day_of_week, alternate_week, start_hour, start_minute, end_hour, end_minute)
VALUES ("c-wuli", 1, 0, 14, 00, 15, 00);

INSERT INTO course_time (course_id, day_of_week, alternate_week, start_hour, start_minute, end_hour, end_minute)
VALUES ("c-wuli", 2, 0, 16, 00, 18, 00);

INSERT INTO course_time (course_id, day_of_week, alternate_week, start_hour, start_minute, end_hour, end_minute)
VALUES ("c-shuxue", 3, 0, 16, 00, 18, 00);

INSERT INTO course_time (course_id, day_of_week, alternate_week, start_hour, start_minute, end_hour, end_minute)
VALUES ("c-shuxue", 4, 0, 10, 00, 13, 00);

CREATE TABLE teacher (
  id     VARCHAR(36) PRIMARY KEY NOT NULL,
  name   VARCHAR(50)             NOT NULL,
  mail   VARCHAR(100),
  tel    VARCHAR(50),
  office VARCHAR(200)
);

INSERT INTO teacher (id, name, mail, tel, office) VALUES ("0000", "wang", "aa@bb.com", "000-00000", "abcdefg");

CREATE TABLE course_teacher (
  course_id  VARCHAR(36) NOT NULL,
  teacher_id VARCHAR(36) NOT NULL,
  PRIMARY KEY (course_id, teacher_id)
);

INSERT INTO course_teacher (course_id, teacher_id) VALUES ("aaa", "0000");

SELECT
  teacher_id AS id,
  name,
  mail,
  tel,
  office
FROM course_teacher
  INNER JOIN teacher ON course_teacher.teacher_id = teacher.id;

CREATE TABLE task (
  id        VARCHAR(36) PRIMARY KEY NOT NULL,
  course_id VARCHAR(36)             NOT NULL,
  name      VARCHAR(100),
  content   TEXT,
  end_time  INTEGER,
  status    INTEGER
);

INSERT INTO task (id, course_id, name, content, end_time, status) VALUES ("t01", "aaa", "today", "todo", 1497001934, 0);
