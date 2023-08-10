create table BPM_USER_POSITION
(
    USER_ID     varchar(20) not null,
    DEPT_ID     varchar(20) not null,
    POSITION_ID varchar(20) not null,
    POSITION    nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS not null
) go

exec sp_addextendedproperty 'MS_Description', N'使用者職稱', 'SCHEMA', 'dbo', 'TABLE', 'BPM_USER_POSITION'
go

exec sp_addextendedproperty 'MS_Description', N'使用者ID', 'SCHEMA', 'dbo', 'TABLE', 'BPM_USER_POSITION', 'COLUMN',
     'USER_ID'
go

exec sp_addextendedproperty 'MS_Description', N'部門編號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_USER_POSITION', 'COLUMN',
     'DEPT_ID'
go

exec sp_addextendedproperty 'MS_Description', N'職稱編號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_USER_POSITION', 'COLUMN',
     'POSITION_ID'
go

exec sp_addextendedproperty 'MS_Description', N'職稱', 'SCHEMA', 'dbo', 'TABLE', 'BPM_USER_POSITION', 'COLUMN',
     'POSITION'
go

insert into dbo.USERS (USER_ID, ACNT_IS_VALID, CREATE_TIMESTAMP, CREATE_USER_ID, DEPT_ID, EMAIL, EMP_ID, LAST_LOGIN_DATE, LAST_LOGIN_IP, LDAP_ID, MODIFY_USER_ID, MODIFY_TIMESTAMP, USER_NAME, TEL1, TEL2, TITLE_ID, LINE_TOKEN, FROM_HR, ORG_ID)
values
        (N'15.0.1', N'Y', N'2023-08-02 11:36:09.000', N'SYNC_HR', N'15', N'test@test.com', N'15.0.1', null, null, null, N'n5000', N'2023-08-02 11:37:22.000', N'李主管', N'87897545', N'1233', N'20', null, null, null),
        (N'16.0.1', N'Y', N'2023-08-02 11:36:09.000', N'SYNC_HR', N'16', N'test@test.com', N'16.0.1', null, null, null, N'n5000', N'2023-08-02 11:37:22.000', N'林科長', N'87897545', N'1233', N'20', null, null, null),
        (N'16.0.2', N'Y', N'2023-08-02 11:36:09.000', N'SYNC_HR', N'16', N'test@test.com', N'16.0.2', null, null, null, N'n5000', N'2023-08-02 11:37:22.000', N'陳科員', N'87897545', N'1233', N'20', null, null, null),
        (N'35.0.1', N'Y', N'2023-08-02 11:48:38.000', N'SYNC_HR', N'35', N'test@test.com', N'35.0.2', null, null, null, N'n5000', N'2023-08-02 11:37:22.000', N'彭技正', N'87897545', N'1233', N'20', null, null, null),
        (N'35.0.2', N'Y', N'2023-08-02 11:48:38.000', N'SYNC_HR', N'35', N'test@test.com', N'35.0.2', null, null, null, N'n5000', N'2023-08-02 11:37:22.000', N'黃資推', N'87897545', N'1233', N'20', null, null, null),
        (N'37.0.1', N'Y', N'2023-08-02 11:36:09.000', N'SYNC_HR', N'37', N'test@test.com', N'37.0.1', null, null, null, N'n5000', N'2023-08-02 11:37:22.000', N'林機操', N'87897545', N'1233', N'20', null, null, null),
        (N'37.0.2', N'Y', N'2023-08-02 11:36:09.000', N'SYNC_HR', N'37', N'test@test.com', N'37.0.2', null, null, null, N'n5000', N'2023-08-02 11:37:22.000', N'王複核', N'87897545', N'1233', N'20', null, null, null);

insert into dbo.BPM_USER_POSITION (USER_ID, DEPT_ID, POSITION_ID, POSITION)
values  (N'16.0.1', N'16', N'1600', N'工程管理處一科科長'),
        (N'16.0.2', N'16', N'1601', N'工程管理處一科科員'),
        (N'15.0.1', N'15', N'1500', N'工程管理處處長'),
        (N'35.0.1', N'35', N'3500', N'簡任技正'),
        (N'35.0.2', N'35', N'3501', N'資推小組'),
        (N'37.0.1', N'37', N'3701', N'機房操作人員'),
        (N'37.0.2', N'37', N'3702', N'安碁複核人員'),
        (N'37.0.3', N'37', N'3703', N'機房管理人員');
