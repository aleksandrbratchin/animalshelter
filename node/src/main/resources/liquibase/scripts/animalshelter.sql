-- liquibase formatted sql

-- changeset vpotapov:1

CREATE TABLE public.shelters (
	id uuid NOT NULL,
	about_shelter text NULL,
	address text NULL,
	"name" varchar(255) NOT NULL,
	safety_measures text NULL,
	security_data text NULL,
	type_of_animal varchar(255) NOT NULL,
	work_schedule text NULL,
	CONSTRAINT shelters_pkey PRIMARY KEY (id),
	CONSTRAINT shelters_type_of_animal_check CHECK (((type_of_animal)::text = ANY ((ARRAY['CAT'::character varying, 'DOG'::character varying])::text[]))),
	CONSTRAINT uk_1nri2n2e9r4qk9ow6euk4qeb1 UNIQUE (name)
);

-- changeset vpotapov:2

CREATE TABLE public.users (
	id uuid NOT NULL,
	date_create timestamp(6) NULL,
	date_update timestamp(6) NULL,
	active_chat int8 NULL,
	chat_id int8 NOT NULL,
	role_user varchar(255) NOT NULL,
	state varchar(255) NOT NULL,
	first_name varchar(255) NULL,
	last_name varchar(255) NULL,
	nick_name varchar(255) NULL,
	phone_number varchar(255) NULL,
	workload int4 NULL,
	shelter_id uuid NULL,
	CONSTRAINT uk_nr2rmfhq6wfp39vcduy7iketb UNIQUE (chat_id),
	CONSTRAINT users_pkey PRIMARY KEY (id),
	CONSTRAINT users_role_user_check CHECK (((role_user)::text = ANY ((ARRAY['CLIENT'::character varying, 'VOLUNTEER'::character varying])::text[]))),
	CONSTRAINT users_state_check CHECK (((state)::text = ANY ((ARRAY['INIT_MENU'::character varying, 'MAIN_MENU'::character varying, 'INFO_SHELTER'::character varying, 'ADOPTION'::character varying, 'CONTACT_VOLUNTEER_MENU'::character varying, 'VOLUNTEER_CHAT'::character varying, 'VOLUNTEER_START_MENU'::character varying, 'PET_REPORT'::character varying, 'RECOMMENDATIONS'::character varying, 'LEAVE_CONTACT_DETAILS_FOR_COMMUNICATION_MENU'::character varying, 'CLIENT_CHAT'::character varying, 'LIST_ANIMALS_MENU'::character varying, 'DAILY_REPORT_MENU'::character varying, 'WAITING_FOR_PHOTOS_FOR_DAILY_REPORT'::character varying, 'CHOOSE_SHELTER_MENU'::character varying, 'WAITING_TEXT_FOR_DAILY_REPORT'::character varying, 'CHECK_REPORT'::character varying])::text[])))
);

ALTER TABLE public.users ADD CONSTRAINT fkkjrm7r3emrq99t5en9sjdsg5u FOREIGN KEY (shelter_id) REFERENCES public.shelters(id);

-- changeset vpotapov:3

CREATE TABLE public.adoption_process_animal (
	id uuid NOT NULL,
	date_create timestamp(6) NULL,
	date_update timestamp(6) NULL,
	adoption_process_status varchar(255) NULL,
	"date" date NOT NULL,
	animal_id uuid NULL,
	shelter_id uuid NULL,
	user_id uuid NULL,
	CONSTRAINT adoption_process_animal_adoption_process_status_check CHECK (((adoption_process_status)::text = ANY ((ARRAY['PROCESS_ADOPTION'::character varying, 'ADOPTED'::character varying, 'NOT_ADOPTED'::character varying, 'DOPTION_DENIED'::character varying])::text[]))),
	CONSTRAINT adoption_process_animal_pkey PRIMARY KEY (id)
);

ALTER TABLE public.adoption_process_animal ADD CONSTRAINT fkcv8k8pjj6rhqgq13kjaotje71 FOREIGN KEY (animal_id) REFERENCES public.animals(id);
ALTER TABLE public.adoption_process_animal ADD CONSTRAINT fkfmccis708ahki042x3yfrgc1s FOREIGN KEY (shelter_id) REFERENCES public.shelters(id);
ALTER TABLE public.adoption_process_animal ADD CONSTRAINT fkq36alfrt46anaxkmal9pmqwtu FOREIGN KEY (user_id) REFERENCES public.users(id);

