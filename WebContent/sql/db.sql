create table t_user(
	id int primary key not null auto_increment,
	name varchar(30) not null unique comment '用户名',
	password varchar(128) not null comment '密码',
	email varchar(50) not null unique comment '邮件',
	phone varchar(11) not null comment '电话'
);

create table t_goods_kind(
	id int primary key not null auto_increment,
	name varchar(30) not null
);

create table t_goods(
	id int primary key not null auto_increment,
	name varchar(30) not null,
	brief_intro text not null comment '简介',
	detail_intro text not null comment '详细描述',
	price float not null,
	small_img varchar(50) not null comment '图片地址',
	big_img varchar(50),
	score float not null comment '商品评价',
	kind int not null comment '商品种类',
	constraint fk_goods_goodskind foreign key (kind) references t_goods_kind(id)
);

create table t_address(
	id int primary key not null auto_increment,
	uid int not null comment '地址拥有者',
	city varchar(10) not null,
	province varchar(10) not null,
	detail varchar(200) not null,
	constraint fk_address_user foreign key (uid) references t_user(id)
);

create table t_order(
	id varchar(30) not null auto_increment,
	uid int not null comment '订单拥有者',
	date date not null comment '订单生成日期',
	status int not null comment '订单状态',
	addr_id int comment '订单地址',
	constraint fk_order_user foreign key (uid) references t_user(id),
	constraint fk_order_address foreign key (addr_id) references t_address(id)
);

create table t_detail(
	id int primary key not null auto_increment,
	oid varchar(30) not null comment '订单号',
	gid int not null comment '商品号',
	num int not null comment '数量',
	constraint fk_detail_order foreign key (oid) references t_order(id),
	constraint fk_detail_goods foreign key (gid) references t_goods(id)
);

create table t_comment(
	id int primary key not null auto_increment,
	score float not null,
	content text not null,
	uid int not null comment '评论者',
	gid int not null,
	constraint fk_comment_user foreign key (uid) references t_user(id),
	constraint fk_comment_goods foreign key (gid) references t_goods(id)
);

/*根据order_id更新订单*/
UPDATE t_order SET status=?,addr_id=? WHERE id =?

/*添加地址至t_address表*/
INSERT INTO t_address(id,uid,city,province,detail) VALUES(?,?,?,?,?)

/*提交订单至t_oreder表*/
INSERT INTO t_order(id,uid,date,status) VALUES(?,?,?,?)

/*添加用户*/
insert into t_user(name,password,email,phone) values(?,?,?,?)

/*根据用户名和密码查询用户*/
select * from t_user where name=? and password=?

/*查找所有的用户*/
select * from t_user

/*对商品按照商品id进行降序排序*/
select g.id,g.name,g.brief_intro,g.detail_intro,g.price,g.small_img,g.big_img,g.score,gk.id,gk.name from t_goods g join t_goods_kind gk on g.kind=gk.id order by g.id desc limit ?,?

/*升序展示所有的商品并分页*/
select g.id,g.name,g.brief_intro,g.detail_intro,g.price,g.small_img,g.big_img,g.score,gk.id,gk.name from t_goods g join t_goods_kind gk on g.kind=gk.id order by g.price asc limit ?,?

/*降序展示所有的商品并分页*/
select g.id,g.name,g.brief_intro,g.detail_intro,g.price,g.small_img,g.big_img,g.score,gk.id,gk.name from t_goods g join t_goods_kind gk on g.kind=gk.id order by g.price desc limit ?,?

/*根据商品种类展示商品*/
select g.id,g.name,g.brief_intro,g.detail_intro,g.price,g.small_img,g.big_img,g.score,gk.id,gk.name from t_goods g join t_goods_kind gk on g.kind=gk.id where gk.id = ?

/*根据商品id展示商品*/
select g.id,g.name,g.brief_intro,g.detail_intro,g.price,g.small_img,g.big_img,g.score,gk.id,gk.name from t_goods g join t_goods_kind gk on g.kind=gk.id where g.id = ?

select g.id,g.name,g.brief_intro,g.detail_intro,g.price,g.small_img,g.big_img,g.score,gk.id,gk.name from t_goods g join t_goods_kind gk on g.kind=gk.id where g.name like concat('%','张','%') or g.brief_intro like concat('%','子','%') or g.detail_intro like concat('%','甜','%');

select count(*) from ? where name like concat('%',?,'%') or brief_intro like concat('%',?,'%') or detail_intro like concat('%',?,'%');