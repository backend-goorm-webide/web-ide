-- drop table users;
--
-- CREATE TABLE IF NOT EXISTS users
-- (
--     ID            bigint auto_increment
--         primary key,
--     MOD_AT        datetime(6)                         null,
--     REG_AT        timestamp default CURRENT_TIMESTAMP null,
--     EMAIL         varchar(255)                        not null,
--     IS_WITHDRAWN  bit                                 null,
--     LAST_LOGIN_AT timestamp                           null,
--     NAME          varchar(100)                        not null,
--     PHONE         varchar(16)                         null,
--     PWD           varchar(255)                        null,
--     USER_ID       varchar(255)                        not null,
--     constraint UK_8l4n5d7w7f0t8kqery39dvkwh
--         unique (USER_ID)
-- );


-- pwd : adminPwd
insert into USERS (id, USER_ID, PWD, NAME, EMAIL, PHONE, IS_WITHDRAWN, LAST_LOGIN_AT, REG_AT, MOD_AT)
values (2, 'admin', '$2a$10$gWdvAoIrk5xaVwlLKFybUeI5KAfmLY3.dU7os3Xs71v3gveqkidDe', '어드민', 'test@test.com',
        '01000001111',
        false, current_timestamp(), current_timestamp(), current_timestamp());

insert into AUTHORITY (AUTHORITY_NAME) values ('ROLE_USER');
insert into AUTHORITY (AUTHORITY_NAME) values ('ROLE_ADMIN');

insert into USER_AUTHORITY (USERS_ID, AUTHORITY_NAME) values (2, 'ROLE_USER');
insert into USER_AUTHORITY (USERS_ID, AUTHORITY_NAME) values (2, 'ROLE_ADMIN');