INSERT INTO oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
VALUES ('test_client', NULL, '$2a$10$2szDKjvKHJCWE6YQNznogOeQF3USZHmCYj1fG7YbfK.vnTgNKLzri', 'read', 'password,refresh_token', NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO users (id, username, password, enabled, account_non_expired, credentials_non_expired, account_non_locked, name, mobile, created_time, updated_time, created_by, updated_by)
VALUES (101, 'admin', '$2a$10$vYA9wKn/hVGOtwQw2eHiceeIGNBdfLYpDmbzHgBSVmOfHXPH4iYdS', true, true, true, true, '超级管理员', '', now(), now(), 'system', 'system');
##角色
INSERT INTO roles (id, code, name, description, created_time, updated_time, created_by, updated_by)
VALUES (101, 'ADMIN', '超级管理员', '公司IT总负责人', now(), now(), 'system', 'system'),
       (102, 'FIN', '财务', '财务', now(), now(), 'system', 'system'),
       (103, 'IT', 'IT', 'IT角色', now(), now(), 'system', 'system');
##资源
INSERT INTO resources (id, name, code, type, url, method, description, created_time, updated_time, created_by, updated_by)
VALUES (101, '新增', 'user_manager:btn_add', 'button', '/users', 'POST', '新增用户功能', now(), now(), 'system', 'system'),
       (102, '编辑', 'user_manager:btn_edit', 'button', '/users', 'PUT', '编辑用户功能', now(), now(), 'system', 'system'),
       (103, '删除', 'user_manager:btn_del', 'button', '/users/{id}', 'DELETE', '删除用户功能', now(), now(), 'system', 'system'),
       (104, '查看', 'user_manager:view', 'url', '/users/{id}', 'GET', '查询用户功能', now(), now(), 'system', 'system');
##用户关系授权
INSERT INTO users_roles_relation (id, user_id, role_id, created_time, updated_time, created_by, updated_by)
VALUES (101, 101, 101, now(), now(), 'system', 'system');
##角色与资源关系表
INSERT INTO roles_resources_relation (id, role_id, resource_id, created_time, updated_time, created_by, updated_by)
VALUES (101, 101, 101, now(), now(), 'system', 'system'),
       (102, 101, 102, now(), now(), 'system', 'system'),
       (103, 101, 103, now(), now(), 'system', 'system'),
       (104, 101, 104, now(), now(), 'system', 'system');