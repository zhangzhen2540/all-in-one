SSO session - TGT(ticket-granting ticket)

ST(service ticket)




# 通过（用户名|密码|当前service名)获取TGT票据, 如：
http://172.19.4.85:8443/cas/v1/tickets
	username=admin&password=123456

响应的TGT信息存储在Location中。

---------
	cache-control: no-cache, no-store, max-age=0, must-revalidate
	content-length: 388
	content-type: text/html;charset=UTF-8
	date: Thu, 25 Jul 2019 07:36:46 GMT
	expires: 0
	location: http://172.19.4.85:8443/cas/v1/tickets/TGT-198-YVbN6oVKDbWf8SOeUVrHsmrF2PKrIbahlrSKYqtBPnyIUFi8dmDZr5GSOAa5-9aNetElocalhost
	pragma: no-cache
	x-content-type-options: nosniff
	x-frame-options: DENY
	x-xss-protection: 1; mode=block

# TGT -> ST
// 参数service需要进行urlencode
curl -X POST -v "http://172.19.4.85:8443/cas/v1/tickets/TGT-198-YVbN6oVKDbWf8SOeUVrHsmrF2PKrIbahlrSKYqtBPnyIUFi8dmDZr5GSOAa5-9aNetElocalhost" -d "service=http%3A%2F%2F172.19.4.77%3A9001%2F"

----------
	ST-106-xIPZd6CRePpqIUSRj5NVE8j-rxIlocalhost

# 过ST访问service，正常下可完成登录:
http://172.19.4.77:9001/cas/callback?ticket=ST-106-xIPZd6CRePpqIUSRj5NVE8j-rxIlocalhost


# 注销登录(清除TGT)，单点登出，如：

curl -X DELETE -v "http://172.19.4.85:8443/cas/v1/tickets/TGT-94-xxxxx"







http://172.19.4.85:8443/cas/v1/tickets/TGT-202-WFS4MIfDaE9uA8ltZ8qkHmVBOVKyazjpTHo2Y5WkL78GTVz-UgIEOPgi6ANNZskPuNYlocalhost





"http://172.19.4.85:8443/cas/login?tgc=TGT-210-jd9sgjVMRfzA93gUWtVe5GNaP4LZ-vNmFoyQVXPlUso6N8qrvltztkyXJS6mOQbKmNAlocalhost&service=http%3A%2F%2F127.0.0.1%3A4501%2Fperm%2Fcas%2Fcallback"







-----------------------------------
http://172.19.4.77:9001/perm/cas/login


http://172.19.4.85:8443/cas/login?service=http://172.19.4.77:9001/cas/callback&tgc=TGT-41-yD4d9Ahd9G69oz--RN6aXj9HKa6IOXFGhJ5egLJvXo7Z06GvvdzhcIYwqOHnHkZPjGwlocalhost

http://172.19.4.77:9001/cas/callback?ticket=ST-28-KO24spVXaMaOdmYBjDc-PMCe9x0localhost



## CAS SQL
```text
create database base_user default charset utf8;

drop table if exists user_info;
create table user_info (
id bigint not null primary key auto_increment,
name varchar(64) not null,
login_name varchar(64) not null,
password varchar(64) not null,
status int not null default 1
);

drop table if exists user_role;
create table user_role (
id bigint not null primary key auto_increment,
user_id int not null,
role_id int not null
);
```