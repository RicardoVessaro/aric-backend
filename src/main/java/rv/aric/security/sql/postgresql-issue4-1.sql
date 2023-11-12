-- OK
CREATE TABLE member (
    id                  UUID PRIMARY KEY,
    name                VARCHAR(80),
    username            VARCHAR(80) NOT NULL,
    email               VARCHAR(80) NOT NULL,
    biography           VARCHAR(200) NOT NULL
);

CREATE TABLE token_type (
    id                      NUMERIC(18) PRIMARY KEY,
    value                   VARCHAR(80) NOT NULL
);

CREATE TABLE token (
    id                  NUMERIC(18) PRIMARY KEY,
    token               VARCHAR(255) NOT NULL,
    expired             NUMERIC(1) NOT NULL,
    revoked             NUMERIC(1) NOT NULL,
    member_id           UUID NOT NULL,
    token_type_id       NUMERIC(18) NOT NULL
);

CREATE SEQUENCE token_id;

ALTER TABLE token
    ADD CONSTRAINT FKtokenTOmember
    FOREIGN KEY (member_id)
    REFERENCES member (id);

ALTER TABLE token
    ADD CONSTRAINT FKtokenTOtoken_type
    FOREIGN KEY (token_type_id)
    REFERENCES token_type (id);

CREATE INDEX X1_token ON token (member_id ASC);
CREATE INDEX X2_token ON token (token_type_id ASC);

ALTER TABLE member
    ADD password VARCHAR(80) NOT NULL;

ALTER TABLE member
    ALTER COLUMN biography DROP NOT NULL;
