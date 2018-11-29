#-access_token存储表
DROP TABLE IF EXISTS oauth_access_token;
CREATE TABLE oauth_access_token
(
  token_id          CHARACTER VARYING(256) COMMENT 'MD5加密的access_token的值',
  token             binary COMMENT 'OAuth2AccessToken.java对象序列化后的二进制数据',
  authentication_id CHARACTER VARYING(256) COMMENT 'MD5加密过的username,client_id,scope',
  user_name         CHARACTER VARYING(256) COMMENT '登录的用户名',
  client_id         CHARACTER VARYING(256) COMMENT '客户端ID',
  authentication    binary COMMENT 'OAuth2Authentication.java对象序列化后的二进制数据',
  refresh_token     CHARACTER VARYING(256) COMMENT 'MD5加密果的refresh_token的值'
);

#-refresh_token存储表
DROP TABLE IF EXISTS oauth_refresh_token;
CREATE TABLE oauth_refresh_token
(
  token_id       CHARACTER VARYING(256) COMMENT 'MD5加密过的refresh_token的值',
  token          binary COMMENT 'OAuth2RefreshToken.java对象序列化后的二进制数据',
  authentication binary COMMENT 'OAuth2Authentication.java对象序列化后的二进制数据'
);

#-授权记录表
DROP TABLE IF EXISTS oauth_approvals;
CREATE TABLE oauth_approvals
(
  userid         CHARACTER VARYING(256) COMMENT '登录的用户名',
  clientid       CHARACTER VARYING(256) COMMENT '客户端ID',
  scope          CHARACTER VARYING(256) COMMENT '申请的权限',
  status         CHARACTER VARYING(10) COMMENT '状态（Approve或Deny）',
  expiresat      TIMESTAMP  COMMENT '过期时间',
  lastmodifiedat TIMESTAMP  COMMENT '最终修改时间'
);
#-授权码表
DROP TABLE IF EXISTS oauth_code;
CREATE TABLE oauth_code
(
  code           CHARACTER VARYING(256) COMMENT '授权码(未加密)',
  authentication binary COMMENT 'AuthorizationRequestHolder.java对象序列化后的二进制数据'
);

#-client用户表
DROP TABLE IF EXISTS oauth_client_details;
CREATE TABLE oauth_client_details
(
  client_id               CHARACTER VARYING(256) NOT NULL COMMENT '客户端ID',
  resource_ids            CHARACTER VARYING(256) COMMENT '资源ID集合,多个资源时用逗号(,)分隔',
  client_secret           CHARACTER VARYING(256) COMMENT '客户端密匙',
  scope                   CHARACTER VARYING(256) COMMENT '客户端申请的权限范围',
  authorized_grant_types  CHARACTER VARYING(256) COMMENT '客户端支持的grant_type',
  web_server_redirect_uri CHARACTER VARYING(256) COMMENT '重定向URI',
  authorities             CHARACTER VARYING(256) COMMENT '客户端所拥有的Spring Security的权限值，多个用逗号(,)分隔',
  access_token_validity   INTEGER COMMENT '访问令牌有效时间值(单位:秒)',
  refresh_token_validity  INTEGER COMMENT '更新令牌有效时间值(单位:秒)',
  additional_information  CHARACTER VARYING(4096) COMMENT '预留字段',
  autoapprove             CHARACTER VARYING(256) COMMENT '用户是否自动Approval操作',
  CONSTRAINT pk_oauth_client_details_client_id PRIMARY KEY (client_id)
);


#-客户端授权令牌表
DROP TABLE IF EXISTS oauth_client_token;
CREATE TABLE oauth_client_token
(
  token_id          CHARACTER VARYING(256) COMMENT 'MD5加密的access_token值',
  token             binary COMMENT 'OAuth2AccessToken.java对象序列化后的二进制数据',
  authentication_id CHARACTER VARYING(256) COMMENT 'MD5加密过的username,client_id,scope',
  user_name         CHARACTER VARYING(256) COMMENT '登录的用户名',
  client_id         CHARACTER VARYING(256) COMMENT '客户端ID'
);

##用户表
DROP TABLE IF EXISTS user;
CREATE TABLE user
(
  id                      SERIAL PRIMARY KEY,
  username                VARCHAR(100) NOT NULL,
  password                VARCHAR(100) NOT NULL,
  name                    VARCHAR(200),
  mobile                  VARCHAR(20),
  enabled                 BOOLEAN,
  account_non_expired     BOOLEAN,
  credentials_non_expired BOOLEAN,
  account_non_locked      BOOLEAN,
  created_time            TIMESTAMP    NOT NULL DEFAULT now(),
  updated_time            TIMESTAMP    NOT NULL DEFAULT now(),
  created_by              VARCHAR(100) NOT NULL,
  updated_by              VARCHAR(100) NOT NULL
);
CREATE UNIQUE INDEX ux_user_username
  ON user (username);
CREATE UNIQUE INDEX ux_user_mobile
  ON user (mobile);

##角色表
DROP TABLE IF EXISTS role;
CREATE TABLE role
(
  id           SERIAL PRIMARY KEY,
  code         VARCHAR(100) NOT NULL,
  name         VARCHAR(200),
  description   VARCHAR(500),
  created_time TIMESTAMP    NOT NULL DEFAULT now(),
  updated_time TIMESTAMP    NOT NULL DEFAULT now(),
  created_by   VARCHAR(100) NOT NULL,
  updated_by   VARCHAR(100) NOT NULL
);


##资源表
DROP TABLE IF EXISTS resources;
CREATE TABLE resources
(
  id           SERIAL PRIMARY KEY,
  code         VARCHAR(100),
  type         VARCHAR(100),
  name         VARCHAR(200),
  url          VARCHAR(200),
  method       VARCHAR(20),
  description  VARCHAR(500),
  created_time TIMESTAMP    NOT NULL DEFAULT now(),
  updated_time TIMESTAMP    NOT NULL DEFAULT now(),
  created_by   VARCHAR(100) NOT NULL,
  updated_by   VARCHAR(100) NOT NULL
);
CREATE UNIQUE INDEX ux_resources_code
  ON resources (code);

##用户和角色关系表
DROP TABLE IF EXISTS user_role_relation;
CREATE TABLE user_role_relation
(
  id           SERIAL PRIMARY KEY,
  user_id      INT          NOT NULL,
  role_id      INT          NOT NULL,
  created_time TIMESTAMP    NOT NULL DEFAULT now(),
  updated_time TIMESTAMP    NOT NULL DEFAULT now(),
  created_by   VARCHAR(100) NOT NULL,
  updated_by   VARCHAR(100) NOT NULL
);


##角色和资源关系表
DROP TABLE IF EXISTS role_resources_relation;
CREATE TABLE role_resources_relation
(
  id           SERIAL PRIMARY KEY,
  resource_id  INT          NOT NULL,
  role_id      INT          NOT NULL,
  created_time TIMESTAMP    NOT NULL DEFAULT now(),
  updated_time TIMESTAMP    NOT NULL DEFAULT now(),
  created_by   VARCHAR(100) NOT NULL,
  updated_by   VARCHAR(100) NOT NULL
);
