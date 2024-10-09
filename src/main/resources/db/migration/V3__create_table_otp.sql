CREATE TABLE "otp" (
                                  "id" uuid PRIMARY KEY,
                                 "user_id" uuid,
                                  "email" varchar,
                                  "otp" varchar,
                                  "otpGeneratedTime" timestamp
                                  
);
ALTER TABLE "otp" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");