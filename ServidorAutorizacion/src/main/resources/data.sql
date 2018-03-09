INSERT INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	('bibliotecaApp', 'secretBiblioteca', 'foo,read,write', 'password,authorization_code,refresh_token,client_credentials', null, null, 36000, 36000, null, true),
	('editorialesApp', 'secretEditoriales', 'foo,read,write', 'password,authorization_code,refresh_token,client_credentials', null, null, 36000, 36000, null, true),
	('barClientIdPassword', 'secret', 'bar,read,write', 'password,authorization_code,refresh_token,client_credentials', null, null, 36000, 36000, null, true),
	('foo', 'foosecret', 'openid', 'password,authorization_code,refresh_token,client_credentials', null, null, 36000, 36000, null, true);


INSERT INTO users (username, password, enabled)
VALUES
	('peter@example.com', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri', true),
	('john@example.com', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri', true),
	('katie@example.com', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri', true);