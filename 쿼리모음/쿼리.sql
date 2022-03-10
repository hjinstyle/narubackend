use ebdb;
use naru;

CREATE SCHEMA naru DEFAULT CHARACTER SET utf8;

CREATE TABLE `naru`.`user` (
  `userId` VARCHAR(500) NOT NULL,
  `userName` VARCHAR(100) NULL,
  `email` VARCHAR(100) NULL,
  `password` VARCHAR(1000) NULL,
  `token` VARCHAR(45) NULL,
  PRIMARY KEY (`userId`));
/*자동 증가컬럼으로 변경 하기*/
ALTER TABLE naru.user MODIFY userId INT NOT NULL AUTO_INCREMENT;
/*자동증가 초기화값 설정*/
ALTER TABLE naru.user AUTO_INCREMENT = 1; 

/*이메일 계정 낫널로 변경하기*/
ALTER TABLE naru.user MODIFY email VARCHAR(100) NOT NULL;

/*todo 테이블 생성*/
CREATE TABLE `naru`.`todo` (
  `id` VARCHAR(500) NOT NULL,
  `userId` VARCHAR(100) NULL,
  `title` VARCHAR(100) NULL,
  `done` VARCHAR(1000) NULL,
  PRIMARY KEY (`id`));
/*자동 증가컬럼으로 변경 하기*/
ALTER TABLE naru.todo MODIFY id INT NOT NULL AUTO_INCREMENT;
commit;





select * from naru.user;
delete from naru.user;
select * from naru.user where email = 'james1@naver.com';


commit;

select * from naru.todo;
delete from naru.todo;

