insert into SEC_USER_ROLE (ID, VERSION, USER_ID, ROLE_NAME)
values (newid(), 1, (select ID from SEC_USER where LOGIN = 'anonymous'), 'LogoReader');
