create table if not exists free_account_numbers
(
    account_number char(16) primary key,
    account_type   varchar(32) not null
);

create table if not exists account_numbers_sequence
(
    account_type varchar(32) primary key,
    counter      bigint       not null
);