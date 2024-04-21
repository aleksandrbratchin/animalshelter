-- liquibase formatted sql

-- changeset vpotapov:create_photo_report

CREATE TABLE photo_report
(
    id     uuid NOT NULL,
    "data" bytea NULL,
    CONSTRAINT photo_report_pkey PRIMARY KEY (id)
);

-- changeset vpotapov:create_shelters

CREATE TABLE shelters
(
    id              uuid         NOT NULL,
    about_shelter   text NULL,
    address         text NULL,
    "name"          varchar(255) NOT NULL,
    safety_measures text NULL,
    security_data   text NULL,
    type_of_animal  varchar(255) NOT NULL,
    work_schedule   text NULL,
    CONSTRAINT shelters_pkey PRIMARY KEY (id),
    CONSTRAINT shelters_type_of_animal_check CHECK (((type_of_animal)::text = ANY ((ARRAY['CAT':: character varying, 'DOG':: character varying])::text[])
) ),
	CONSTRAINT uk_name UNIQUE (name)
);

-- changeset vpotapov:create_users

CREATE TABLE users
(
    id           uuid         NOT NULL,
    date_create  timestamp(6) NULL,
    date_update  timestamp(6) NULL,
    active_chat  int8 NULL,
    chat_id      int8         NOT NULL,
    role_user    varchar(255) NOT NULL,
    state        varchar(255) NOT NULL,
    first_name   varchar(255) NULL,
    last_name    varchar(255) NULL,
    nick_name    varchar(255) NULL,
    phone_number varchar(255) NULL,
    workload     int4 NULL,
    shelter_id   uuid NULL,
    CONSTRAINT uk_chat_id UNIQUE (chat_id),
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_role_user_check CHECK (((role_user)::text = ANY ((ARRAY['CLIENT':: character varying, 'VOLUNTEER':: character varying])::text[])
) ),
	CONSTRAINT users_state_check CHECK (((state)::text = ANY ((ARRAY['INIT_MENU'::character varying, 'MAIN_MENU'::character varying, 'INFO_SHELTER'::character varying, 'ADOPTION'::character varying, 'CONTACT_VOLUNTEER_MENU'::character varying, 'VOLUNTEER_CHAT'::character varying, 'VOLUNTEER_START_MENU'::character varying, 'PET_REPORT'::character varying, 'RECOMMENDATIONS'::character varying, 'LEAVE_CONTACT_DETAILS_FOR_COMMUNICATION_MENU'::character varying, 'CLIENT_CHAT'::character varying, 'LIST_ANIMALS_MENU'::character varying, 'DAILY_REPORT_MENU'::character varying, 'WAITING_FOR_PHOTOS_FOR_DAILY_REPORT'::character varying, 'CHOOSE_SHELTER_MENU'::character varying, 'WAITING_TEXT_FOR_DAILY_REPORT'::character varying, 'CHECK_REPORT'::character varying])::text[])))
);

ALTER TABLE users
    ADD CONSTRAINT fk_users_shelters FOREIGN KEY (shelter_id) REFERENCES shelters (id);

-- changeset vpotapov:create_animals

CREATE TABLE animals
(
    id             uuid NOT NULL,
    adopted        varchar(255) NULL,
    age            float8 NULL,
    breed          varchar(255) NULL,
    habits         varchar(255) NULL,
    "name"         varchar(255) NULL,
    type_of_animal varchar(255) NULL,
    shelter_id     uuid NULL,
    CONSTRAINT animals_adopted_check CHECK (((adopted)::text = ANY ((ARRAY['PROCESS_OF_ADOPTION':: character varying, 'ADOPTED':: character varying, 'NOT_ADOPTED':: character varying])::text[])
) ),
	CONSTRAINT animals_pkey PRIMARY KEY (id),
	CONSTRAINT animals_type_of_animal_check CHECK (((type_of_animal)::text = ANY ((ARRAY['CAT'::character varying, 'DOG'::character varying])::text[])))
);

ALTER TABLE animals
    ADD CONSTRAINT fk_animals FOREIGN KEY (shelter_id) REFERENCES shelters (id);

-- changeset vpotapov:create_adoption_process_animal

