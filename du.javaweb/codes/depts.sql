create database if not exists javaweb;

use javaweb;

drop table if exists depts;

create table if not exists depts (
    dno int primary key,
    dname varchar(255),
    loc varchar(255)
);

insert into
    depts(dno, dname, loc)
    values(1, '销售部', '销售部位置'),
          (2, '研发部', '研发部位置'),
          (3, '技术部', '技术部位置'),
          (4, '媒体部', '媒体部位置');

commit;

select * from depts;