CREATE TABLE users
(uuid VARCHAR(100) PRIMARY KEY,
 username VARCHAR(30),
 email VARCHAR(30),
 admin BOOLEAN,
 last_login TIMESTAMP,
 is_active BOOLEAN,
 password VARCHAR(300));
