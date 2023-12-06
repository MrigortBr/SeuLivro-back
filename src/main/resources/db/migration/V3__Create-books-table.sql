CREATE TABLE books (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author UUID REFERENCES users(id),
    publication_year INTEGER not null,
    genre VARCHAR(100) not null,
    description TEXT not null,
    price DECIMAL(10, 2) not null,
    stock_quantity INTEGER not null,
    store UUID REFERENCES stores(id),
   	enabled Boolean default true,
    image text not null
    
   );