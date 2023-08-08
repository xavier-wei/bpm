create table BPM_FUNCTION
(
    Id                 bigint identity
        constraint PK_BPM_Function
        primary key,
    Function_Id        varchar(20) not null
        constraint AK_Function_Id
            unique,
    Function_Name      nvarchar(50) collate Chinese_Taiwan_Stroke_CS_AS not null,
    Function_Descript  nvarchar(500) collate Chinese_Taiwan_Stroke_CS_AS,
    Function_Path      varchar(500),
    Function_Category  varchar     not null,
    Sort_No            decimal(3)  not null,
    Master_Function_Id varchar(20),
    Status             varchar     not null,
    Update_User        varchar(20) not null,
    Update_Time        datetime    not null
) go

exec sp_addextendedproperty 'MS_Description', N'功能檔', 'SCHEMA', 'dbo', 'TABLE', 'BPM_FUNCTION'
go

insert into dbo.BPM_FUNCTION (Id, Function_Id, Function_Name, Function_Descript, Function_Path, Function_Category, Sort_No, Master_Function_Id, Status, Update_User, Update_Time)
values  (44, N'bpm000', N'行政支援系統', N'行政支援系統', N'', N'0', 1, N'', N'Y', N'SYS', N'2023-07-27 00:00:00.470'),
        (45, N'bpm001', N'表單管理', N'表單管理', N'', N'0', 1, N'bpm000', N'Y', N'SYS', N'2023-07-27 00:00:00.470'),
        (46, N'bpm0000', N'新增表單', N'新增表單', N'/', N'0', 1, N'bpm001', N'Y', N'SYS', N'2023-07-27 00:00:00.470'),
        (47, N'bpm0100', N'ISMS簽核表單', N'ISMS簽核表單', N'/', N'0', 2, N'bpm0000', N'Y', N'SYS', N'2023-07-27 00:00:00.470'),
        (48, N'bpm0200', N'處理與通知', N'處理與通知', N'/', N'0', 1, N'bpm001', N'Y', N'SYS', N'2023-07-27 00:00:00.470'),
        (49, N'bpm0300', N'流程追蹤', N'流程追蹤', N'/', N'0', 2, N'bpm001', N'Y', N'SYS', N'2023-07-27 00:00:00.470'),
        (50, N'l410Apply', N'L410-共用系統使用者帳號申請單', N'L410-共用系統使用者帳號申請單', N'/l410Apply', N'1', 3, N'bpm0100', N'Y', N'SYS', N'2023-07-27 00:00:00.470'),
        (51, N'l414Apply', N'L414-網路服務連結申請單', N'L414-網路服務連結申請單', N'/l414Apply', N'1', 4, N'bpm0100', N'Y', N'SYS', N'2023-07-27 00:00:00.470'),
        (52, N'pending', N'待處理', N'待處理', N'/pending', N'1', 1, N'bpm0200', N'Y', N'SYS', N'2023-07-27 00:00:00.470'),
        (53, N'notify', N'申請', N'申請', N'/notify', N'1', 1, N'bpm0300', N'Y', N'SYS', N'2023-07-27 00:00:00.470');
