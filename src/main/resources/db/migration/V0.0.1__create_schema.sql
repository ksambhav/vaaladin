create table if not exists  USER_INFO (
    ID INT AUTO_INCREMENT primary key,
	USERNAME varchar(50) not null unique,
	FULL_NAME varchar(50) not null,
	MOBILE varchar(12) unique,
	PASSWORD varchar(500) not null,
	ENABLED boolean not null default false,
	ACCOUNT_NON_EXPIRED boolean not null default true,
	ACCOUNT_NON_LOCKED boolean not null default true,
	CREDENTIALS_NON_EXPIRED boolean not null default true,
	CREATED_ON date default CURRENT_DATE,
	UPDATED_ON timestamp default CURRENT_TIMESTAMP,
	VERSION int,
	TENANT_ID varchar(64)
);

create table if not exists  AUTHORITIES (
	USERNAME varchar(50) not null,
	AUTHORITY varchar(50) not null,
	constraint fk_authorities_users foreign key(USERNAME) references USER_INFO(USERNAME)
);
create unique index ix_auth_username on AUTHORITIES (USERNAME,AUTHORITY);