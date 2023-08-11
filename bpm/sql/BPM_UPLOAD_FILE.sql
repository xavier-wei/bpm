create table BPM_UPLOAD_FILE
(
    Id             bigint identity
        constraint BPM_UPLOAD_FILE_pk
            primary key,
    FORM_ID          VARCHAR(50)                                        not null,
    FILE_NAME        NVARCHAR(200) collate Chinese_Taiwan_Stroke_CS_AS  not null,
    FILE_SIZE        NVARCHAR(20) collate Chinese_Taiwan_Stroke_CS_AS   not null,
    AUTHOR_NAME      NVARCHAR(20) collate Chinese_Taiwan_Stroke_CS_AS   not null,
    FILE_DESCRIPTION NVARCHAR(1000) collate Chinese_Taiwan_Stroke_CS_AS,
    FILE_PATH        NVARCHAR(1000) collate Chinese_Taiwan_Stroke_CS_AS not null,
    UPDATE_USER      NVARCHAR(20) collate Chinese_Taiwan_Stroke_CS_AS,
    UPDATE_TIME      DATETIME                                           not null,
    CREATE_USER      NVARCHAR(20) collate Chinese_Taiwan_Stroke_CS_AS,
    CREATE_TIME      DATETIME                                           not null

)


go

exec sp_addextendedproperty 'MS_Description', N'表單附件檔案上傳清單', 'SCHEMA', 'dbo', 'TABLE', 'BPM_UPLOAD_FILE'

go

exec sp_addextendedproperty 'MS_Description', N'唯一識別碼', 'SCHEMA', 'dbo', 'TABLE', 'BPM_UPLOAD_FILE', 'COLUMN',
     'Id'
go

exec sp_addextendedproperty 'MS_Description', N'表單編號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_UPLOAD_FILE', 'COLUMN',
     'FORM_ID'
go

exec sp_addextendedproperty 'MS_Description', N'附件名稱', 'SCHEMA', 'dbo', 'TABLE', 'BPM_UPLOAD_FILE', 'COLUMN',
     'FILE_NAME'
go

exec sp_addextendedproperty 'MS_Description', N'大小', 'SCHEMA', 'dbo', 'TABLE', 'BPM_UPLOAD_FILE', 'COLUMN',
     'FILE_SIZE'
go

exec sp_addextendedproperty 'MS_Description', N'作者姓名', 'SCHEMA', 'dbo', 'TABLE', 'BPM_UPLOAD_FILE', 'COLUMN',
     'AUTHOR_NAME'
go

exec sp_addextendedproperty 'MS_Description', N'附件說明', 'SCHEMA', 'dbo', 'TABLE', 'BPM_UPLOAD_FILE', 'COLUMN',
     'FILE_DESCRIPTION'
go

exec sp_addextendedproperty 'MS_Description', N'檔案路徑', 'SCHEMA', 'dbo', 'TABLE', 'BPM_UPLOAD_FILE', 'COLUMN',
     'FILE_PATH'
go

exec sp_addextendedproperty 'MS_Description', N'異動人員', 'SCHEMA', 'dbo', 'TABLE', 'BPM_UPLOAD_FILE', 'COLUMN',
     'UPDATE_USER'
go

exec sp_addextendedproperty 'MS_Description', N'異動日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_UPLOAD_FILE', 'COLUMN',
     'UPDATE_TIME'
go

exec sp_addextendedproperty 'MS_Description', N'建檔人員', 'SCHEMA', 'dbo', 'TABLE', 'BPM_UPLOAD_FILE', 'COLUMN',
     'CREATE_USER'
go

exec sp_addextendedproperty 'MS_Description', N'建檔日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_UPLOAD_FILE', 'COLUMN',
     'CREATE_TIME'
go





