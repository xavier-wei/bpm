create table EIP_BPM_SIGN_STATUS
(
    FORM_ID             varchar(50)                                      not null,
    PROCESS_INSTANCE_ID varchar(50)                                      not null,
    TASK_ID             varchar(50)                                      not null,
    SIGNER_ID           nvarchar(20)                                     not null,
    SIGNER              nvarchar(20) collate Chinese_Taiwan_Stroke_CS_AS not null,
    STATUS              varchar                                          not null,
    OPINION             nvarchar(1000) collate Chinese_Taiwan_Stroke_CS_AS,
    SIGNING_DATETIME    datetime default getdate()                       not null,
    constraint EIP_BPM_SIGN_STATUS_pk
        primary key (FORM_ID, PROCESS_INSTANCE_ID, TASK_ID, SIGNER_ID)
)
go

exec sp_addextendedproperty 'MS_Description', N'表單編號', 'SCHEMA', 'dbo', 'TABLE', 'EIP_BPM_SIGN_STATUS', 'COLUMN',
     'FORM_ID'
go

exec sp_addextendedproperty 'MS_Description', N'流程實體編號', 'SCHEMA', 'dbo', 'TABLE', 'EIP_BPM_SIGN_STATUS',
     'COLUMN', 'PROCESS_INSTANCE_ID'
go

exec sp_addextendedproperty 'MS_Description', N'任務編號', 'SCHEMA', 'dbo', 'TABLE', 'EIP_BPM_SIGN_STATUS', 'COLUMN',
     'TASK_ID'
go

exec sp_addextendedproperty 'MS_Description', N'簽核人員編號', 'SCHEMA', 'dbo', 'TABLE', 'EIP_BPM_SIGN_STATUS',
     'COLUMN', 'SIGNER_ID'
go

exec sp_addextendedproperty 'MS_Description', N'簽核人員', 'SCHEMA', 'dbo', 'TABLE', 'EIP_BPM_SIGN_STATUS', 'COLUMN',
     'SIGNER'
go

exec sp_addextendedproperty 'MS_Description', N'簽核結果', 'SCHEMA', 'dbo', 'TABLE', 'EIP_BPM_SIGN_STATUS', 'COLUMN',
     'STATUS'
go

exec sp_addextendedproperty 'MS_Description', N'處理意見', 'SCHEMA', 'dbo', 'TABLE', 'EIP_BPM_SIGN_STATUS', 'COLUMN',
     'OPINION'
go

exec sp_addextendedproperty 'MS_Description', N'簽核日期時間', 'SCHEMA', 'dbo', 'TABLE', 'EIP_BPM_SIGN_STATUS',
     'COLUMN', 'SIGNING_DATETIME'
go
