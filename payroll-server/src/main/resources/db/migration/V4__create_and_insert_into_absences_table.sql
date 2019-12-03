CREATE TABLE absences(
    employeeId VARCHAR(50) NOT NULL,
    leaveDate DATE NOT NULL,
    leaveReason VARCHAR(20) NOT NULL,
    createdTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedTime TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
);

insert into absences (employeeId, leaveDate, leaveReason) values ('emp201110101000003', '2019-09-02', 'SICK_LEAVE');
insert into absences (employeeId, leaveDate, leaveReason) values ('emp201110101000003', '2019-09-03', 'CASUAL_LEAVE');
insert into absences (employeeId, leaveDate, leaveReason) values ('emp201110101000003', '2019-09-04', 'MATERNITY_LEAVE');
insert into absences (employeeId, leaveDate, leaveReason) values ('emp201110101000003', '2019-09-05', 'DISAPPROVED_LEAVE');
