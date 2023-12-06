CREATE TABLE stores
(
   id UUID PRIMARY KEY,
   owner_id UUID REFERENCES users (id),
   cnpj VARCHAR (18) NOT NULL,
   name VARCHAR (255) NOT NULL UNIQUE,
   fantasy_name VARCHAR (255) NOT NULL,
   description TEXT,
   enabled Boolean default true,
   image text not null
);