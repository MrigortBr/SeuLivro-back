CREATE TABLE users
(
   id UUID PRIMARY KEY UNIQUE,
   first_name TEXT NOT NULL,
   last_name TEXT NOT NULL,
   birth_date DATE,
   email TEXT NOT NULL UNIQUE,
   password TEXT NOT NULL,
   role TEXT NOT NULL,
   nickname TEXT not null UNIQUE,
   enabled Boolean default true,
   identifier TEXT NOT NULL UNIQUE,
   type_identifier TEXT NOT NULL,
   image text not null
);