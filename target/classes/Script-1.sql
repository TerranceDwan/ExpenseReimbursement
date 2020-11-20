drop table if exists requests;
drop table if exists employees;

CREATE table employees(
emp_id serial PRIMARY KEY,
fname varchar NOT NULL,
lname varchar NOT NULL,
username varchar UNIQUE NOT NULL,
passcode varchar NOT NULL,
email varchar NOT NULL,
address varchar,
manager_id int,
emp_status varchar(1)
);
CREATE TABLE requests(
req_id serial PRIMARY KEY,
status varchar NOT NULL,
purchase_date date NOT NULL,
purpose varchar NOT NULL,
amount int NOT NULL,
reciept_img bytea,
submitted_date date not null,
additional_comments varchar,
emp_id int REFERENCES employees(emp_id) not null,
manager_id int not null
);
CREATE TABLE request_status(
code varchar(1),
status varchar
);
insert into request_status
values('a', 'approved'),
('d','denied'),
('p', 'pending')

insert into employees 
values(default, 'Terrance', 'Lewis', 'tlewis', 'secret', 'terrance.lewis@tfnb.net', '123 Imaginary Lane, Indianapolis, IN', 3, 'm');
insert into employees 
values(default, 'Calvin', 'Broadus', 'snoop', 'secret', 'snoop.dogg@tfnb.net', '123 Imaginary Lane, Dreamtown, CA', 1, 'm');
insert into employees 
values(default, 'Sean', 'Carter', 'jayzee', 'secret', 'jay.z@tfnb.net', '123 Imaginary Lane, New York, NY', 2, 'm');
insert into employees 
values(default, 'Kendrick', 'Duckworth', 'klamar', 'secret', 'kendrick.lamar@tfnb.net', '123 Imaginary Lane, Dreamtown, CA', 2, 'e');
insert into employees 
values(default, 'Marshall', 'Mathers', 'eminem', 'secret', 'eminem@tfnb.net', '123 Imaginary Lane, Detroit, MI', 1, 'e');
insert into employees 
values(default, 'Earl', 'Stevens', 'earl40', 'secret', 'e40@tfnb.net', '123 Imaginary Lane, Vallejo, CA', 2, 'e');
insert into employees 
values(default, 'Alvin', 'Joiner', 'xzibit', 'secret', 'xzibit@tfnb.net', '123 Imaginary Lane, Detroit, MI', 1, 'e');
insert into employees 
values(default, 'Wasalu', 'Jaco', 'lupef', 'secret', 'lupe.fiasco@tfnb.net', '123 Imaginary Lane, Chicago, IL', 1, 'e');
insert into employees 
values(default, 'Kanye', 'West', 'kanye', 'secret', 'kanye.west@tfnb.net', '123 Imaginary Lane, Chicago, IL', 1, 'e');
insert into employees 
values(default, 'Tupac', 'Shakur', 'tupac', 'secret', 'tupac@tfnb.net', '123 Imaginary Lane, Compton, CA', 2, 'e');
insert into employees 
values(default, 'Tyler', 'Okonma', 'thecreator', 'secret', 'tyler.the.creator@tfnb.net', '123 Imaginary Lane, Ladera Heights, CA', 2, 'e');
insert into employees 
values(default, 'Sean', 'Combs', 'puffy', 'secret', 'p.diddy@tfnb.net', '123 Imaginary Lane, Harlem, NY', 3, 'e');
insert into employees 
values(default, 'Curtis', 'Jackson', '50cent', 'secret', '50.cent@tfnb.net', '123 Imaginary Lane, South Jamaica, NY', 3, 'e');
insert into employees 
values(default, 'Christopher', 'Wallace', 'biggie', 'secret', 'biggie@tfnb.net', '123 Imaginary Lane, Brooklyn, NY', 3, 'e');


