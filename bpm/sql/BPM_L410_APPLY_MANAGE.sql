-- auto-generated definition
create table BPM_L410_APPLY_MANAGE
(
    Id                 bigint identity
        constraint BPM_L410_APPLY_MANAGE_pk
        primary key,
    SYSTEM_APPLY         varchar(3)   not null,
    SYSTEM_APPLY_NAME    nvarchar(40),
    CHECKBOX             nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    SYS                  nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    SYSTEM_APPLY_INPUT   nvarchar(50),
    SYS_CHANGE           nvarchar(50),
    EMAIL_APPLY1         nvarchar(20),
    EMAIL_APPLY2         nvarchar(20),
    IS_UNIT_ADM          nvarchar,
    IS_UNIT_DATA_MGR     nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    IS_WEB_SITE_OTHER    nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    OTHER_REASON         nvarchar(30),
    ADM_UNIT             nvarchar(7),
    ADM_STATUS           nvarchar(10),
    ADM_ENABLE_DATE      datetime,
    ADM_NAME             nvarchar(10),
    OTHER_SYS            nvarchar(20),
    OTHER_SYS_ACCOUNT    nvarchar(20),
    IS_COLON             nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    APPLY_VERSION        nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    PERMISSIONS_VERSION  nvarchar default '0' collate Chinese_Taiwan_Stroke_CS_AS,
    CREATE_USER          nvarchar(20) not null,
    CREATE_TIME          datetime     not null,
    SYSTEM_APPLY_NUMERIC int
)
    go

exec sp_addextendedproperty 'MS_Description', 'L410申請項目管理', 'SCHEMA', 'dbo', 'TABLE', 'BPM_L410_APPLY_MANAGE'
go

exec sp_addextendedproperty 'MS_Description', '項目編號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_L410_APPLY_MANAGE', 'COLUMN',
     'SYSTEM_APPLY'
go

exec sp_addextendedproperty 'MS_Description', '項目名稱', 'SCHEMA', 'dbo', 'TABLE', 'BPM_L410_APPLY_MANAGE', 'COLUMN',
     'SYSTEM_APPLY_NAME'
go

exec sp_addextendedproperty 'MS_Description', '選擇的checkbox對象', 'SCHEMA', 'dbo', 'TABLE', 'BPM_L410_APPLY_MANAGE',
     'COLUMN', 'CHECKBOX'
go

exec sp_addextendedproperty 'MS_Description', '處理權限 (radio選擇對象)', 'SCHEMA', 'dbo', 'TABLE', 'BPM_L410_APPLY_MANAGE',
     'COLUMN', 'SYS'
go

exec sp_addextendedproperty 'MS_Description', '申請項目輸入框', 'SCHEMA', 'dbo', 'TABLE', 'BPM_L410_APPLY_MANAGE', 'COLUMN',
     'SYSTEM_APPLY_INPUT'
go

exec sp_addextendedproperty 'MS_Description', '處理權限異動資料', 'SCHEMA', 'dbo', 'TABLE', 'BPM_L410_APPLY_MANAGE', 'COLUMN',
     'SYS_CHANGE'
go

exec sp_addextendedproperty 'MS_Description', '申請帳號1', 'SCHEMA', 'dbo', 'TABLE', 'BPM_L410_APPLY_MANAGE', 'COLUMN',
     'EMAIL_APPLY1'
go

exec sp_addextendedproperty 'MS_Description', '申請帳號2', 'SCHEMA', 'dbo', 'TABLE', 'BPM_L410_APPLY_MANAGE', 'COLUMN',
     'EMAIL_APPLY2'
go

exec sp_addextendedproperty 'MS_Description', '是否是部門管理者', 'SCHEMA', 'dbo', 'TABLE', 'BPM_L410_APPLY_MANAGE', 'COLUMN',
     'IS_UNIT_ADM'
go

exec sp_addextendedproperty 'MS_Description', '是否是部門資料維護者', 'SCHEMA', 'dbo', 'TABLE', 'BPM_L410_APPLY_MANAGE', 'COLUMN',
     'IS_UNIT_DATA_MGR'
go

exec sp_addextendedproperty 'MS_Description', '是否是網站帳號其他', 'SCHEMA', 'dbo', 'TABLE', 'BPM_L410_APPLY_MANAGE', 'COLUMN',
     'IS_WEB_SITE_OTHER'
go

exec sp_addextendedproperty 'MS_Description', '其他說明', 'SCHEMA', 'dbo', 'TABLE', 'BPM_L410_APPLY_MANAGE', 'COLUMN',
     'OTHER_REASON'
go

exec sp_addextendedproperty 'MS_Description', '管理單位', 'SCHEMA', 'dbo', 'TABLE', 'BPM_L410_APPLY_MANAGE', 'COLUMN',
     'ADM_UNIT'
go

exec sp_addextendedproperty 'MS_Description', '處理情形', 'SCHEMA', 'dbo', 'TABLE', 'BPM_L410_APPLY_MANAGE', 'COLUMN',
     'ADM_STATUS'
go

exec sp_addextendedproperty 'MS_Description', '生效日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_L410_APPLY_MANAGE', 'COLUMN',
     'ADM_ENABLE_DATE'
go

exec sp_addextendedproperty 'MS_Description', '處理人員', 'SCHEMA', 'dbo', 'TABLE', 'BPM_L410_APPLY_MANAGE', 'COLUMN',
     'ADM_NAME'
go

exec sp_addextendedproperty 'MS_Description', '其他系統名稱', 'SCHEMA', 'dbo', 'TABLE', 'BPM_L410_APPLY_MANAGE', 'COLUMN',
     'OTHER_SYS'
go

exec sp_addextendedproperty 'MS_Description', '其他系統帳號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_L410_APPLY_MANAGE', 'COLUMN',
     'OTHER_SYS_ACCOUNT'
go

exec sp_addextendedproperty 'MS_Description', '是否需要冒號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_L410_APPLY_MANAGE', 'COLUMN',
     'IS_COLON'
go

exec sp_addextendedproperty 'MS_Description', '申請項目版本', 'SCHEMA', 'dbo', 'TABLE', 'BPM_L410_APPLY_MANAGE', 'COLUMN',
     'APPLY_VERSION'
go

exec sp_addextendedproperty 'MS_Description', '處理權限版本', 'SCHEMA', 'dbo', 'TABLE', 'BPM_L410_APPLY_MANAGE', 'COLUMN',
     'PERMISSIONS_VERSION'
go

exec sp_addextendedproperty 'MS_Description', '建檔人員', 'SCHEMA', 'dbo', 'TABLE', 'BPM_L410_APPLY_MANAGE', 'COLUMN',
     'CREATE_USER'
go

exec sp_addextendedproperty 'MS_Description', '建檔日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_L410_APPLY_MANAGE', 'COLUMN',
     'CREATE_TIME'
go

