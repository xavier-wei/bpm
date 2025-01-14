create table dbo.BPM_ISMS_L414
(
    FORM_ID                 VARCHAR(50) not null
        constraint BPM_ISMS_L414_pk
            primary key,
    PROCESS_INSTANCE_ID     VARCHAR(50) not null,
    APPLY_DATE              DATETIME    not null,
    FIL_EMPID               NVARCHAR(20) collate Chinese_Taiwan_Stroke_CS_AS not null,
    FIL_NAME                NVARCHAR(20) collate Chinese_Taiwan_Stroke_CS_AS not null,
    FIL_UNIT                NVARCHAR(100) collate Chinese_Taiwan_Stroke_CS_AS not null,
    APP_EMPID               NVARCHAR(20) collate Chinese_Taiwan_Stroke_CS_AS not null,
    APP_NAME                NVARCHAR(20) collate Chinese_Taiwan_Stroke_CS_AS not null,
    APP_UNIT                NVARCHAR(100) collate Chinese_Taiwan_Stroke_CS_AS not null,
    IS_SUBMIT               NVARCHAR(1) default '0' not null,
    IS_ENABLE               NVARCHAR(1) default '0' not null,
    ENABLE_TIME             NVARCHAR(1) default '1' not null,
    WORKING_TIME            NVARCHAR(100) collate Chinese_Taiwan_Stroke_CS_AS,
    OTHER_ENABLE_TIME       NVARCHAR(100) collate Chinese_Taiwan_Stroke_CS_AS,
    SELECTE_EDATE_TYPE      NVARCHAR(1) default '1' not null,
    SDATE                   DATETIME,
    EDATE                   DATETIME,
    OTHERE_EDATE            NVARCHAR(100) collate Chinese_Taiwan_Stroke_CS_AS,
    DEL_ENABLE_DATE         DATETIME,
    SOURCE_IP               NVARCHAR(100) collate Chinese_Taiwan_Stroke_CS_AS,
    TARGET_IP               NVARCHAR(100) collate Chinese_Taiwan_Stroke_CS_AS,
    PORT                    NVARCHAR(50) collate Chinese_Taiwan_Stroke_CS_AS,
    IS_TCP                  NVARCHAR(1) default '0',
    IS_UDP                  NVARCHAR(1) default '0',
    INSTRUCTIONS            NVARCHAR(1000) collate Chinese_Taiwan_Stroke_CS_AS,
    AGREE_TYPE              NVARCHAR(1) default '1',
    SCHEDULE_DATE           DATETIME,
    PARTIAL_AGREE_REASON    NVARCHAR(500) collate Chinese_Taiwan_Stroke_CS_AS,
    NOT_AGREE_REASON        NVARCHAR(500) collate Chinese_Taiwan_Stroke_CS_AS,
    IS_EXTERNAL_FIREWALL    NVARCHAR(1) default '0',
    IS_INTERNAL_FIREWALL    NVARCHAR(1) default '0',
    FIREWALL_CONTENT        NVARCHAR(1000) collate Chinese_Taiwan_Stroke_CS_AS,
    PROCESS_INSTANCE_STATUS NVARCHAR(1) default '0' not null,
    FINISH_DATETIME         DATETIME,
    UPDATE_USER             NVARCHAR(20) collate Chinese_Taiwan_Stroke_CS_AS,
    UPDATE_TIME             DATETIME,
    CREATE_USER             NVARCHAR(20) collate Chinese_Taiwan_Stroke_CS_AS not null,
    CREATE_TIME             DATETIME    not null
) go

exec sp_addextendedproperty 'MS_Description', N'L414-網路服務連結申請單', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414'
go

exec sp_addextendedproperty 'MS_Description', N'表單編號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'FORM_ID'
go

exec sp_addextendedproperty 'MS_Description', N'流程實體編號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'PROCESS_INSTANCE_ID'
go

exec sp_addextendedproperty 'MS_Description', N'申請日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'APPLY_DATE'
go

exec sp_addextendedproperty 'MS_Description', N'填表人員工編號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'FIL_EMPID'
go

exec sp_addextendedproperty 'MS_Description', N'填表人姓名', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'FIL_NAME'
go

exec sp_addextendedproperty 'MS_Description', N'填表人單位名稱', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'FIL_UNIT'
go

