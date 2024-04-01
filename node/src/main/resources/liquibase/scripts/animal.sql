CREATE TABLE animals (
    id UUID NOT NULL PRIMARY KEY,
    type_of_animal TEXT NOT NULL,
    name TEXT NOT NULL,
    age REAL  CHECK (age>-1),
    breed TEXT NOT NULL,
    habits TEXT NOT NULL,
    adopted BIT,
    id_shelter UUID FOREIGN KEY
);
