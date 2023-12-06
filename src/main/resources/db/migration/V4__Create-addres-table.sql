CREATE TABLE address (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(id),
    street_address VARCHAR(255) not null,
    city VARCHAR(100) not null,
    acronym_state VARCHAR(2) not null,
    postal_code VARCHAR(20) not null,
    country VARCHAR(100) not null,
   	reference text not null,
   	name text
);