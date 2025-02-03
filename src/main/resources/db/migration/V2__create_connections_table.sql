CREATE TABLE connections (
                             child VARCHAR(255) NOT NULL,
                             father VARCHAR(255) NOT NULL,
                             mother VARCHAR(255) NOT NULL,
                             PRIMARY KEY (child, father, mother),
                             FOREIGN KEY (child) REFERENCES individuals(user_id),
                             FOREIGN KEY (father) REFERENCES individuals(user_id),
                             FOREIGN KEY (mother) REFERENCES individuals(user_id)
);