-- changeset vpotapov:4

CREATE TABLE public.animals (
	id uuid NOT NULL,
	adopted varchar(255) NULL,
	age float8 NULL,
	breed varchar(255) NULL,
	habits varchar(255) NULL,
	"name" varchar(255) NULL,
	type_of_animal varchar(255) NULL,
	shelter_id uuid NULL,
	CONSTRAINT animals_adopted_check CHECK (((adopted)::text = ANY ((ARRAY['PROCESS_OF_ADOPTION'::character varying, 'ADOPTED'::character varying, 'NOT_ADOPTED'::character varying])::text[]))),
	CONSTRAINT animals_pkey PRIMARY KEY (id),
	CONSTRAINT animals_type_of_animal_check CHECK (((type_of_animal)::text = ANY ((ARRAY['CAT'::character varying, 'DOG'::character varying])::text[])))
);

ALTER TABLE public.animals ADD CONSTRAINT fk7fmlpw3o4ourhtv3qy8gl6cn5 FOREIGN KEY (shelter_id) REFERENCES public.shelters(id);

-- changeset vpotapov:5

CREATE TABLE public.photo_report (
	id uuid NOT NULL,
	"data" bytea NULL,
	CONSTRAINT photo_report_pkey PRIMARY KEY (id)
);

-- changeset vpotapov:6

CREATE TABLE public.daily_report (
	id uuid NOT NULL,
	date_create timestamp(6) NULL,
	date_update timestamp(6) NULL,
	date_report date NOT NULL,
	report_status varchar(255) NOT NULL,
	report_text text NULL,
	adoption_animal_id uuid NULL,
	photo_report_id uuid NULL,
	volunteer_id uuid NULL,
	CONSTRAINT daily_report_pkey PRIMARY KEY (id),
	CONSTRAINT daily_report_report_status_check CHECK (((report_status)::text = ANY ((ARRAY['IN_PROCESSING'::character varying, 'APPROVED'::character varying, 'NOT_APPROVED'::character varying])::text[]))),
	CONSTRAINT uk_h27ociymu9o2g8i1pjxog27fp UNIQUE (photo_report_id)
);

ALTER TABLE public.daily_report ADD CONSTRAINT fkd2rwr80f6v6w1tuil9u2a2lox FOREIGN KEY (adoption_animal_id) REFERENCES public.adoption_process_animal(id);
ALTER TABLE public.daily_report ADD CONSTRAINT fkeutnc4tinni81h3a4nuv3op5v FOREIGN KEY (volunteer_id) REFERENCES public.users(id);
ALTER TABLE public.daily_report ADD CONSTRAINT fkh5mc7nxb2eysi53u3wv90gmq0 FOREIGN KEY (photo_report_id) REFERENCES public.photo_report(id);

-- changeset vpotapov:7

CREATE TABLE public.driving_directions (
	id uuid NOT NULL,
	"data" bytea NULL,
	shelter_id uuid NULL,
	CONSTRAINT driving_directions_pkey PRIMARY KEY (id),
	CONSTRAINT uk_cdlo00ydaxe0rykhwbhyvp592 UNIQUE (shelter_id)
);

ALTER TABLE public.driving_directions ADD CONSTRAINT fkjopdd4vy6v7o2djebw0qwlgw5 FOREIGN KEY (shelter_id) REFERENCES public.shelters(id);

-- changeset vpotapov:8

CREATE TABLE public.info_for_adoption (
	id uuid NOT NULL,
	home_improvement_for_adult_animal text NOT NULL,
	home_improvement_for_animal_with_disabilities text NOT NULL,
	home_improvement_for_young_animal text NOT NULL,
	list_documents text NOT NULL,
	list_specialists text NOT NULL,
	reasons_for_refusal_of_adoption text NOT NULL,
	rules_animals text NOT NULL,
	tips_from_specialist text NOT NULL,
	transportation text NOT NULL,
	type_of_animal varchar(255) NOT NULL,
	CONSTRAINT info_for_adoption_pkey PRIMARY KEY (id),
	CONSTRAINT info_for_adoption_type_of_animal_check CHECK (((type_of_animal)::text = ANY ((ARRAY['CAT'::character varying, 'DOG'::character varying])::text[]))),
	CONSTRAINT uk_3aa48eh7ml1hhq7bslq6ypc38 UNIQUE (type_of_animal)
);
