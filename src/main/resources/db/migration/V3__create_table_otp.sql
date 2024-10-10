CREATE TABLE "otp" (
                                  "id" uuid PRIMARY KEY,
                                 "user_id" uuid,
                                  "otp" varchar,
                                  "otp_generated_time" timestamp
                                  
);
ALTER TABLE "otp" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");