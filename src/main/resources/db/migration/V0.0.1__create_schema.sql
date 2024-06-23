create table if not exists  user_info (
    id INT AUTO_INCREMENT primary key,
	username varchar(50) not null unique,
	password varchar(500) not null,
	enabled boolean not null default false,
	account_non_expired boolean not null default false,
	account_non_locked boolean not null default false,
	credentials_non_expired boolean not null default false,
	created_on timestamp default CURRENT_TIMESTAMP,
	updated_on timestamp default CURRENT_TIMESTAMP
);

create table if not exists  authorities (
	username varchar(50) not null,
	authority varchar(50) not null,
	constraint fk_authorities_users foreign key(username) references user_info(username)
);
create unique index ix_auth_username on authorities (username,authority);