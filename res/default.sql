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

DROP TABLE semester;

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

DROP TABLE course;

CREATE TABLE course_time(
  course_id VARCHAR(36),
  day_of_week INTEGER,
  start_hour INTEGER,
  end_hour INTEGER,
  start_minute INTEGER,
  end_minute INTEGER,
  PRIMARY KEY(course_id, day_of_week, start_hour, end_hour, start_minute, end_minute)
)
