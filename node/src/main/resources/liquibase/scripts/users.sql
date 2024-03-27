--liquibase formatted sql

-- changeset aleksbratchin:create_users
CREATE TABLE users
(
    id          uuid         NOT NULL,
    date_create timestamp(6) NULL,
    date_update timestamp(6) NULL,
    chat_id     int8         NOT NULL,
    role_user   varchar(255) NOT NULL,
    state       varchar(255) NOT NULL,
    CONSTRAINT users_chat_id_unique UNIQUE (chat_id),
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_role_check CHECK (((role_user)::text = ANY ((ARRAY['CLIENT':: character varying, 'VOLUNTEER':: character varying])::text[]))),
	CONSTRAINT users_state_check CHECK (((state)::text = ANY ((ARRAY['MAIN_MENU'::character varying, 'INFO_SHELTER'::character varying, 'ADOPTION'::character varying, 'VOLUNTEER'::character varying, 'PET_REPORT'::character varying])::text[])))
);
