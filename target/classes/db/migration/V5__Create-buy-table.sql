CREATE TABLE users_books (
    id BIGSERIAL PRIMARY KEY,
 	id_user UUID REFERENCES users(id),
	id_book UUID REFERENCES books(id),
	id_address UUID REFERENCES address(id),
    price DECIMAL(10, 2),
    quantity INTEGER,
    date_purchase date
   );