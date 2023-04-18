-- Spring Boot 가동시 초기 데이터 생성 시 사용
insert into users values(90001, now(), 'user1', 'test111', '701010-1111111');
insert into users values(90002, now(), 'user2', 'test222', '801010-1111111');
insert into users values(90003, now(), 'user3', 'test333', '901010-1111111');
insert into users values(90004, now(), 'user4', 'test444', '601010-1111111');


insert into post values(10001, 'My first post', 90001);
insert into post values(10002, 'My first post', 90001);