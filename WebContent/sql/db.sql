create table t_user(
	id int primary key not null auto_increment,
	name varchar(30) not null unique comment '�û���',
	password varchar(128) not null comment '����',
	email varchar(50) not null unique comment '�ʼ�',
	phone varchar(11) not null comment '�绰'
);

create table t_goods_kind(
	id int primary key not null auto_increment,
	name varchar(30) not null
);

create table t_goods(
	id int primary key not null auto_increment,
	name varchar(30) not null,
	brief_intro text not null comment '���',
	detail_intro text not null comment '��ϸ����',
	price float not null,
	small_img varchar(50) not null comment 'ͼƬ��ַ',
	big_img varchar(50),
	score float not null comment '��Ʒ����',
	kind int not null comment '��Ʒ����',
	constraint fk_goods_goodskind foreign key (kind) references t_goods_kind(id)
);

create table t_address(
	id int primary key not null auto_increment,
	uid int not null comment '��ַӵ����',
	city varchar(10) not null,
	province varchar(10) not null,
	detail varchar(200) not null,
	constraint fk_address_user foreign key (uid) references t_user(id)
);

create table t_order(
	id varchar(30) not null auto_increment,
	uid int not null comment '����ӵ����',
	date date not null comment '������������',
	status int not null comment '����״̬',
	addr_id int comment '������ַ',
	constraint fk_order_user foreign key (uid) references t_user(id),
	constraint fk_order_address foreign key (addr_id) references t_address(id)
);

create table t_detail(
	id int primary key not null auto_increment,
	oid varchar(30) not null comment '������',
	gid int not null comment '��Ʒ��',
	num int not null comment '����',
	constraint fk_detail_order foreign key (oid) references t_order(id),
	constraint fk_detail_goods foreign key (gid) references t_goods(id)
);

create table t_comment(
	id int primary key not null auto_increment,
	score float not null,
	content text not null,
	uid int not null comment '������',
	gid int not null,
	constraint fk_comment_user foreign key (uid) references t_user(id),
	constraint fk_comment_goods foreign key (gid) references t_goods(id)
);

/*����order_id���¶���*/
UPDATE t_order SET status=?,addr_id=? WHERE id =?

/*��ӵ�ַ��t_address��*/
INSERT INTO t_address(id,uid,city,province,detail) VALUES(?,?,?,?,?)

/*�ύ������t_oreder��*/
INSERT INTO t_order(id,uid,date,status) VALUES(?,?,?,?)

/*����û�*/
insert into t_user(name,password,email,phone) values(?,?,?,?)

/*�����û����������ѯ�û�*/
select * from t_user where name=? and password=?

/*�������е��û�*/
select * from t_user

/*����Ʒ������Ʒid���н�������*/
select g.id,g.name,g.brief_intro,g.detail_intro,g.price,g.small_img,g.big_img,g.score,gk.id,gk.name from t_goods g join t_goods_kind gk on g.kind=gk.id order by g.id desc limit ?,?

/*����չʾ���е���Ʒ����ҳ*/
select g.id,g.name,g.brief_intro,g.detail_intro,g.price,g.small_img,g.big_img,g.score,gk.id,gk.name from t_goods g join t_goods_kind gk on g.kind=gk.id order by g.price asc limit ?,?

/*����չʾ���е���Ʒ����ҳ*/
select g.id,g.name,g.brief_intro,g.detail_intro,g.price,g.small_img,g.big_img,g.score,gk.id,gk.name from t_goods g join t_goods_kind gk on g.kind=gk.id order by g.price desc limit ?,?

/*������Ʒ����չʾ��Ʒ*/
select g.id,g.name,g.brief_intro,g.detail_intro,g.price,g.small_img,g.big_img,g.score,gk.id,gk.name from t_goods g join t_goods_kind gk on g.kind=gk.id where gk.id = ?

/*������Ʒidչʾ��Ʒ*/
select g.id,g.name,g.brief_intro,g.detail_intro,g.price,g.small_img,g.big_img,g.score,gk.id,gk.name from t_goods g join t_goods_kind gk on g.kind=gk.id where g.id = ?

select g.id,g.name,g.brief_intro,g.detail_intro,g.price,g.small_img,g.big_img,g.score,gk.id,gk.name from t_goods g join t_goods_kind gk on g.kind=gk.id where g.name like concat('%','��','%') or g.brief_intro like concat('%','��','%') or g.detail_intro like concat('%','��','%');

select count(*) from ? where name like concat('%',?,'%') or brief_intro like concat('%',?,'%') or detail_intro like concat('%',?,'%');