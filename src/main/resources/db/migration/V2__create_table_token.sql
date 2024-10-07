CREATE TABLE "token" (
                                  "id" uuid PRIMARY KEY,
                                 "user_id" uuid,
                                  "access_token" varchar,
                                  "refresh_token" varchar,
                                  "is_revoked" bool,
                                  "created_at" timestamp,
                                  "last_modified" timestamp
);
ALTER TABLE "token" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");