exec sp_addextendedproperty 'MS_Description', N'申請人員工編號', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'APP_EMPID'
go

exec sp_addextendedproperty 'MS_Description', N'申請人姓名', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'APP_NAME'
go

exec sp_addextendedproperty 'MS_Description', N'申請人單位名稱', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'APP_UNIT'
go

exec sp_addextendedproperty 'MS_Description', N'是否暫存、送出', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'IS_SUBMIT'
go

exec sp_addextendedproperty 'MS_Description', N'規則', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'IS_ENABLE'
go

exec sp_addextendedproperty 'MS_Description', N'使用時段', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'ENABLE_TIME'
go

exec sp_addextendedproperty 'MS_Description', N'每週一至週五使用內容', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414',
     'COLUMN', 'WORKING_TIME'
go

exec sp_addextendedproperty 'MS_Description', N'使用特殊時段內容', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414',
     'COLUMN', 'OTHER_ENABLE_TIME'
go

exec sp_addextendedproperty 'MS_Description', N'啟用期間類別', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'SELECTE_EDATE_TYPE'
go

exec sp_addextendedproperty 'MS_Description', N'啟用期間開始時間', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414',
     'COLUMN', 'SDATE'
go

exec sp_addextendedproperty 'MS_Description', N'啟用期間結束時間', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414',
     'COLUMN', 'EDATE'
go

exec sp_addextendedproperty 'MS_Description', N'職務異動止說明', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'OTHERE_EDATE'
go

exec sp_addextendedproperty 'MS_Description', N'刪除規則時間', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'DEL_ENABLE_DATE'
go

exec sp_addextendedproperty 'MS_Description', N'來源 IP', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'SOURCE_IP'
go

exec sp_addextendedproperty 'MS_Description', N'目的 IP', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'TARGET_IP'
go

exec sp_addextendedproperty 'MS_Description', N'使用協定(Port)', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'PORT'
go

exec sp_addextendedproperty 'MS_Description', N'傳輸模式是否為TCP', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414',
     'COLUMN', 'IS_TCP'
go

exec sp_addextendedproperty 'MS_Description', N'傳輸模式是否為UDP', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414',
     'COLUMN', 'IS_UDP'
go

exec sp_addextendedproperty 'MS_Description', N'用途說明', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'INSTRUCTIONS'
go

exec sp_addextendedproperty 'MS_Description', N'處理意見', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'AGREE_TYPE'
go

exec sp_addextendedproperty 'MS_Description', N'預定完成日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'SCHEDULE_DATE'
go

exec sp_addextendedproperty 'MS_Description', N'部分同意設定原因', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'PARTIAL_AGREE_REASON'
go

exec sp_addextendedproperty 'MS_Description', N'不同意設定原因', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'NOT_AGREE_REASON'
go

exec sp_addextendedproperty 'MS_Description', N'變更設備：是否為外部防火牆', 'SCHEMA', 'dbo', 'TABLE',
     'BPM_ISMS_L414', 'COLUMN', 'IS_EXTERNAL_FIREWALL'
go

exec sp_addextendedproperty 'MS_Description', N'變更設備：是否為內部防火牆', 'SCHEMA', 'dbo', 'TABLE',
     'BPM_ISMS_L414', 'COLUMN', 'IS_INTERNAL_FIREWALL'
go

exec sp_addextendedproperty 'MS_Description', N'設定內容', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'FIREWALL_CONTENT'
go

exec sp_addextendedproperty 'MS_Description', N'流程實體狀態', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'PROCESS_INSTANCE_STATUS'
go

exec sp_addextendedproperty 'MS_Description', N'實際完成日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'FINISH_DATETIME'
go

exec sp_addextendedproperty 'MS_Description', N'異動人員', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'UPDATE_USER'
go

exec sp_addextendedproperty 'MS_Description', N'異動日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'UPDATE_TIME'
go

exec sp_addextendedproperty 'MS_Description', N'建檔人員', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'CREATE_USER'
go

exec sp_addextendedproperty 'MS_Description', N'建檔日期', 'SCHEMA', 'dbo', 'TABLE', 'BPM_ISMS_L414', 'COLUMN',
     'CREATE_TIME'
go
