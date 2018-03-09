create table oauth_client_details (
    client_id VARCHAR(256),
    resource_ids VARCHAR(256),
    client_secret VARCHAR(256),
    scope VARCHAR(256),
    authorized_grant_types VARCHAR(256),
    web_server_redirect_uri VARCHAR(256),
    authorities VARCHAR(256),
    access_token_validity INTEGER,
    refresh_token_validity INTEGER,
    additional_information VARCHAR(4096),
    autoapprove VARCHAR(256),
    primary key(client_id)
);

create table oauth_client_token (
  token_id VARCHAR(255),
  token VARBINARY,
  authentication_id VARCHAR(255),
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  primary key(authentication_id)
);

create table oauth_access_token (
  token_id VARCHAR(255),
  token VARBINARY,
  authentication_id VARCHAR(255),
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication VARBINARY,
  refresh_token VARCHAR(255),
  primary key(authentication_id)
);

create table oauth_refresh_token (
  token_id VARCHAR(255),
  token VARBINARY,
  authentication VARBINARY
);

create table users (
    user_id BIGINT PRIMARY KEY auto_increment,
    username VARCHAR(128) UNIQUE,
    password VARCHAR(256),
    enabled BOOL,
);