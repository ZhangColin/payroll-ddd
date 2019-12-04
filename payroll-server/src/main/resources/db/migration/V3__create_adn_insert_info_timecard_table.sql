CREATE TABLE timecards(
    id INT NOT NULL AUTO_INCREMENT,
    employeeId VARCHAR(50) NOT NULL,
    workDay DATE NOT NULL,
    workHours INT NOT NULL,
    createdTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedTime TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

insert into timecards (employeeId, workDay, workHours) values ('emp200109101000001', '2019-11-04', 8);
insert into timecards (employeeId, workDay, workHours) values ('emp200109101000001', '2019-11-05', 8);
insert into timecards (employeeId, workDay, workHours) values ('emp200109101000001', '2019-11-06', 10);
insert into timecards (employeeId, workDay, workHours) values ('emp200109101000001', '2019-11-07', 9);
insert into timecards (employeeId, workDay, workHours) values ('emp200109101000001', '2019-11-08', 7);
insert into timecards (employeeId, workDay, workHours) values ('emp201107101000002', '2019-11-04', 8);
insert into timecards (employeeId, workDay, workHours) values ('emp201107101000002', '2019-11-05', 10);
insert into timecards (employeeId, workDay, workHours) values ('emp201107101000002', '2019-11-06', 5);
insert into timecards (employeeId, workDay, workHours) values ('emp201107101000002', '2019-11-07', 9);
insert into timecards (employeeId, workDay, workHours) values ('emp201107101000002', '2019-11-08', 7);