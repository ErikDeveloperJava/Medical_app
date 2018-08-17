create database medical_app character set utf8 collate utf8_general_ci;

use medical_app;

create table department(
  id int not null auto_increment primary key ,
  name_en varchar(255) not null ,
  name_ru varchar(255) not null ,
  name_arm varchar(255) not null
)engine InnoDB character set utf8 collate  utf8_general_ci;

create table days(
  id int not null auto_increment primary key ,
  name_en varchar(255) not null ,
  name_ru varchar(255) not null ,
  name_arm varchar(255) not null
)engine InnoDB character set utf8 collate  utf8_general_ci;

create table doctor(
  id int not null auto_increment primary key ,
  information text not null ,
  department_id int not null ,
  foreign key (department_id) references department(id)on delete cascade
)engine InnoDB character set utf8 collate  utf8_general_ci;

create table working_days(
  id int not null  auto_increment primary key ,
  day_id int not null ,
  working_hours varchar(255) not null ,
  doctor_id int not null ,
  foreign key (day_id) references days(id) on delete cascade ,
  foreign key (doctor_id) references doctor(id) on delete cascade
)engine InnoDB character set utf8 collate  utf8_general_ci;

create table user(
  id int not null auto_increment primary key ,
  name varchar(255) not null ,
  surname varchar(255) not null ,
  username varchar(255) not null unique ,
  password varchar(255) not null ,
  img_url varchar(255) not null ,
  role varchar(255) not null ,
  doctor_id int,
  foreign key (doctor_id) references doctor(id) on delete cascade
)engine InnoDB character set utf8 collate  utf8_general_ci;

create table registered_info(
  id int not null auto_increment primary key ,
  title varchar(255) not null ,
  date datetime not null ,
  message varchar(255) not null
)engine InnoDB character set utf8 collate  utf8_general_ci;

create table doctor_registered(
  id int not null auto_increment primary key ,
  user_id int not null ,
  doctor_id int not null ,
  status varchar(255) not null ,
  registered_info_id int not null ,
  foreign key (registered_info_id) references registered_info(id) on delete cascade,
  foreign key (user_id) references user(id) on delete cascade ,
  foreign key (doctor_id) references user(id) on delete cascade
)engine InnoDB character set utf8 collate  utf8_general_ci;

create table post(
  id int not null auto_increment primary key ,
  title varchar(255) not null ,
  description text not null ,
  img_url varchar(255) not null ,
  created_date datetime not null ,
  user_id int not null ,
  foreign key (user_id)references user(id) on delete cascade
)engine InnoDB character set utf8 collate  utf8_general_ci;

create table comment(
  id int not null auto_increment primary key ,
  comment text not null ,
  send_date datetime not null ,
  post_id int not null ,
  user_id int not null ,
  foreign key (post_id) references post(id) on delete cascade ,
  foreign key (user_id) references user(id) on delete cascade
)engine InnoDB character set utf8 collate  utf8_general_ci;


insert into days(name_en, name_ru, name_arm) VALUES
  ('Monday','Понедельник','Երկուշաբթի'),
  ('Tuesday','Вторник','Երեքշաբթի'),
  ('Wednesday','Среда','Չորեքշաբթի'),
  ('Thursday','Четверг','Հինգշաբթի'),
  ('Friday','Пятница','ՈՒրբաթ'),
  ('Saturday','Суббота','Շաբաթ'),
  ('Sunday','Воскресенье','Կիրակի');

insert into user (name, surname, username, password, img_url, role, doctor_id) values
  ('Admin','Admin','admin','$2a$04$UHxlDujmuzhGxfYiV7oG1OqUG3d7kwnGaQIMfITBZlhacRsH88R5K','admin/admin.png','ADMIN',null);