ALTER TABLE connections DROP CONSTRAINT connections_pkey;

ALTER TABLE connections ADD PRIMARY KEY (child);

ALTER TABLE connections ALTER COLUMN father DROP NOT NULL;
ALTER TABLE connections ALTER COLUMN mother DROP NOT NULL;