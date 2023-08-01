create table BPM_SIGN_STATUS
(
    UUID                UNIQUEIDENTIFIER default NEWID()                  not null,
    FORM_ID             VARCHAR(50)                                       not null,
    PROCESS_INSTANCE_ID VARCHAR(50)                                       not null,
    TASK_ID             VARCHAR(50)                                       not null,
    TASK_NAME           NVARCHAR(50) collate Chinese_Taiwan_Stroke_CS_AS  not null,
    SIGNER_ID           NVARCHAR(20) collate Chinese_Taiwan_Stroke_CS_AS  not null,
    SIGNER              NVARCHAR(20) collate Chinese_Taiwan_Stroke_CS_AS  not null,
    SIGN_UNIT           NVARCHAR(100) collate Chinese_Taiwan_Stroke_CS_AS not null,
    SIGN_RESULT         VARCHAR                                           not null,
    OPINION             NVARCHAR(1000) collate Chinese_Taiwan_Stroke_CS_AS,
    DIRECTIONS          NVARCHAR(1000) collate Chinese_Taiwan_Stroke_CS_AS,
    SIGNING_DATETIME    DATETIME         default GETDATE()                not null,
    constraint BPM_SIGN_STATUS_pk
        primary key (UUID)
)
    go

exec sp_addextendedproperty 'MS_Description', N'表單簽核紀錄', 'SCHEMA', 'dbo', 'TABLE', 'BPM_SIGN_STATUS'
go

exec sp_addextendedproperty 'MS_Description', 'UUID', 'SCHEMA', 'dbo', 'TABLE', 'BPM_SIGN_STATUS', 'COLUMN',
     'UUID'
go

exec sp_addextendedproperty 'MS_Description', N'表單編號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_SIGN_STATUS', 'COLUMN',
     'FORM_ID'
go

exec sp_addextendedproperty 'MS_Description', N'流程實體編號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_SIGN_STATUS', 'COLUMN',
     'PROCESS_INSTANCE_ID'
go

exec sp_addextendedproperty 'MS_Description', N'任務編號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_SIGN_STATUS', 'COLUMN',
     'TASK_ID'
go

exec sp_addextendedproperty 'MS_Description', N'簽核人員編號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_SIGN_STATUS', 'COLUMN',
     'SIGNER_ID'
go

exec sp_addextendedproperty 'MS_Description', N'簽核人員', 'SCHEMA', 'dbo', 'TABLE', 'BPM_SIGN_STATUS', 'COLUMN',
     'SIGNER'
go

exec sp_addextendedproperty 'MS_Description', N'單位名稱', 'SCHEMA', 'dbo', 'TABLE', 'BPM_SIGN_STATUS', 'COLUMN',
     'SIGN_UNIT'
go

exec sp_addextendedproperty 'MS_Description', N'簽核結果', 'SCHEMA', 'dbo', 'TABLE', 'BPM_SIGN_STATUS', 'COLUMN',
     'SIGN_RESULT'
go

exec sp_addextendedproperty 'MS_Description', N'處理意見', 'SCHEMA', 'dbo', 'TABLE', 'BPM_SIGN_STATUS', 'COLUMN',
     'OPINION'
go

exec sp_addextendedproperty 'MS_Description', N'說明', 'SCHEMA', 'dbo', 'TABLE', 'BPM_SIGN_STATUS', 'COLUMN',
     'DIRECTIONS'
go

exec sp_addextendedproperty 'MS_Description', N'簽核日期時間', 'SCHEMA', 'dbo', 'TABLE', 'BPM_SIGN_STATUS', 'COLUMN',
     'SIGNING_DATETIME'
go

