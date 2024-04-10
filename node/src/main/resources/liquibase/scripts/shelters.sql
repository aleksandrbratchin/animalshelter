CREATE TABLE shelters (
    id uuid NOT NULL PRIMARY KEY,
    shelter_information TEXT NOT NULL,
    shelter_address TEXT NOT NULL,
    work_schedule_shelter TEXT NOT NULL,
    safety_measures_in_shelter TEXT NOT NULL,
    security_data TEXT NOT NULL,
    contact_for_communication TEXT NOT NULL,
);
