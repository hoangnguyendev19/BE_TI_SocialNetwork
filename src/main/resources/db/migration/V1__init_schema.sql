CREATE TABLE "setting"
(
    "id"            uuid PRIMARY KEY,
    "key"           varchar,
    "value"         varchar,
    "created_at"    timestamp,
    "last_modified" timestamp
);
CREATE TABLE "users"
(
    "id"                  uuid PRIMARY KEY,
    "email"               varchar,
    "password"            varchar,
    "status"              varchar,
    "first_name"          varchar,
    "last_name"           varchar,
    "date_of_birth"       date,
    "present_address"     varchar,
    "permanent_address"   varchar,
    "phone_number"        varchar,
    "city"                varchar,
    "country"             varchar,
    "state"               varchar,
    "profile_picture_url" varchar,
    "cover_picture_url"   varchar,
    "is_delete"           bool,
    "last_login"          timestamp,
    "created_at"          timestamp,
    "last_modified"       timestamp
);

CREATE TABLE "boarding_house"
(
    "id"                  uuid PRIMARY KEY,
    "user_id"             uuid,
    "boarding_house_name" varchar,
    "present_address"     varchar,
    "ward"                varchar,
    "city"                varchar,
    "is_delete"           bool,
    "created_at"          timestamp,
    "last_modified"       timestamp
);

CREATE TABLE "room"
(
    "id"                        uuid PRIMARY KEY,
    "boarding_house_id"         uuid,
    "room_name"                 varchar,
    "room_rate"                 int,
    "electric_meter_old_number" int,
    "water_meter_old_number"    int,
    "room_status"               varchar,
    "is_delete"                 bool,
    "created_at"                timestamp,
    "last_modified"             timestamp
);

CREATE TABLE "room_setting"
(
    "id"                uuid PRIMARY KEY,
    "boarding_house_id" uuid,
    "electric_bill"     int,
    "water_bill"        int,
    "created_at"        timestamp,
    "last_modified"     timestamp
);

CREATE TABLE "room_user"
(
    "id"            uuid PRIMARY KEY,
    "room_id"       uuid,
    "user_id"       uuid,
    "is_delete"     bool,
    "created_at"    timestamp,
    "last_modified" timestamp
);

CREATE TABLE "history_room"
(
    "id"                    uuid PRIMARY KEY,
    "room_id"               uuid,
    "electric_meter_number" int,
    "water_meter_number"    int,
    "is_delete"             bool,
    "created_at"            timestamp,
    "last_modified"         timestamp
);

CREATE TABLE "payment"
(
    "id"                           uuid PRIMARY KEY,
    "room_id"                      uuid,
    "electricity_meter_new_number" int,
    "water_meter_new_number"       int,
    "total_amount"                 int,
    "payment_status"               varchar,
    "created_at"                   timestamp,
    "last_modified"                timestamp
);

CREATE TABLE "post"
(
    "id"             uuid PRIMARY KEY,
    "user_id"        uuid,
    "content"        varchar,
    "parent_post_id" uuid,
    "is_delete"      bool,
    "created_at"     timestamp,
    "last_modified"  timestamp
);

CREATE TABLE "reason_report_post"
(
    "id"            uuid PRIMARY KEY,
    "reason"        varchar,
    "created_at"    timestamp,
    "last_modified" timestamp
);

CREATE TABLE "post_report"
(
    "id"            uuid PRIMARY KEY,
    "user_id"       uuid,
    "post_id"       uuid,
    "reason"        varchar,
    "created_at"    timestamp,
    "last_modified" date

);

CREATE TABLE "media"
(
    "id"            uuid PRIMARY KEY,
    "post_id"       uuid,
    "media_type"    varchar,
    "media_url"     varchar,
    "is_delete"     bool,
    "created_at"    timestamp,
    "last_modified" timestamp
);

CREATE TABLE "like"
(
    "id"            uuid PRIMARY KEY,
    "user_id"       uuid,
    "post_id"       uuid,
    "last_modified" timestamp,
    "created_at"    timestamp
);

CREATE TABLE "like_comment"
(
    "id"            uuid PRIMARY KEY,
    "user_id"       uuid,
    "comment_id"    uuid,
    "last_modified" timestamp,
    "created_at"    timestamp
);

CREATE TABLE "comment"
(
    "id"                uuid PRIMARY KEY,
    "user_id"           uuid,
    "post_id"           uuid,
    "parent_comment_id" uuid,
    "comment_text"      varchar,
    "is_hidden"         bool,
    "is_delete"         bool,
    "created_at"        timestamp,
    "last_modified"     timestamp
);
CREATE TABLE "token"
(
    "id"            uuid PRIMARY KEY,
    "user_id"       uuid,
    "access_token"  varchar,
    "refresh_token" varchar,
    "is_revoked"    bool,
    "created_at"    timestamp,
    "last_modified" timestamp
);
CREATE TABLE "otp"
(
    "id"            uuid PRIMARY KEY,
    "user_id"       uuid,
    "otp"           varchar,
    "created_at"    timestamp,
    "last_modified" timestamp
);
ALTER TABLE "otp"
    ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");
ALTER TABLE "token"
    ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "post"
    ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "media"
    ADD FOREIGN KEY ("post_id") REFERENCES "post" ("id");

ALTER TABLE "like"
    ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "like"
    ADD FOREIGN KEY ("post_id") REFERENCES "post" ("id");

ALTER TABLE "comment"
    ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "comment"
    ADD FOREIGN KEY ("post_id") REFERENCES "post" ("id");

ALTER TABLE "room"
    ADD FOREIGN KEY ("boarding_house_id") REFERENCES "boarding_house" ("id");

ALTER TABLE "history_room"
    ADD FOREIGN KEY ("room_id") REFERENCES "room" ("id");

ALTER TABLE "boarding_house"
    ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "like_comment"
    ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "like_comment"
    ADD FOREIGN KEY ("comment_id") REFERENCES "comment" ("id");

ALTER TABLE "room_user"
    ADD FOREIGN KEY ("room_id") REFERENCES "users" ("id");

ALTER TABLE "room_user"
    ADD FOREIGN KEY ("room_id") REFERENCES "room" ("id");

ALTER TABLE "room_setting"
    ADD FOREIGN KEY ("boarding_house_id") REFERENCES "boarding_house" ("id");

ALTER TABLE "payment"
    ADD FOREIGN KEY ("room_id") REFERENCES "room" ("id");

ALTER TABLE "post_report"
    ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "post_report"
    ADD FOREIGN KEY ("post_id") REFERENCES "post" ("id");

-- ALTER TABLE "post_report" ADD FOREIGN KEY ("reason_id") REFERENCES "reason_report_post" ("id");
