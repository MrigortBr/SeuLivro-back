create table invite(
    id UUID PRIMARY KEY unique,
	id_book UUID REFERENCES books(id) not null,
	status TEXT not null
);