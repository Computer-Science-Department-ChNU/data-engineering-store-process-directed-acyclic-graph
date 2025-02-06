ALTER TABLE connections ADD COLUMN id BIGSERIAL;

ALTER TABLE connections DROP CONSTRAINT connections_pkey;

ALTER TABLE connections ADD PRIMARY KEY (id);