CREATE TABLE adoption_process_animal
(
    id                      uuid NOT NULL,
    date_create             timestamp(6) NULL,
    date_update             timestamp(6) NULL,
    adoption_process_status varchar(255) NULL,
    "date"                  date NOT NULL,
    animal_id               uuid NULL,
    shelter_id              uuid NULL,
    user_id                 uuid NULL,
    CONSTRAINT adoption_process_animal_adoption_process_status_check CHECK (((adoption_process_status)::text = ANY ((ARRAY['PROCESS_ADOPTION':: character varying, 'ADOPTED':: character varying, 'NOT_ADOPTED':: character varying, 'ADOPTION_DENIED':: character varying])::text[])
) ),
	CONSTRAINT adoption_process_animal_pkey PRIMARY KEY (id)
);

ALTER TABLE adoption_process_animal
    ADD CONSTRAINT fk_adoption_process_animal_animals FOREIGN KEY (animal_id) REFERENCES animals (id);
ALTER TABLE adoption_process_animal
    ADD CONSTRAINT fk_adoption_process_animal_shelters FOREIGN KEY (shelter_id) REFERENCES shelters (id);
ALTER TABLE adoption_process_animal
    ADD CONSTRAINT fk_adoption_process_animal_users FOREIGN KEY (user_id) REFERENCES users (id);

-- changeset vpotapov:create_daily_report

CREATE TABLE daily_report
(
    id                 uuid         NOT NULL,
    date_create        timestamp(6) NULL,
    date_update        timestamp(6) NULL,
    date_report        date         NOT NULL,
    report_status      varchar(255) NOT NULL,
    report_text        text NULL,
    adoption_animal_id uuid NULL,
    photo_report_id    uuid NULL,
    volunteer_id       uuid NULL,
    CONSTRAINT daily_report_pkey PRIMARY KEY (id),
    CONSTRAINT daily_report_report_status_check CHECK (((report_status)::text = ANY ((ARRAY['IN_PROCESSING':: character varying, 'APPROVED':: character varying, 'NOT_APPROVED':: character varying])::text[])
) ),
	CONSTRAINT uk_photo_report_id UNIQUE (photo_report_id)
);

ALTER TABLE daily_report
    ADD CONSTRAINT fk_daily_report_adoption_process_animal FOREIGN KEY (adoption_animal_id) REFERENCES adoption_process_animal (id);
ALTER TABLE daily_report
    ADD CONSTRAINT fk_daily_report_users FOREIGN KEY (volunteer_id) REFERENCES users (id);
ALTER TABLE daily_report
    ADD CONSTRAINT fk_daily_report_photo_report FOREIGN KEY (photo_report_id) REFERENCES photo_report (id);

-- changeset vpotapov:create_driving_directions

CREATE TABLE driving_directions
(
    id         uuid NOT NULL,
    "data"     bytea NULL,
    shelter_id uuid NULL,
    CONSTRAINT driving_directions_pkey PRIMARY KEY (id),
    CONSTRAINT uk_shelter_id UNIQUE (shelter_id)
);

ALTER TABLE driving_directions
    ADD CONSTRAINT fk_driving_directions_shelters FOREIGN KEY (shelter_id) REFERENCES shelters (id);

-- changeset vpotapov:create_info_for_adoption

CREATE TABLE info_for_adoption
(
    id                                            uuid         NOT NULL,
    home_improvement_for_adult_animal             text         NOT NULL,
    home_improvement_for_animal_with_disabilities text         NOT NULL,
    home_improvement_for_young_animal             text         NOT NULL,
    list_documents                                text         NOT NULL,
    list_specialists                              text         NOT NULL,
    reasons_for_refusal_of_adoption               text         NOT NULL,
    rules_animals                                 text         NOT NULL,
    tips_from_specialist                          text         NOT NULL,
    transportation                                text         NOT NULL,
    type_of_animal                                varchar(255) NOT NULL,
    CONSTRAINT info_for_adoption_pkey PRIMARY KEY (id),
    CONSTRAINT info_for_adoption_type_of_animal_check CHECK (((type_of_animal)::text = ANY ((ARRAY['CAT':: character varying, 'DOG':: character varying])::text[])
) ),
	CONSTRAINT uk_type_of_animal UNIQUE (type_of_animal)
);